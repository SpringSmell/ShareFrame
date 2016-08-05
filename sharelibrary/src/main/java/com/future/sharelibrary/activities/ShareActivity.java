package com.future.sharelibrary.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.BaseParentViewHolder;

import java.io.Serializable;

/**
 * 对外开放的类，请继承该类
 * 使用该类须隐藏title，主题城需使用兼容的风格，详情请查看Demo的mainifests
 * Created by chris on 2016/6/8.
 */
public abstract class ShareActivity extends HttpActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resultLayoutResId());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onInit();
        onInitData();
        onBindData(getViewHolder());
        onInitTitle();
    }

    /**
     * 初始化操作
     */
    public void onInit(){}

    public abstract int resultLayoutResId();
    /**
     * 初始化操作
     */
    public abstract void onInitData();

    /**
     * 绑定数据
     * @param viewHolder
     */
    public abstract void onBindData(BaseParentViewHolder viewHolder);

    @CallSuper
    public void onInitTitle(){
        setBackGroundColor(getResources().getColor(R.color.colorThemePrimary));
        setBackValid();
        if(getIntent().hasExtra("title")){
            setTitle(getIntent().getStringExtra("title"));
        }
    }

    public static void startAction(Activity activity, Class activityClass, String title){
        startAction(activity,activityClass,title,null,0);
    }
    public static void startAction(Activity activity, Class activityClass, String title,int requestCode){
        startAction(activity,activityClass,title,null,requestCode);
    }

    public static void startAction(Activity activity, Class activityClass, String title, Bundle params){
        startAction(activity,activityClass,title,params,0);
    }

    /**
     * 这只是一个简易的跳转方法，参数多时建议重写此方法
     * @param activity
     * @param activityClass
     * @param title
     * @param params
     * @param requestCode
     */
    public static void startAction(Activity activity, Class activityClass, String title, Bundle params,int requestCode){
        Intent intent=new Intent(activity,activityClass);
        intent.putExtra("title",title);
        intent.putExtra("params",params);
        activity.startActivityForResult(intent,requestCode);
    }
}
