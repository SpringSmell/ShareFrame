package com.future.shareframe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.future.shareframe.R;
import com.future.sharelibrary.widgets.ARCChart;

/**
 * Created by Administrator on 2016/6/7.
 */
public class Test1Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(new ARCChart(this));
        super.onCreate(savedInstanceState);
    }

}
