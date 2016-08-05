package com.future.shareframe.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.future.shareframe.R;
import com.future.shareframe.adapter.TestRVParentAdapter;
import com.future.shareframe.model.TestRVBean;
import com.future.sharelibrary.activities.*;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.adapter.RecyclerViewBaseAdapter;
import com.future.sharelibrary.function.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/7.
 */
public class LauncherActivity extends ShareActivity {

    private RecyclerView mRecyclerVew;
    private TestRVParentAdapter adapter;

    @Override
    public int resultLayoutResId() {
        return R.layout.activity_launcher;
    }

    @Override
    public void onInitData() {

    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {
        mRecyclerVew = viewHolder.getView(R.id.xRecyclerView);
        List<TestRVBean> contents = new ArrayList<>();
        TestRVBean bean = new TestRVBean();
        bean.title = "TestViewActivity";
        contents.add(bean);
        bean = new TestRVBean();
        bean.title = "SideActivity";
        contents.add(bean);
        bean = new TestRVBean();
        bean.title = "ChartActivity";
        contents.add(bean);
        bean = new TestRVBean();
        bean.title = "SelectorTimeActivity";
        contents.add(bean);
        bean = new TestRVBean();
        bean.title = "CameraActivity";
        contents.add(bean);
        bean = new TestRVBean();
        bean.title = "TestViewActivity";
        contents.add(bean);
        adapter = new TestRVParentAdapter();
        bean = new TestRVBean();
        bean.title = "ResolveSwipeRefreshActivity";
        contents.add(bean);
        adapter = new TestRVParentAdapter();
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL);
        mRecyclerVew.setLayoutManager(manager);
        mRecyclerVew.setAdapter(adapter);
        adapter.setDatas(contents);
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(LauncherActivity.this, TestViewActivity.class));
                        break;
                    case 1:
                        SideActivity.startAction(LauncherActivity.this, SideActivity.class, "滑动菜单");
                        break;
                    case 2:
                        ChartActivity.startAction(LauncherActivity.this, ChartActivity.class, "图表库");
                        break;
                    case 3:
                        SelectorTimeActivity.startAction(LauncherActivity.this, SelectorTimeActivity.class, "时间选择器");
                        break;
                    case 4:
                        CameraBaseActivity.startAction(LauncherActivity.this, CameraBaseActivity.class, "图片选择器");
                        break;
                    case 5:
                        TestViewActivity.startAction(LauncherActivity.this, TestViewActivity.class, "虚线动画");
                        break;
                    case 6:
                        ResolveSwipeRefreshActivity.startAction(LauncherActivity.this, ResolveSwipeRefreshActivity.class, "冲突的的下拉刷新");
                        break;
                }
            }
        });
//        String json = "[{\"station\":\"Z9280\",\"name\":\"成都\",\"仰角\":[\"0.5\",\"1.5\",\"2.4\"],\"范围\":[\"230\",\"460\"]},{\"station\":\"Z9833\",\"name\":\"乐山\",\"仰角\":[\"0.5\",\"1.5\",\"2.4\"],\"范围\":[\"230\",\"460\"]},{\"station\":\"BCCD\",\"name\":\"拼图\",\"仰角\":[\"0.5\",\"1.5\",\"2.4\"],\"范围\":[\"230\",\"460\"]}]";
//        List<TestBean> testBean = GsonUtils.parseFromJsons(json, TestBean.class);
//        if (testBean == null) {
//            Log.e("TEST", "onBindData: ");
//        }
//        showSnackbar(testBean.get(0).get仰角()[0] + "");
    }

    @Override
    public void onInitTitle() {
        super.onInitTitle();
        setTitle("库功能列表");
        setBackValid(false);
//        setBackGroundColor(getResources().getColor(R.color.colorAccent));
    }
}
