package com.future.shareframe.activities;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ScrollView;

import com.future.shareframe.R;
import com.future.shareframe.adapter.ResolveSwipeRefreshAdapter;
import com.future.sharelibrary.activities.ShareActivity;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.widgets.CustomSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris zou on 2016/8/3.
 */

public class ResolveSwipeRefreshActivity extends ShareActivity {

    CustomSwipeRefreshLayout mCustomSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ScrollView mScrollView;
    ResolveSwipeRefreshAdapter mResolveSwipeRefreshAdapter;

    @Override
    public int resultLayoutResId() {
        return R.layout.activity_resolve_swipe_refresh;
    }

    @Override
    public void onInitData() {

    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {

        mScrollView=viewHolder.getView(R.id.scrollView);
        mScrollView.setVisibility(View.VISIBLE);
        mCustomSwipeRefreshLayout=viewHolder.getView(R.id.swipeRefreshLayout);
        mCustomSwipeRefreshLayout.setResolveScrollView(mScrollView);

//        mResolveSwipeRefreshAdapter=new ResolveSwipeRefreshAdapter();
//        mRecyclerView=viewHolder.getView(R.id.xRecyclerView);
//        mRecyclerView.setVisibility(View.GONE);
//        mRecyclerView.setAdapter(mResolveSwipeRefreshAdapter);
//        RecyclerView.LayoutManager layoutManager=new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//        List<String> datas=new ArrayList<>();
//        for(int i=0;i<100;i++){
//            datas.add(i+"");
//        }
//        mResolveSwipeRefreshAdapter.setDatas(datas);

    }
}
