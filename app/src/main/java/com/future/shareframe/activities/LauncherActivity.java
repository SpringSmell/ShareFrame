package com.future.shareframe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.future.shareframe.R;
import com.future.shareframe.adapter.TestRVAdapter;
import com.future.shareframe.model.TestRVBean;
import com.future.sharelibrary.activities.ShareActivity;
import com.future.sharelibrary.adapter.BaseViewHolder;
import com.future.sharelibrary.adapter.RecyclerViewBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/7.
 */
public class LauncherActivity extends ShareActivity {

    private RecyclerView mRecyclerVew;
    private TestRVAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    public void init() {

    }

    @Override
    public void onBindData(BaseViewHolder viewHolder) {
        mRecyclerVew=viewHolder.getView(R.id.xRecyclerView);
        List<TestRVBean> contents=new ArrayList<>();
        for(int i=0;i<5;i++){
            TestRVBean bean=new TestRVBean();
            bean.title="item:"+i;
            contents.add(bean);
        }
        adapter=new TestRVAdapter();
        RecyclerView.LayoutManager manager=new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL);
        mRecyclerVew.setLayoutManager(manager);
        mRecyclerVew.setAdapter(adapter);
        for(int i=6;i<10;i++){
            TestRVBean bean=new TestRVBean();
            bean.title="item:"+i;
            contents.add(bean);
        }
        adapter.setDatas(contents);
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(LauncherActivity.this, Test1Activity.class));
                        break;
                    case 1:
                        startActivity(new Intent(LauncherActivity.this, SideActivity.class));
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    @Override
    public void initTitle() {
        setTitle("启动页");
        showTitle(true);
    }
}
