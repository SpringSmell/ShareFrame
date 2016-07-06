package com.future.sharelibrary.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.future.sharelibrary.adapter.BaseViewHolder;

/**
 * 对外开放的类，请继承该类
 * Created by chris on 2016/6/8.
 */
public abstract class ShareActivity extends HttpActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        init();
        onBindData(getViewHolder());
        initTitle();
    }

    public abstract void init();

    public abstract void onBindData(BaseViewHolder viewHolder);

    public void initTitle(){}

}
