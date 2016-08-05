package com.future.sharelibrary.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/15.
 */
public class Params implements Serializable {

    private String content;
    private String tag;

    public Params(String tag, String content) {
        this.tag = tag;
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
