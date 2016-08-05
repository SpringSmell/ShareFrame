package com.future.shareframe.activities;

import android.os.Bundle;

import com.future.sharelibrary.activities.WebActivity;


/**
 * Created by chris Zou on 2016/7/4.
 */
public class ShowWebActivity extends WebActivity {


    @Override
    public String loadUrl() {
        return "http://www.baidu.com";
    }

    @Override
    public void onInitData() {

    }
}
