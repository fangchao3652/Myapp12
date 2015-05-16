package com.example.adapter;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.R;


/**
 * 滚动到底部加载更多Adapter
 *
 * @author FC
 */
public abstract class FooterAdapter extends BaseAnimAdapter {

    public static final int TYPE_FOOTER = Integer.MIN_VALUE;
    private static final int TYPE_ADAPTEE_OFFSET = 2;
    private boolean canLoadMore = false;
    private boolean isFooterShow;

    public FooterAdapter() {

    }

    /**
     * footer 双列显示
     *
     * @param manager
     */
    public FooterAdapter(GridLayoutManager manager) {
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (getItemViewType(position)) {
                    case TYPE_FOOTER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
    }

    /**
     * footer width列显示
     *
     * @param manager
     * @param width
     */
    public FooterAdapter(GridLayoutManager manager, final int width) {
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (getItemViewType(position)) {
                    case TYPE_FOOTER:
                        return width;
                    default:
                        return 1;
                }
            }
        });
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        }
        return onCreateContentItemViewHolder(parent, viewType - TYPE_ADAPTEE_OFFSET);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Log.e("position",position+"   "+getContentItemCount());
        if (position == getContentItemCount() && holder.getItemViewType() == TYPE_FOOTER) {
            onBindFooterView(holder, position);
        } else {
            onBindContentItemView(holder, position);
        }
    }

    private static class ViewHolderFooter extends RecyclerView.ViewHolder {
        public ProgressBar pro;
        public TextView title;

        public ViewHolderFooter(View v) {
            super(v);
            pro = (ProgressBar) v.findViewById(R.id.pro);
            title = (TextView) v.findViewById(R.id.footerTitle);
        }

    }

    @Override
    public final int getItemCount() {
        int itemCount = getContentItemCount();
        if (useFooter()) {
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == getContentItemCount() && useFooter()) {
            return TYPE_FOOTER;
        }
        return getContentItemType(position) + TYPE_ADAPTEE_OFFSET;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public void setFooterShow(boolean isFooterShow) {
        this.isFooterShow = isFooterShow;
    }

    public boolean isFooterShow() {
        return isFooterShow;
    }

    public abstract boolean useFooter();

    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footer_loadmore_view, parent, false);
        ViewHolderFooter vh = new ViewHolderFooter(v);
        return vh;
    }

    public void onBindFooterView(RecyclerView.ViewHolder holder, int position) {
        ViewHolderFooter footerHolder = (ViewHolderFooter) holder;
        if (isCanLoadMore()) {
            footerHolder.pro.setVisibility(View.VISIBLE);
            footerHolder.title.setText("加载更多");
        } else {
            footerHolder.pro.setVisibility(View.GONE);
            footerHolder.title.setText("当前已是最后一条");
        }

    }

    public abstract RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindContentItemView(RecyclerView.ViewHolder holder, int position);

    public abstract int getContentItemCount();

    public abstract int getContentItemType(int position);
}
