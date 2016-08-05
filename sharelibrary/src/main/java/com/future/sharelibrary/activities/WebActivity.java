package com.future.sharelibrary.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.BaseParentViewHolder;


public abstract class WebActivity extends ShareActivity {

    private WebView mWebView;

    @Override
    public int resultLayoutResId() {
        return R.layout.activity_web;
    }

    /**
     * 布局为R.layout.activity_web
     * 还未找到更好的方式替代
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(resultLayoutResId());
    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {
        mWebView = viewHolder.getView(R.id.webContent);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.loadUrl(loadUrl());
    }

    public abstract String loadUrl();
}
