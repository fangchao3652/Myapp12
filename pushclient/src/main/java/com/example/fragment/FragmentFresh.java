package com.example.fragment;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.Bean.FreshNews;
import com.example.Bean.FreshNewsListBean;
import com.example.Bean.ResultSingleBean;
import com.example.R;
import com.example.adapter.FragmentFreshAdapter;
import com.example.common.CustomToast;
import com.example.data.DataHelper;
import com.example.data.DiskDataHelper;
import com.example.data.GetDataHelper;
import com.example.data.VolleyResponseHelper;
import com.example.view.RecyclerOnScrollListener;
import com.example.view.matchview.MatchTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

/**
 * 新鲜事 Fragment
 * Created by fc on 2015/4/28.
 */
@EFragment(R.layout.frgment_fresh)
public class FragmentFresh extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, DataHelper.DataListener {
    @ViewById(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    LinearLayoutManager linearLayoutManager;
    int PageIndex = 1;
    FreshNewsListBean listdata;
    @ViewById(R.id.rl_include)
    RelativeLayout rl_include;
    @ViewById(R.id.tv_loading)
    MatchTextView tv_loading;
    @ViewById(R.id.tv_error)
    MatchTextView tv_error;

    private FragmentFreshAdapter mAdapter;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
private boolean  loadedfromcachce=false;
    @AfterViews
    void init() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.drawable.wait)
                .build();
        initUI();
        inintByLocalData();
        initData(1);
    }


    private void initUI() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mRecyclerView.setOnScrollListener(new RecyclerOnScrollListener(linearLayoutManager, mRefreshLayout, mRecyclerView) {
            @Override
            public void onLoadMore() {

                if (mAdapter != null && mAdapter.isCanLoadMore()) {
                    loadedfromcachce=false;
                    PageIndex++;
                    initData(2);
                }
            }
        });
    }

    private void initData(int tag) {
        mRefreshLayout.setRefreshing(true);
        // 搜索数据
        DataHelper piclist = new GetDataHelper(FreshNews.getUrlFreshNews(PageIndex)
                , this, tag);
        piclist.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //清除内存缓存，避免由于内存缓存造成的图片显示不完整
        imageLoader.clearMemoryCache();
    }

    private void inintByLocalData() {
        JSONObject jsonObject = DiskDataHelper.getInstance().getListFromCache("fragment_fresh");
        if (jsonObject != null) {
            ResultSingleBean rb1 = (ResultSingleBean) VolleyResponseHelper
                    .jsonToBean(jsonObject, 8);
            if (rb1.getRetCode() == 0) {
                showView(1);
                listdata = (FreshNewsListBean) rb1.getRetObj();
                mAdapter = new FragmentFreshAdapter(listdata.getPosts(), imageLoader, options, getActivity());
                if (listdata.getCount() > listdata
                        .getPosts().size()) {
                    mAdapter.setCanLoadMore(false);
                    mAdapter.setFooterShow(true);
                } else {
                    mAdapter.setCanLoadMore(true);
                }
                mRecyclerView.setAdapter(mAdapter);
                loadedfromcachce=true;
            } else {
                showView(2);

                CustomToast.showToast(rb1.getRetMessage(), getActivity());
            }
        }
    }

    @Override
    public void sucess(JSONObject response, int code) {
        mRefreshLayout.setRefreshing(false);
        switch (code) {
            case 1:


                ResultSingleBean rb1 = (ResultSingleBean) VolleyResponseHelper
                        .jsonToBean(response, 8);
                if (rb1.getRetCode() == 0) {
                    showView(1);
                    DiskDataHelper.getInstance().saveListToCache("fragment_fresh", response);

                    listdata = (FreshNewsListBean) rb1.getRetObj();
                    mAdapter = new FragmentFreshAdapter(listdata.getPosts(), imageLoader, options, getActivity());
                    if (listdata.getCount() > listdata
                            .getPosts().size()) {
                        mAdapter.setCanLoadMore(false);
                        mAdapter.setFooterShow(true);
                    } else {
                        mAdapter.setCanLoadMore(true);
                    }
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    showView(2);

                    CustomToast.showToast(rb1.getRetMessage(), getActivity());
                }
                break;
            case 2:
                ResultSingleBean rb2 = (ResultSingleBean) VolleyResponseHelper
                        .jsonToBean(response, 8);
                if (rb2.getRetCode() == 0) {
                    showView(1);

                    FreshNewsListBean b = (FreshNewsListBean) rb2.getRetObj();

                    if (24 > b
                            .getPosts().size()) {
                        mAdapter.setCanLoadMore(false);
                        mAdapter.setFooterShow(true);
                    } else {
                        mAdapter.setCanLoadMore(true);
                    }
                    if (listdata != null) {
                        //    listdata.setPageInfo(b.getPageInfo());
                        listdata.addBeans(b.getPosts());
                        mAdapter.notifyDataSetChanged();
                    }

                } else {
                    showView(2);

                    CustomToast.showToast(rb2.getRetMessage(), getActivity());
                }
                break;

        }
    }

    @Override
    public void err(String error, int code) {
        mRefreshLayout.setRefreshing(false);
        if(!loadedfromcachce){
        showView(2);
        CustomToast.showToast(error, getActivity());
    }}

    @Override
    public void onRefresh() {
        loadedfromcachce=false;
        PageIndex = 1;
        showView(0);
        initData(1);

    }

    /**
     * 0: 加载中  1：成功 2：error
     *
     * @param m
     */
    void showView(int m) {
        switch (m) {
            case 0:
                rl_include.setVisibility(View.VISIBLE);
                tv_loading.setVisibility(View.VISIBLE);
                tv_error.setVisibility(View.GONE);
                break;
            case 1:
                rl_include.setVisibility(View.GONE);
                break;
            case 2:
                rl_include.setVisibility(View.VISIBLE);
                tv_loading.setVisibility(View.GONE);
                tv_error.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void onActionBarClick() {
        if (mRecyclerView != null && mAdapter.getItemCount() > 0) {
            mRecyclerView.scrollToPosition(0);
        }
    }

}
