package com.future.sharelibrary.model;

import android.view.View;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SideTabContent implements Serializable {

    private long id;
    private Class fragment;
    private CharSequence title;
    private int icon;
    /**
     * 传递到fragment的model
     */
    private Params params;
    /**
     * 自定义Tab
     */
    private View customView;

    private int type=TYPE_CUSTOM_NO;

    /**
     * 自定义Tab标签
     */
    public static final int TYPE_CUSTOM=0;
    /**
     * 默认的Tab标签
     */
    public static final int TYPE_CUSTOM_NO=1;

    public SideTabContent(Class fragment, CharSequence title, View customView, Params params){
        this(fragment,title,0,customView, params);
    }

    public SideTabContent(Class fragment, CharSequence title, View customView){
        this(fragment,title,0,customView,null);
    }

//    public SideTabContent(Class fragment, CharSequence title, int customViewId, Params params){
//        this(fragment,title,0,customView, params);
//    }
//
//    public SideTabContent(Class fragment, CharSequence title, int customViewId){
//        this(fragment,title,0,customView,null);
//    }

    public SideTabContent(Class fragment, CharSequence title, int icon, Params params){
        this(fragment,title,icon,null, params);
    }

    public SideTabContent(Class fragment, CharSequence title, int icon){
        this(fragment,title,icon,null,null);
    }

    public SideTabContent(Class fragment, CharSequence title, int icon, View customView, Params params){
        this.id=makeId();
        this.fragment=fragment;
        this.title=title;
        this.params = params;
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

    public Params getParams() {
        return params;
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

    public long getId(){
        return id;
    }

    private long makeId(){
        Random random=new Random();
        return random.nextLong();
    }
}
