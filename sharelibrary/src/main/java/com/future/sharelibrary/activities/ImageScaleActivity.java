package com.future.sharelibrary.activities;

import android.widget.ImageView;

import com.future.sharelibrary.R;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.tools.ImageUtils;

/**
 * Created by chris zou on 2016/8/9.
 */

public class ImageScaleActivity extends ShareActivity{
    @Override
    public int resultLayoutResId() {
        return R.layout.activity_image_scale;
    }

    @Override
    public void onInitData() {

    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {
        String url="http://pic27.nipic.com/20130127/11885082_202137137172_2.jpg";
        ImageUtils.displayImg((ImageView) viewHolder.getView(R.id.imageScaleView),url);
    }


}
