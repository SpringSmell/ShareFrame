package com.future.shareframe.activities;

import android.graphics.Color;

import com.future.shareframe.R;
import com.future.sharelibrary.activities.ShareActivity;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.widgets.CurveChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by chris zou on 2016/7/19.
 */

public class ChartActivity extends ShareActivity {

    private CurveChartView mCurveChartView;

    @Override
    public int resultLayoutResId() {
        return R.layout.activity_chart;
    }

    @Override
    public void onInitData() {

    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {
        mCurveChartView = viewHolder.getView(R.id.chartView);

        List<CurveChartView.ChartBean> chartBeen = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            CurveChartView.ChartBean chartBean = new CurveChartView.ChartBean();
            chartBean.index = random.nextInt(100);
            chartBeen.add(chartBean);

        }

        List<List<CurveChartView.ParamsBean>> headBeenList = new ArrayList<>();
        List<List<CurveChartView.ParamsBean>> footBeenList = new ArrayList<>();
        List<CurveChartView.ParamsBean> headBeen = new ArrayList<>();
        List<CurveChartView.ParamsBean> footBeen = new ArrayList<>();
        for (int j = 0; j < 24; j++) {
            CurveChartView.ParamsBean headBean = new CurveChartView.ParamsBean();
            headBean.titleName = "" + j;
            headBean.icon=R.mipmap.ic_launcher;
            headBeen.add(headBean);
            CurveChartView.ParamsBean footBean = new CurveChartView.ParamsBean();
            footBean.titleName = "" + j;
            footBeen.add(headBean);
        }
        headBeenList.add(headBeen);
        headBeenList.add(headBeen);
        footBeenList.add(footBeen);

        mCurveChartView.setData(chartBeen, headBeenList, footBeenList);
        mCurveChartView.setShapeColor(Color.GREEN, 50);

    }

}
