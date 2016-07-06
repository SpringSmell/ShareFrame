package com.future.shareframe.fragments;


import com.future.shareframe.R;
import com.future.sharelibrary.adapter.BaseViewHolder;
import com.future.sharelibrary.fragments.BaseFragment;

/**
 * Created by Administrator on 2016/6/21.
 */
public class TestFragment extends BaseFragment {

    @Override
    public int resultLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void onBindData(BaseViewHolder viewHolder) {

    }

    @Override
    public void initTitle() {
        super.initTitle();
        showTitle(false);
    }
}
