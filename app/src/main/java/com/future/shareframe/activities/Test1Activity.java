package com.future.shareframe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.future.shareframe.R;

/**
 * Created by Administrator on 2016/6/7.
 */
public class Test1Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_Dialog);
        setContentView(R.layout.activity_test1);
        super.onCreate(savedInstanceState);
    }

}
