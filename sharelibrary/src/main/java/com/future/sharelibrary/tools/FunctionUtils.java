package com.future.sharelibrary.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Administrator on 2016/8/2.
 */
public class FunctionUtils {


    /**
     * 打开本地浏览器
     *
     * @param context
     * @param url
     */
    public static final void openWebBrowser(Context context, String url) {
        Intent browserAction = new Intent(Intent.ACTION_VIEW);
        browserAction.setData(Uri.parse(url));
        context.startActivity(browserAction);
    }
}
