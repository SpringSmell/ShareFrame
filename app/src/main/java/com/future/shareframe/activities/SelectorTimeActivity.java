package com.future.shareframe.activities;

import android.view.View;
import android.widget.TextView;

import com.future.shareframe.R;
import com.future.shareframe.frame.MainApplication;
import com.future.sharelibrary.activities.ShareActivity;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.function.AreaSelector;
import com.future.sharelibrary.function.TimeSelector;
import com.future.sharelibrary.tools.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris zou on 2016/7/22.
 */

public class SelectorTimeActivity extends ShareActivity {
    public TimeSelector mTimeSelector;
    public AreaSelector mAreaSelector;

    public List<DBUtils.CityData> cityDataList;

    @Override
    public int resultLayoutResId() {
        return R.layout.activity_selector_time;
    }

    @Override
    public void onInitData() {

    }


    @Override
    public void onBindData(final BaseParentViewHolder viewHolder) {
    }

    public void selectorTime(final View view) {
        if (mTimeSelector == null) {
            mTimeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
                @Override
                public void handle(String time) {
                    ((TextView) view).setText(time);
                }
            }, "1999-01-01 00:00", "2021-01-01 00:00");
            mTimeSelector.setMode(TimeSelector.MODE.YMDHM);
        }
        mTimeSelector.show();
    }

    public void selectorArea(final View view) {
        if (mAreaSelector == null) {
            mAreaSelector = new AreaSelector(SelectorTimeActivity.this, new AreaSelector.ResultHandler() {
                @Override
                public void handle(String[] time) {
                    String area = "";
                    for (String string : time) {
                        area += string + "-";
                    }
                    ((TextView) view).setText(area.subSequence(0, area.length() - 1));
                }
            },"重庆");
//            mAreaSelector.setMode(AreaSelector.MODE.PCDCT);
        }
        mAreaSelector.show();
    }

}
