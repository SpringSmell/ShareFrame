package com.future.sharelibrary.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;


import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.adapter.SideTabBaseParentAdapter;
import com.future.sharelibrary.model.SideTabContent;

import java.util.LinkedList;

/**
 * 水平滑动Tab
 * Created by chris Zou on 2016/4/15.
 */
public abstract class SideTabBaseActivity extends ShareActivity implements TabLayout.OnTabSelectedListener{

    public static final String TAG = SideTabBaseActivity.class.getSimpleName();
    protected TabLayout mTabs;
    protected ViewPager mViewPager;
    protected SideTabBaseParentAdapter mSideTabAdapter;
    private LinkedList<SideTabContent> mSideTabContentList;
    private int currentLocation = 0;
    public static int DEFAULT_LAYOUT = R.layout.activity_sidetab;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resultLayoutResId());
        bindData(getViewHolder());
    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {
        init();
        mViewPager = viewHolder.getView(R.id.sideMainViewPager);
        mTabs = viewHolder.getView(R.id.sideMainTabLayout);

        mSideTabContentList = new LinkedList<>();
        mSideTabContentList = initTabData(mSideTabContentList);
        mSideTabAdapter = new SideTabBaseParentAdapter(getSupportFragmentManager(), mSideTabContentList);

        mViewPager.setAdapter(mSideTabAdapter);
        mTabs.setupWithViewPager(mViewPager);
        if(mSideTabContentList.size()>5){
            mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);//支持超出水平的内容滚动
            mTabs.setTabGravity(TabLayout.GRAVITY_CENTER);
        }else{
            mTabs.setTabMode(TabLayout.MODE_FIXED);//支持超出水平的内容滚动
            mTabs.setTabGravity(TabLayout.GRAVITY_FILL);
        }
        mTabs.removeAllTabs();
        for (SideTabContent sideTabContent : mSideTabContentList) {
            switch (sideTabContent.getType()) {
                case SideTabContent.TYPE_CUSTOM:
                    mTabs.addTab(mTabs.newTab().setCustomView(sideTabContent.getCustomView()));
                    break;
                case SideTabContent.TYPE_CUSTOM_NO:
//                    TabLayout.Tab tab = mTabs.newTab();
//                    tab.setText(sideTabContent.getTitle());
//                    if (sideTabContent.getIcon() != 0) {
//                        tab.setIcon(sideTabContent.getIcon());
//                    }
                    mTabs.addTab(mTabs.newTab().setText("").setIcon(sideTabContent.getIcon()));
                    break;
            }
        }
        mSideTabAdapter.setData(mSideTabContentList);

    }

    /**
     * @param position 下标
     * @return
     * @hint 该只有在界面加载完成之后才能取到值, 且只有展示过的页面才有值
     */
    public <T> T getFragment(int position) {
        return (T) this.mSideTabContentList.get(position).getFragment();
    }

    /**
     * 显示指定页面
     *
     * @param position
     */
    public void setCurrentView(int position) {
        if (position < mSideTabAdapter.getCount() && position >= 0) {
            mViewPager.setCurrentItem(position);
        } else {
            Log.e(TAG, "\"需要显示的位置未包含在数组内\"");
        }
    }

    /**
     * 返回布局ID，返回0使用默认布局，
     * 自定义布局，请参考默认布局使用控件及ID
     *
     * @return
     */
    public int resultLayoutResId() {
        return DEFAULT_LAYOUT;
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (layoutResID == 0) {
            layoutResID = DEFAULT_LAYOUT;//默认布局
        }
        super.setContentView(layoutResID);
    }

    public void init() {}

    /**
     * 初始化Tab项的数据
     *
     * @param mSideTabContentList
     * @return
     */
    public abstract LinkedList<SideTabContent> initTabData(LinkedList<SideTabContent> mSideTabContentList);

    /**
     * 绑定数据
     *
     * @param holder 控件器，内包含根view及实用方法
     */
    public abstract void bindData(BaseParentViewHolder holder);

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setCurrentView(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
