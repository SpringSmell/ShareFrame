package com.future.sharelibrary.activities;

import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.BaseParentViewHolder;

/**
 * 调用系统相册，可多选
 * Created by chris on 2016/6/8.
 */
public class CameraBaseActivity extends ShareActivity {
    @Override
    public int resultLayoutResId() {
        return R.layout.activity_camera;
    }

    @Override
    public void onInitData() {

    }
    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {

    }

}
