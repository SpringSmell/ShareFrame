package com.future.shareframe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.future.shareframe.R;
import com.future.sharelibrary.activities.ShareActivity;
import com.future.sharelibrary.adapter.BaseParentViewHolder;

/**
 * Created by Administrator on 2016/6/7.
 */
public class TestViewActivity extends ShareActivity {

    @Override
    public int resultLayoutResId() {
        return R.layout.activity_test_view;
    }

    @Override
    public void onInitData() {

    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {

    }
}
