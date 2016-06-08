package com.future.shareframe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.future.shareframe.R;
import com.future.shareframe.frame.MainApplication;
import com.future.sharelibrary.activities.ShareActivity;
import com.future.sharelibrary.activities.ThemeActivity;
import com.future.sharelibrary.frame.ExitApplication;
import com.future.sharelibrary.listener.OnHttpListener;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/6/7.
 */
public class LauncherActivity extends ShareActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    public void initView() {
        ((TextView)findViewById(R.id.content)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LauncherActivity.this,Test1Activity.class));
                post("http://www.baidu.com",null, new OnHttpListener() {
                    @Override
                    public void onHttpResult(String json) {
                        showSnackbar("加载完成");
                    }
                });
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        setTitle("启动页");
//        isShowTitle(false);
    }
}
