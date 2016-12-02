package com.future.sharelibrary.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.future.sharelibrary.R;
import com.future.sharelibrary.tools.SDCardImageLoader;
import com.future.sharelibrary.widgets.images.ImageScaleView;

import java.util.List;

/**
 * Created by chris zou on 2016/9/5.
 */

public class ViewImgAdapter extends PagerAdapter {
    private SDCardImageLoader loader;
    private List<String> imgPaths;

    public ViewImgAdapter(List<String> imgPaths){
        this.imgPaths=imgPaths;
//        loader = new SDCardImageLoader(ScreenUtils.getScreenW(), ScreenUtils.getScreenH());
    }

    @Override
    public int getCount() {
        return imgPaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String localhostPath=imgPaths.get(position);
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.activity_image_scale,container,false);
        ImageScaleView imageScaleView= (ImageScaleView) view.findViewById(R.id.imageScaleView);
//        Bitmap bitmap= BitmapFactory.decodeFile(localhostPath);
//        loader.putCache(localhostPath,bitmap);
//        imageScaleView.setImageBitmap(bitmap);

        container.addView(imageScaleView);
        return imageScaleView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
