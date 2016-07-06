package com.future.shareframe.frame;


import android.app.Application;


/**
 * Created by chris on 2016/6/7.
 */
public class MainApplication extends Application {

    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MainApplication newInstance() {
        if (instance == null) {
            instance = new MainApplication();
        }
        return instance;

    }

}
