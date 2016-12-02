package com.future.sharelibrary.frame;

import android.support.annotation.CallSuper;

import org.xutils.x;

/**
 * Created by Administrator on 2016/6/21.
 */
public class ShareApplication extends ExitApplication {

    private static ShareApplication instance;
    @CallSuper
    @Override
    public void onCreate() {
        x.Ext.setDebug(true);
        x.Ext.init(this);
        super.onCreate();
    }

    public static ShareApplication getInstance(){
        if(instance==null){
            instance=new ShareApplication();
        }
        return instance;
    }
}
