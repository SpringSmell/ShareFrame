package com.future.sharelibrary.frame;

/**
 * Created by Administrator on 2016/6/21.
 */
public class ShareApplication extends ExitApplication {

    private static ShareApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static ShareApplication getInstance(){
        if(instance==null){
            instance=new ShareApplication();
        }
        return instance;
    }
}
