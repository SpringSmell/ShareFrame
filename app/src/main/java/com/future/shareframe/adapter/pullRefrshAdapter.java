package com.future.shareframe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.future.shareframe.R;
import com.future.sharelibrary.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public class pullRefrshAdapter extends RecyclerView.Adapter<pullRefrshAdapter.PullRefreshViewHolder> {

    private List<String> contentList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public pullRefrshAdapter(Context context, List<String> contentList) {
        this.contentList = contentList;
        this.mContext = context;
    }

    @Override
    public PullRefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pull_refresh,parent,false);
        PullRefreshViewHolder viewHolder=new PullRefreshViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PullRefreshViewHolder holder, final int position) {
        holder.content.setText(contentList.get(position));
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

    public class PullRefreshViewHolder extends RecyclerView.ViewHolder {

        private TextView content;

        public PullRefreshViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
