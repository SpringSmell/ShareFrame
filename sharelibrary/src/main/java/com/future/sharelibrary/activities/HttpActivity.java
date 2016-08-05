package com.future.sharelibrary.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.future.sharelibrary.listener.OnHttpListener;
import com.future.sharelibrary.tools.HttpUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Set;

/**
 * Created by Administrator on 2016/6/8.
 */
public class HttpActivity extends ThemeActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected boolean isNetworkAvailable(){
        return HttpUtils.isNetWorkAvailable(this);
    }

    protected void onPost(String url,Bundle params, final OnHttpListener onHttpListener){
        x.http().post(getParams(url,params), new Callback.CacheCallback<String>() {

            @Override
            public boolean onCache(String s) {
                boolean flag=onHttpListener.onTrustCache();
                if(flag){
                    alertPopupWindow();
                }
                return flag;// true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
            }

            @Override
            public void onSuccess(String s) {
                onHttpListener.onHttpResult(s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                showSnackbar("请求失败，请稍候重试...");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showSnackbar("已取消");
            }

            @Override
            public void onFinished() {
                dismissPopupWindow();
            }
        });
    }

    protected void onGet(String url,Bundle params, final OnHttpListener onHttpListener){
        x.http().get(getParams(HttpUtils.httpEventGetFormat(url,params),null), new Callback.CacheCallback<String>() {

            @Override
            public boolean onCache(String s) {
                boolean flag=onHttpListener.onTrustCache();
                if(flag){
                    alertPopupWindow();
                }
                return flag;// true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
            }

            @Override
            public void onSuccess(String s) {
                Log.e("HTTP", "onSuccess: "+s);
                onHttpListener.onHttpResult(s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                showSnackbar("请求失败，请稍候重试...");
            }

            @Override
            public void onCancelled(Callback.CancelledException e) {
                showSnackbar("已取消");
            }

            @Override
            public void onFinished() {
                dismissPopupWindow();
            }
        });
    }

    protected RequestParams getParams(String url, Bundle params){
        RequestParams mRequestParams=new RequestParams(url);
        if(params==null){
            return mRequestParams;
        }
        Set<String> keySet=params.keySet();
        for(String key:keySet){
            mRequestParams.addBodyParameter(key,params.get(key)+"");
        }
        return mRequestParams;
    }
}
