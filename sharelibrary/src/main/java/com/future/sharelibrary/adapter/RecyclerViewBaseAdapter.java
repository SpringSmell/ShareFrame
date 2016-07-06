package com.future.sharelibrary.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.future.sharelibrary.model.BaseBean;
import com.future.sharelibrary.tools.ImageUtils;

import java.util.List;

/**
 * 基础适配器，其他列表控件适配器可继承，但不建议继承，比如：listView、GridView
 * Created by chris Zou on 2016/6/12.
 */
public abstract class  RecyclerViewBaseAdapter<B extends BaseBean> extends RecyclerView.Adapter<RecyclerViewBaseAdapter.RecyclerViewBaseViewHolder> {//<M extends RecyclerViewBaseAdapter.BaseBean>

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private List<B> dataList;
    @Override
    public RecyclerViewBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resultResId(), parent, false);
        RecyclerViewBaseViewHolder viewHolder = new RecyclerViewBaseViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(RecyclerViewBaseViewHolder holder, final int position) {
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
        onBindData(holder, position,dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 布局文件
     * @return
     */
    public abstract int resultResId();

    public abstract void onBindData(RecyclerViewBaseViewHolder holder, int position,B itemData);

    public void setDatas(List<B> datas) {
        if (datas != null) {
            this.dataList = datas;
            notifyDataSetChanged();
        } else {
            throw new NullPointerException("The data is null,please check");
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener=onItemLongClickListener;
    }

    public List<?> getDatas(){
        return dataList;
    }

    public class RecyclerViewBaseViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;

        public RecyclerViewBaseViewHolder(View rootView) {
            super(rootView);
            this.mViews = new SparseArray<>();
        }

        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public RecyclerViewBaseViewHolder setText(int id, CharSequence content) {
            return setText(id, content, null);
        }

        public RecyclerViewBaseViewHolder setText(int id, CharSequence content, View.OnClickListener onClickListener) {
            TextView textView = getView(id);
            textView.setText(content);
            textView.setOnClickListener(onClickListener);
            return this;
        }

        public RecyclerViewBaseViewHolder setImgRes(int id, int iconId) {
            return setImg(id, "",iconId, null);
        }

        public RecyclerViewBaseViewHolder setImgRes(int id, int iconId, View.OnClickListener onClickListener) {
            return setImg(id,"",iconId,onClickListener);
        }

        public RecyclerViewBaseViewHolder setImgUrl(int id, CharSequence url) {
            return setImg(id, url,0, null);
        }

        public RecyclerViewBaseViewHolder setImgUrl(int id, CharSequence url,View.OnClickListener onClickListener) {
            return setImg(id, url,0, onClickListener);
        }

        public RecyclerViewBaseViewHolder setOnClickListener(int id,View.OnClickListener onClickListener){
            getView(id).setOnClickListener(onClickListener);
            return this;
        }

        private RecyclerViewBaseViewHolder setImg(int id, CharSequence url, int iconId, View.OnClickListener onClickListener) {
            ImageView img = getView(id);
            if (!TextUtils.isEmpty(url))
                ImageUtils.displayImg(img, url+"");
            if(iconId!=0)
                img.setImageResource(iconId);
            img.setOnClickListener(onClickListener);
            return this;
        }
    }

    /**
     * 这里应该放模型通用字段
     */
    public static class BaseBean{
        public long id;
        public String title;
    }

    public interface OnItemClickListener {

        void onClick(View v, int position);
    }

    public interface OnItemLongClickListener {

        void onClick(View v, int position);
    }
}
