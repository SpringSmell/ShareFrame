package com.future.shareframe.adapter;

import com.future.shareframe.R;
import com.future.shareframe.model.TestRVBean;
import com.future.sharelibrary.adapter.RecyclerViewBaseAdapter;

/**
 * Created by chris Zou on 2016/7/6.
 */
public class TestRVAdapter extends RecyclerViewBaseAdapter<TestRVBean> {//<TestRVBean>

    /**
     * 布局文件
     *
     * @return
     */
    @Override
    public int resultResId() {
        return R.layout.item_launcher;
    }

    @Override
    public void onBindData(RecyclerViewBaseViewHolder holder, int position, TestRVBean itemData) {
        holder.setText(R.id.launcherTitle,itemData.title);
    }
}
