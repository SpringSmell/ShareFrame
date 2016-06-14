package com.future.shareframe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.future.shareframe.R;
import com.future.shareframe.adapter.TestRVAdapter;
import com.future.shareframe.frame.MainApplication;
import com.future.sharelibrary.activities.MapActivity;
import com.future.sharelibrary.activities.ShareActivity;
import com.future.sharelibrary.activities.ThemeActivity;
import com.future.sharelibrary.frame.ExitApplication;
import com.future.sharelibrary.listener.OnHttpListener;
import com.future.sharelibrary.listener.OnItemClickListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.w3c.dom.Text;

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
    public void initView() {
        mRecyclerVew= (RecyclerView) findViewById(R.id.xRecyclerView);
    }

    @Override
    public void bindData() {
        List<String> contents=new ArrayList<>();
        for(int i=0;i<5;i++){
            contents.add("item:"+i);
        }
        adapter=new TestRVAdapter(contents);
        RecyclerView.LayoutManager manager=new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL);
        mRecyclerVew.setLayoutManager(manager);
        mRecyclerVew.setAdapter(adapter);
        for(int i=6;i<10;i++){
            contents.add("item:"+i);
        }
        adapter.setDatas(contents);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                showSnackbar(""+position);
            }
        });
    }

    @Override
    public void initTitle() {
        setTitle("启动页");
        showTitle(true);
    }
}
