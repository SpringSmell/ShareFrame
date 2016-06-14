package com.future.shareframe.adapter;

import com.future.shareframe.R;
import com.future.sharelibrary.adapter.RecyclerViewBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public class TestRVAdapter extends RecyclerViewBaseAdapter {

    private List<String> contentList;

    public TestRVAdapter(List<String> contentList) {
        this.contentList = contentList;
    }

    @Override
    public List<?> itemCount() {
        return contentList;
    }

    @Override
    public int resultResId() {
        return R.layout.item_pull_refresh;
    }

    @Override
    public void onBindData(BaseViewHolder holder, int position) {
        holder.setText(R.id.content,contentList.get(position));

    }
}
