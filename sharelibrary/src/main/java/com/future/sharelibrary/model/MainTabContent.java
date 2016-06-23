package com.future.sharelibrary.model;

import android.view.View;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/15.
 */
public class MainTabContent implements Serializable {

    private Class fragment;
    private CharSequence title;
    private int icon;
    /**
     * 传递到fragment的model
     */
    private MainContent mainContent;
    private View customView;//自定义Tab

    private int type=TYPE_CUSTOM_NO;

    /**
     * 自定义Tab标签
     */
    public static final int TYPE_CUSTOM=0;
    /**
     * 默认的Tab标签
     */
    public static final int TYPE_CUSTOM_NO=1;

    public MainTabContent(Class fragment,CharSequence title,View customView,MainContent mainContent){
        this(fragment,title,0,customView,mainContent);
    }

    public MainTabContent(Class fragment, CharSequence title,int icon,MainContent mainContent){
        this(fragment,title,icon,null,mainContent);
    }

    public MainTabContent(Class fragment, CharSequence title,int icon,View customView,MainContent mainContent){
        this.fragment=fragment;
        this.title=title;
        this.mainContent=mainContent;
        this.icon=icon;
        this.customView=customView;
        if(customView!=null){
            type=TYPE_CUSTOM;
        }
        if(fragment==null){
            throw new IllegalArgumentException("You need set a class");
        }
    }



    public CharSequence getTitle() {
        return title;
    }

    public Class getFragment() {
        return fragment;
    }

    public MainContent getMainContent() {
        return mainContent;
    }

    public int getIcon() {
        return icon;
    }

    public int getType() {
        return type;
    }

    public View getCustomView() {
        return customView;
    }
}
