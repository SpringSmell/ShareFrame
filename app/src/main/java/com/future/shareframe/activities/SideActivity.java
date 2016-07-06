package com.future.shareframe.activities;


import com.future.shareframe.R;
import com.future.shareframe.fragments.TestFragment;
import com.future.sharelibrary.activities.SideTabFragmentActivity;
import com.future.sharelibrary.model.MainContent;
import com.future.sharelibrary.model.MainTabContent;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/6/21.
 */
public class SideActivity extends SideTabFragmentActivity {


    @Override
    public void init() {

    }

    @Override
    public LinkedList<MainTabContent> setData(LinkedList<MainTabContent> mMainTabContentList) {

        MainTabContent mainTabContent = new MainTabContent(TestFragment.class, "1", R.mipmap.ic_launcher, new MainContent("1","json"));
        mMainTabContentList.add(mainTabContent);
        mMainTabContentList.add(mainTabContent);
        mMainTabContentList.add(mainTabContent);
        mMainTabContentList.add(mainTabContent);
        mMainTabContentList.add(mainTabContent);
        return mMainTabContentList;
    }

    @Override
    public void initTitle() {
        setBackValid();
    }
}
