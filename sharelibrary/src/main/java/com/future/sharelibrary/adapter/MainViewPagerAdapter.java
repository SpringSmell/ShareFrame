package com.future.sharelibrary.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.future.sharelibrary.model.MainTabContent;

import java.util.LinkedList;


/**
 * Created by Administrator on 2016/4/15.
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private LinkedList<MainTabContent> mainTabContentList;

    public MainViewPagerAdapter(FragmentManager fm,LinkedList<MainTabContent> mainTabContentList) {
        super(fm);
        this.mainTabContentList = mainTabContentList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=new Fragment();

        try {
            fragment= (Fragment) mainTabContentList.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle bundle=new Bundle();
        bundle.putSerializable("content",mainTabContentList.get(position).getMainContent());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mainTabContentList!=null?mainTabContentList.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mainTabContentList.get(position).getTitle();
    }

    public void setData(LinkedList<MainTabContent> mainTabContentList){
        if(mainTabContentList !=null){
            this.mainTabContentList = mainTabContentList;
            notifyDataSetChanged();
        }else{
            throw new NullPointerException("You cannot set null data");
        }
    }

}
