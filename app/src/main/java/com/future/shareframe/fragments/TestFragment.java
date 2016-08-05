package com.future.shareframe.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.future.shareframe.R;
import com.future.sharelibrary.adapter.BaseParentViewHolder;
import com.future.sharelibrary.fragments.ShareFragment;

/**
 * Created by Administrator on 2016/6/21.
 */
public class TestFragment extends ShareFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public int resultLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {
        viewHolder.setText(R.id.content, "fragments list", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar(getActivity().getSupportFragmentManager().getFragments().size() + "");
            }
        });
    }

    @Override
    public void initTitle() {
        setTitle("测试Fragment");
        showTitleBar(false);
    }

    public String getTitle() {
        return super.getTitle() + "";
    }
}
