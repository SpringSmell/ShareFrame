package com.future.sharelibrary.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.future.sharelibrary.function.ApkUpdateService;

/**
 * Created by chris zou on 2016/8/24.
 */

public class DownloadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ApkUpdateService.startAction(context, intent.getStringExtra(ApkUpdateService.APK_URL), intent.getStringExtra(ApkUpdateService.TITLE));
    }
}