package com.future.shareframe.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.future.shareframe.R;
import com.future.shareframe.fragments.TestFragment;
import com.future.sharelibrary.activities.SideTabBaseActivity;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.model.Params;
import com.future.sharelibrary.model.SideTabContent;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/6/21.
 */
public class SideActivity extends SideTabBaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInitData() {

    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public LinkedList<SideTabContent> initTabData(LinkedList<SideTabContent> mSideTabContentList) {
        mSideTabContentList=new LinkedList<>();
        mSideTabContentList.add(new SideTabContent(TestFragment.class, "1", R.mipmap.ic_launcher, new Params("1", "json")));
        mSideTabContentList.add(new SideTabContent(TestFragment.class, "2", R.mipmap.ic_launcher));
        mSideTabContentList.add(new SideTabContent(TestFragment.class, "3", R.mipmap.ic_launcher));
//        mSideTabContentList.add(new SideTabContent(TestFragment.class, "4", R.mipmap.ic_launcher));
//        mSideTabContentList.add(new SideTabContent(TestFragment.class, "5", R.mipmap.ic_launcher));
//        mSideTabContentList.add(new SideTabContent(TestFragment.class, "6", R.mipmap.ic_launcher));
//        mSideTabContentList.add(new SideTabContent(TestFragment.class, "7", R.mipmap.ic_launcher, new Params("1", "json")));
//        mSideTabContentList.add(new SideTabContent(TestFragment.class, "8", R.mipmap.ic_launcher, new Params("1", "json")));
//        mSideTabContentList.add(new SideTabContent(TestFragment.class, "9", R.mipmap.ic_launcher, new Params("1", "json")));
//        View customView= LayoutInflater.from(this).inflate(R.layout.activity_tab,null);
//        sideTabContent = new SideTabContent(TestFragment.class,"4", customView);
//        mSideTabContentList.add(sideTabContent);
//
//        View customView2= LayoutInflater.from(this).inflate(R.layout.activity_tab,null);
//        sideTabContent = new SideTabContent(TestFragment.class, "5",customView2);
//        mSideTabContentList.add(sideTabContent);
        return mSideTabContentList;
    }

    @Override
    public void bindData(BaseParentViewHolder holder) {

    }
}
