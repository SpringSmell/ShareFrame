package com.future.sharelibrary.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.MainViewPagerAdapter;
import com.future.sharelibrary.model.MainTabContent;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/4/15.
 */
public abstract class SideTabFragmentActivity extends ShareActivity {

    protected TabLayout mTabs;
    protected ViewPager mViewPager;
    protected MainViewPagerAdapter mMainViewPagerAdapter;
    private LinkedList<MainTabContent> mMainTabContentList;
    private int currentLocation=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidetab);
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        mTabs = (TabLayout) findViewById(R.id.mainTabLayout);
    }

    @Override
    public void bindData() {
        mMainTabContentList = new LinkedList<>();
        mMainTabContentList = setData(mMainTabContentList);
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), mMainTabContentList);

        mViewPager.setAdapter(mMainViewPagerAdapter);
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);//支持超出水平的内容滚动
        mTabs.setupWithViewPager(mViewPager);
        mTabs.setTabsFromPagerAdapter(mMainViewPagerAdapter);

        mTabs.removeAllTabs();
        for (MainTabContent mainTabContent : mMainTabContentList) {
            switch (mainTabContent.getType()) {
                case MainTabContent.TYPE_CUSTOM:
                    mTabs.addTab(mTabs.newTab().setCustomView(mainTabContent.getCustomView()));
                    break;
                case MainTabContent.TYPE_CUSTOM_NO:
                    TabLayout.Tab tab=mTabs.newTab();
                    tab.setText(mainTabContent.getTitle());
                    if(mainTabContent.getIcon()!=0){
                        tab.setIcon(mainTabContent.getIcon());
                    }
                    mTabs.addTab(tab);
                    break;
            }
        }
        mMainViewPagerAdapter.setData(mMainTabContentList);
    }

    @Override
    public void initTitle() {

    }

    public void setCurrentView(int position){
        if(position<mMainViewPagerAdapter.getCount()&&position>=0){
            mViewPager.setCurrentItem(position);
        }else{
            showSnackbar("需要显示的位置未包含在数组内");
        }
    }

    public abstract LinkedList<MainTabContent> setData(LinkedList<MainTabContent> mMainTabContentList);
}
