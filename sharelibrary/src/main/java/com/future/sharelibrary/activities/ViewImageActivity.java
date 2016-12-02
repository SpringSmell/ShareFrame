package com.future.sharelibrary.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.ViewImgAdapter;
import com.future.sharelibrary.tools.ScreenUtils;
import com.future.sharelibrary.adapter.BaseParentViewHolder;

import java.util.ArrayList;

/**
 * 查看图片
 * Created by chris zou on 2016/9/5.
 */

public class ViewImageActivity extends ShareActivity {
    private ViewPager mViewPager;
    private ViewImgAdapter mViewImgAdapter;
    private ArrayList<String> paths;
    private ArrayList<String> alreadyPaths;

    public static final String PATHS="paths";
    public static final String ALREADY_PATHS="alreadyPaths";
    public static final String CURRENT_ITEM="currentItem";

    @Override
    public int resultLayoutResId() {
        return R.layout.activity_view_image;
    }

    @Override
    public void onInitData() {
        paths=getIntent().getStringArrayListExtra(PATHS);
        alreadyPaths=getIntent().getStringArrayListExtra(ALREADY_PATHS);
        ScreenUtils.initScreen(this);
    }

    @Override
    public void onBindData(BaseParentViewHolder holder) {
        mViewPager=holder.getView(R.id.viewImgViewPager);
        mViewImgAdapter=new ViewImgAdapter(paths);
        mViewPager.setAdapter(mViewImgAdapter);
        mViewPager.setCurrentItem(getIntent().getIntExtra(CURRENT_ITEM,0));
    }

    @Override
    public void onInitLayout() {
        super.onInitLayout();
    }

    public static void startAction(Context context, String title, ArrayList<String> paths, ArrayList<String> alreadyPaths, int currentItem){
        Intent intent=new Intent(context,ViewImageActivity.class);
        intent.putExtra(ShareActivity.TITLE,title);
        intent.putExtra(PATHS,paths);
        intent.putExtra(ALREADY_PATHS,alreadyPaths);
        intent.putExtra(CURRENT_ITEM,currentItem);
        context.startActivity(intent);
    }
}
