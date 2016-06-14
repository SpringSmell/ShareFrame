package com.future.sharelibrary.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.future.sharelibrary.R;

/**
 * Created by Administrator on 2016/6/12.
 */
public class MapFragment extends BaseFragment {

    protected View layoutView;
    protected BaiduMap mBaiduMap;
    protected MapView mMapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layoutView==null){
            layoutView=inflater.inflate(R.layout.fragment_bd_map,container,false);
        }
        ViewGroup viewGroup= (ViewGroup) container.getParent();
        if(viewGroup!=null){
            viewGroup.removeAllViews();
        }
        initView();
        bindData();
        return layoutView;
    }

    public void initView() {
        mMapView= (MapView) layoutView.findViewById(R.id.baiduMap);
        if(mMapView!=null){
            mBaiduMap=mMapView.getMap();
        }
    }

    public void bindData() {

    }
}
