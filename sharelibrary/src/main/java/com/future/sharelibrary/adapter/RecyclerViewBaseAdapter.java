package com.future.sharelibrary.adapter;

import android.view.View;

import java.util.List;

/**
 * 基础适配器，其他列表控件适配器可继承，但不建议继承，比如：listView、GridView
 * Created by chris Zou on 2016/6/12.
 */
public abstract class RecyclerViewBaseAdapter<M> extends RecyclerViewBaseParentAdapter {//<M extends RecyclerViewBaseAdapter.BaseBean>
    private List<M> dataList;

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener!=null){
                    mOnItemLongClickListener.onClick(v,position);
                }
                return false;
            }
        });
        onBindData(holder,position,dataList.get(position));
    }

    public abstract void onBindData(BaseViewHolder holder, int position, M itemData);

    public void setDatas(List<M> datas) {
        if (datas != null) {
            this.dataList = datas;
            notifyDataSetChanged();
        } else {
            throw new NullPointerException("The data is null,please check");
        }
    }

    public List<?> getDatas(){
        return dataList;
    }

}
