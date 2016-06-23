package com.future.shareframe.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.future.shareframe.R;
import com.future.shareframe.fragments.TestFragment;
import com.future.sharelibrary.activities.SideTabFragmentActivity;
import com.future.sharelibrary.model.MainTabContent;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/6/21.
 */
public class SideActivity extends SideTabFragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public LinkedList<MainTabContent> setData(LinkedList<MainTabContent> mMainTabContentList) {

        MainTabContent mainTabContent = new MainTabContent(TestFragment.class, "1", R.mipmap.ic_launcher, null);
        mMainTabContentList.add(mainTabContent);
        mMainTabContentList.add(mainTabContent);
        mMainTabContentList.add(mainTabContent);
        mMainTabContentList.add(mainTabContent);
        mMainTabContentList.add(mainTabContent);
        return mMainTabContentList;
    }
}
