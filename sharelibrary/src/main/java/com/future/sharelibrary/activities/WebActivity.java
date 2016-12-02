package com.future.sharelibrary.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.BaseParentViewHolder;


public class WebActivity extends ShareActivity {

    private WebView mWebView;

    @Override
    public int resultLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    public void onInitData() {

    }

    /**
     * 布局为R.layout.activity_web
     * 还未找到更好的方式替代
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(resultLayoutResId());
    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {
        mWebView = viewHolder.getView(R.id.webView);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.loadUrl(getIntent().getStringExtra("url"));
    }

    public static void startAction(Context context, String url,String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
}
