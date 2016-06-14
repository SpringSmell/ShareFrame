package com.future.sharelibrary.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.E;
import com.future.sharelibrary.listener.OnItemClickListener;
import com.future.sharelibrary.tools.ImageUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerViewBaseAdapter.BaseViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private List<?> dataList;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resultResId(), parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(view);
        return viewHolder;
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
        onBindData(holder, position);
    }

    @Override
    public int getItemCount() {
        dataList = itemCount();
        return dataList == null ? 0 : dataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public abstract int resultResId();

    public abstract List<?> itemCount();

    public abstract void onBindData(BaseViewHolder holder, int position);

    public void setDatas(List<?> datas) {
        if (datas != null) {
            this.dataList = datas;
            notifyDataSetChanged();
        } else {
            throw new NullPointerException("The data is null,please check");
        }
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;

        public BaseViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public BaseViewHolder setText(int id, CharSequence content) {
            TextView textView = getView(id);
            textView.setText(content);
            return this;
        }

        public BaseViewHolder setImgRes(int id, int iconId) {
            ImageView imgView = getView(id);
            imgView.setImageResource(iconId);
            return this;
        }

        public BaseViewHolder setImgUrl(int id, String url) {
            ImageUtils.displayImg((ImageView) getView(id), url);
            return this;
        }

    }
}
