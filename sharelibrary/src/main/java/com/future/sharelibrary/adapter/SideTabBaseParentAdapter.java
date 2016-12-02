package com.future.sharelibrary.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.future.sharelibrary.model.SideTabContent;

import java.util.LinkedList;


/**
 * Created by Administrator on 2016/4/15.
 */
public class SideTabBaseParentAdapter extends FragmentBaseParentPagerAdapter {

    private LinkedList<SideTabContent> mSideTabContentList;

    public SideTabBaseParentAdapter(FragmentManager fm, LinkedList<SideTabContent> sideTabContentList) {
        super(fm);
        this.mSideTabContentList = sideTabContentList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        try {
            fragment = (Fragment) mSideTabContentList.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("content", mSideTabContentList.get(position).getParams());
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return mSideTabContentList != null ? mSideTabContentList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return mSideTabContentList.get(position).getId();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mSideTabContentList.get(position).getTitle();
    }

    public void setData(LinkedList<SideTabContent> sideTabContentList) {
        if (sideTabContentList != null) {
            this.mSideTabContentList = sideTabContentList;
            notifyDataSetChanged();
        } else {
            throw new NullPointerException("You cannot set null data");
        }
    }

}
