package com.future.sharelibrary.listener;

/**
 * Created by Administrator on 2016/6/8.
 */
public interface OnHttpListener {

    /**
     * 返回json
     * @param json
     */
    void onHttpResult(String json);

    /**
     * 是否信任缓存
     * @return
     */
    boolean onTrustCache();
}
