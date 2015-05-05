package com.example.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Bean.BaseBean;
import com.example.Bean.NewBaseBean;
import com.example.Bean.NewsListBean;
import com.example.R;
import com.example.view.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by fc on 2015/4/27.
 */
public class FramgentNewsAdapter extends FooterAdapter {

    BaseBean data;// 元数据
    private OnItemClickListener listener;
    private Context mContext;
    public FramgentNewsAdapter(BaseBean data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public boolean useFooter() {
        return isFooterShow();
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslist, parent, false);
        ContentViewHolder vh = new ContentViewHolder(v);
        return vh;
    }

    @Override
    public void onBindContentItemView(RecyclerView.ViewHolder holder, final int position) {
        ContentViewHolder vh = (ContentViewHolder) holder;
        vh.Tv_title.setText(getItem(position).getTitle());//fc
        vh.Tv_source.setText(getItem(position).getSource());//fc
        vh.Tv_time.setText(getItem(position).getPublishTime());//fc
      if(getItem(position).getImageUrl()!=null)
        vh.item_img.setImageURI(Uri.parse(getItem(position).getImageUrl()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getContentItemCount() {
        if (data instanceof NewsListBean) {
            NewsListBean bean = (NewsListBean) data;
            return bean.getNewsList().size();
        } else {
            return 0;
        }
    }

    @Override
    public int getContentItemType(int position) {
        return 0;
    }

    public NewBaseBean getItem(int position) {
        if (data instanceof NewsListBean) {
            NewsListBean bean = (NewsListBean) data;
            return bean.getNewsList().get(position);
        } else
            return null;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * holder
     */
    private class ContentViewHolder extends RecyclerView.ViewHolder {
        public TextView Tv_title;
        public TextView Tv_source;
        public TextView Tv_time;
    public SimpleDraweeView item_img;
        public ContentViewHolder(View itemView) {
            super(itemView);
            Tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            Tv_time = (TextView) itemView.findViewById(R.id.tv_publishtime);
            Tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            item_img= (SimpleDraweeView) itemView.findViewById(R.id.item_img);
        }
    }
}
