package com.example.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.adapter.FooterAdapter;
import com.example.application.MyApplication;


/**
 * 滚动到底部加载更多监听
 *
 * @author fangchao
 */
public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = RecyclerOnScrollListener.class.getSimpleName();
    private RecyclerView.LayoutManager manager;
    //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
    boolean isSlidingToLast = false;
    SwipeRefreshLayout mSwipeLayout;
    RecyclerView recyclerView;

    public RecyclerOnScrollListener(RecyclerView.LayoutManager manager, SwipeRefreshLayout mSwipeLayout, RecyclerView recyclerView) {
        this.manager = manager;
        this.mSwipeLayout = mSwipeLayout;
        this.recyclerView = recyclerView;
    }

    int lastVisibleItem;
    int totalItemCount;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        // 当不滚动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的ItemPosition
            if (manager instanceof LinearLayoutManager) {
                lastVisibleItem = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();
                totalItemCount = manager.getItemCount();
            } else {
                lastVisibleItem = ((GridLayoutManager) manager).findLastCompletelyVisibleItemPosition();
                totalItemCount = manager.getItemCount();
            }

            // 判断是否滚动到底部，并且是向右滚动
            if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                //加载更多功能的代码
                if (mSwipeLayout.isRefreshing()) {

                } else {
                    if (recyclerView.getAdapter() != null && ((FooterAdapter) recyclerView.getAdapter()).isCanLoadMore()) {
                        ((FooterAdapter) recyclerView.getAdapter()).setFooterShow(true);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    } else if (!((FooterAdapter) recyclerView.getAdapter()).isCanLoadMore() && !((FooterAdapter) recyclerView.getAdapter()).isFooterShow()) {
                        ((FooterAdapter) recyclerView.getAdapter()).setFooterShow(true);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                    onLoadMore();
                }
            }
            onScrolllEnd();
        } else {
            onScrolling();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        if (dx > 0 || dy > 0) {
            //大于0表示，正在向右滚动
            isSlidingToLast = true;
        } else {
            //小于等于0 表示停止或向左滚动
            isSlidingToLast = false;
        }

    }


    public abstract void onLoadMore();

    public void onScrolling() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (MyApplication.getInstance().getRequestQueue()) {
                        MyApplication.getInstance().getRequestQueue().wait();
                    }
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }

    public void onScrolllEnd() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (MyApplication.getInstance().getRequestQueue()) {
                        MyApplication.getInstance().getRequestQueue().notifyAll();
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }

}
