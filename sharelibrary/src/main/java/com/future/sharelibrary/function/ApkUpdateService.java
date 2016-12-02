package com.future.sharelibrary.function;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.future.sharelibrary.R;
import com.future.sharelibrary.broadcast.DownloadReceiver;
import com.future.sharelibrary.tools.CommonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import static android.content.Intent.ACTION_VIEW;

/**
 * Created by chris zou on 2016/8/15.
 */

public class ApkUpdateService extends Service {

    private NotificationManager mNotificationManager = null;
    private NotificationCompat.Builder builder;

    public static final int updateNotifiId = 0x000001;
    public static final String APK_DOWNLOAD_FLAG = "apkDownloadFlag";
    public static final String APK_URL = "apk_url";
    public static final String TITLE = "title";

    // apk下载路径
    private String apkUrl = "";
    // apk保存路径(如果有SD卡就保存SD卡,如果没有SD卡就保存到手机包名下的路径)
    private String apkDir = "";
    // apk保存文件名
    private String title = "";

    private Callback.Cancelable mCancelable;
    private boolean isDownloadFinish;

    BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (APK_DOWNLOAD_FLAG.equalsIgnoreCase(intent.getAction())) {
//                startDownload();
                startAction(context, APK_URL, TITLE);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * @Description:
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(APK_DOWNLOAD_FLAG);
//        registerReceiver(notificationReceiver, filter);
//        mRemoteViews.setTextViewText();
//        nCustomView.findViewById(R.id.downloadAction).setOnClickListener(this);
//        nCustomView.findViewById(R.id.downloadTitle).setOnClickListener(this);
//        nCustomView.findViewById(R.id.downloadHint).setOnClickListener(this);
//        nCustomView.findViewById(R.id.downloadLogo).setOnClickListener(this);
//        nCustomView.findViewById(R.id.downloadProgress).setOnClickListener(this);
        initAPKDir();// 创建保存路径
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mCancelable != null && !mCancelable.isCancelled() && !isDownloadFinish) {
            showToast("已经在下载了，请查看通知栏");
            return super.onStartCommand(intent, flags, startId);
        }
        // 接收Intent传来的参数:
        apkUrl = intent.getStringExtra(APK_URL);
        title = intent.getStringExtra(TITLE);
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        if (isDownloadFinish) {
            mCancelable.cancel();
        } else {
            mCancelable = x.http().post(new RequestParams(apkUrl), new Callback.ProgressCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    isDownloadFinish = true;
                    PendingIntent mPendingIntent = PendingIntent.getActivity(ApkUpdateService.this, 0, CommonUtils.getInstallApkIntent(result), 0);
                    builder.setContentText("下载完成,请点击安装");
                    builder.setContentIntent(mPendingIntent);
//                    builder.setCustomContentView(mRemoteViews);
                    mNotificationManager.notify(updateNotifiId, builder.build());

                    // 震动提示
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(1000L);//参数是震动时间(long类型)

                    stopSelf();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    isDownloadFinish = false;

                    Intent onceAgainIntent = new Intent(ApkUpdateService.this, DownloadReceiver.class);
                    onceAgainIntent.setAction(APK_DOWNLOAD_FLAG);
                    onceAgainIntent.putExtra(APK_URL, apkUrl);
                    onceAgainIntent.putExtra(TITLE, title);
                    PendingIntent pi = PendingIntent.getBroadcast(ApkUpdateService.this, 0, onceAgainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pi);

                    builder.setContentInfo("下载失败，点击重试");
                    mNotificationManager.notify(updateNotifiId, builder.build());
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    mCancelable = null;
                }

                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {
                    mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    builder = new NotificationCompat.Builder(getApplicationContext());
                    builder.setSmallIcon(R.mipmap.ic_file_download_black);
                    builder.setTicker("正在下载新版本");
                    builder.setContentTitle(title);
                    builder.setContentText("正在下载,请稍后....");
                    builder.setNumber(0);
                    builder.setOngoing(true);
                    builder.setAutoCancel(true);
                    mNotificationManager.notify(updateNotifiId, builder.build());
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    int x = (int) current;
                    int totalCount = (int) total;
                    builder.setSmallIcon(R.mipmap.ic_file_download_black);
                    builder.setProgress(totalCount, x, false);
                    builder.setContentInfo(getPercent(x, totalCount));
                    mNotificationManager.notify(updateNotifiId, builder.build());
                }
            });
        }
    }

    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);
    }

    private void initAPKDir() {
        /**
         * 创建路径的时候一定要用[/],不能使用[\],但是创建文件夹加文件的时候可以使用[\].
         * [/]符号是Linux系统路径分隔符,而[\]是windows系统路径分隔符 Android内核是Linux.
         */
        if (isHasSdcard())// 判断是否插入SD卡
            apkDir = getApplicationContext().getFilesDir().getAbsolutePath() + "/apk/download/";// 保存到app的包名路径下
        else
            apkDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk/download/";// 保存到SD卡路径下
        File destDir = new File(apkDir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
    }

    /**
     * @Description:判断是否插入SD卡
     */
    private boolean isHasSdcard() {
        String status = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param x     当前值
     * @param total 总值
     * @return 当前百分比
     * @Description:返回百分之值
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getPercent(int x, int total) {
        String result = "";// 接受百分比的值
        double x_double = x * 1.0;
        double tempResult = x_double / total;
        // 百分比格式，后面不足2位的用0补齐 ##.00%
        DecimalFormat df1 = new DecimalFormat("0.00%");
        result = df1.format(tempResult);
        return result;
    }

    /**
     * Title: onDestroy
     *
     * @Description:
     * @see android.app.Service#onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    public void showToast(String content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    public static void startAction(Context context, String apkUrl, String title) {
        Intent intent = new Intent(context, ApkUpdateService.class);
        intent.putExtra(APK_URL, apkUrl);
        intent.putExtra(TITLE, title);
        context.startService(intent);
    }

    @Override
    public boolean stopService(Intent name) {
//        unregisterReceiver(notificationReceiver);
        return super.stopService(name);
    }
}
