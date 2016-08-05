package com.future.shareframe.adapter;

import com.future.shareframe.R;
import com.future.sharelibrary.adapter.RecyclerViewBaseAdapter;
import com.future.sharelibrary.adapter.RecyclerViewBaseParentAdapter;

/**
 * Created by chris zou on 2016/8/3.
 */

public class ResolveSwipeRefreshAdapter extends RecyclerViewBaseAdapter<String> {

    @Override
    public void onBindData(BaseViewHolder holder, int position, String itemData) {
        holder.setText(R.id.launcherTitle,itemData);
    }

    @Override
    public int resultLayoutResId() {
        return R.layout.item_launcher;
    }
}
