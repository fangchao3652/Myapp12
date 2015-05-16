package com.example.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.Bean.BannerListBean;
import com.example.Bean.BaseBean;
import com.example.Bean.NewsListBean;
import com.example.Bean.ResultListBean;
import com.example.Bean.ResultSingleBean;
import com.example.R;
import com.example.activity.NewsDetailsActivity_;
import com.example.adapter.BannerAdapter;
import com.example.adapter.FramgentNewsAdapter;
import com.example.adapter.FramgentRecommandAdapter;
import com.example.common.CommonUtils;
import com.example.common.CustomToast;
import com.example.data.DataHelper;
import com.example.data.DiskDataHelper;
import com.example.data.PostDataHelper;
import com.example.data.URLHelper;
import com.example.data.VolleyResponseHelper;
import com.example.view.CustomScrollView;
import com.example.view.FcScrollView;
import com.example.view.OnItemClickListener;
import com.example.view.RecyclerOnScrollListener;
import com.example.view.viewpager_fc.CycleIndicator;
import com.example.view.viewpager_fc.CycleViewPager;
import com.nineoldandroids.view.ViewHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by fc on 2015/4/27.
 */
@EFragment(R.layout.fragment_recommend)
public class FragmentRecommend extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, DataHelper.DataListener {
    @FragmentArg
    int type = 1;
    @ViewById(R.id.main_scroll)
    FcScrollView main_scroll;

    @ViewById(R.id.rv_news_list)
    RecyclerView mRecyclerView;
    @ViewById(R.id.srl_newslist)
    SwipeRefreshLayout mRefreshLayout;
@ViewById(R.id.banner_rl)
  RelativeLayout  banner_rl;


    @ViewById(R.id.banner_pager)
    CycleViewPager banner_pager;
    @ViewById(R.id.guidance_indicator)
    CycleIndicator guidance_indicator;
    BannerListBean bannerData;//fc 先用的news的数据
    BannerAdapter bannerAdapter;

    LinearLayoutManager linearLayoutManager;
    int PageIndex = 1;
    boolean loadedfromcache = false;
    private NewsListBean listData;
    private FramgentRecommandAdapter mAdapter;
    private OnItemClickListener myListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            NewsDetailsActivity_.intent(getActivity()).id(String.valueOf(listData.getNewsList().get(position).getId())).start();

            //  WebActivity_.intent(getActivity()).webUrl(String.valueOf(listData.getNewsList().get(position).getNewBodyHtml())).start();
            //   getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
            // GoodsDetailActivity_.intent(getActivity()).goodsSN(listData.getGoodsList().get(position).getGoodsSN()).start();
        }
    };

    @AfterViews
    void init() {
        initUI();
        //showView(0);
        initByLocalData();
        initData(0);//焦点图
        initData(1);
    }


    @Click({R.id.view_loading_error})
    void click(View v) {
        switch (v.getId()) {
            case R.id.view_loading_error:
                PageIndex = 1;
               // showView(0);
                initData(1);
                break;
        }
    }

    private void initData(int tag) {
        mRefreshLayout.setRefreshing(true);
        // 搜索数据
        DataHelper piclist = new PostDataHelper(URLHelper.getURL(
                URLHelper.MOUDLE_Newsinfo, URLHelper.M_GetNewsTitle),
                URLHelper.getNewsListParams(PageIndex, type), this, tag);
        piclist.execute();
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
                    loadedfromcache=false;
                    PageIndex++;
                    initData(2);
                }
            }
        });

        main_scroll.setScrollViewListener(new FcScrollView.ScrollviewListener() {
            @Override
            public void onScrollChanged(FcScrollView scrollView, int x, int y, int oldx, int oldy) {
                ViewHelper.setTranslationY(banner_rl, y / 3);
            }
        });


        ViewGroup.LayoutParams mParams1 = banner_pager.getLayoutParams();
        mParams1.width = CommonUtils.getScreenWidthPX(getActivity());
        mParams1.height = CommonUtils.getScreenWidthPX(getActivity()) * 34 / 72;
        banner_pager.setLayoutParams(mParams1);
        banner_pager.setOffscreenPageLimit(5);

    }


    @Override
    public void onResume() {
        super.onResume();
        if(bannerAdapter != null){
            banner_pager.startAutoScroll();
        }
    }

    @Override
    public void onRefresh() {
        loadedfromcache = false;
        PageIndex = 1;
        initData(1);
    }

    private void initByLocalData() {
        JSONObject jsonObject = DiskDataHelper.getInstance().getListFromCache("fragment_newslist");
        if (jsonObject != null) {
            // banner数据成功回调
            ResultSingleBean rb1 = (ResultSingleBean) VolleyResponseHelper
                    .jsonToBean(jsonObject, 6);
            if (rb1.getRetCode() == 0) {
                listData = (NewsListBean) rb1.getRetObj();

                // 判断是否需要到底部自动加载
                mAdapter = new FramgentRecommandAdapter(listData, getActivity());
                if (Integer.valueOf(URLHelper.PAGESIZE) > listData
                        .getNewsList().size()) {
                    mAdapter.setCanLoadMore(false);
                    mAdapter.setFooterShow(true);
                } else {
                    mAdapter.setCanLoadMore(true);
                }
                loadedfromcache = true;
                mAdapter.setOnItemClickListener(myListener);
                mRecyclerView.setAdapter(mAdapter);
            }
            if (listData != null)
                if (listData.getNewsList().size() == 0) {
                 //   showView(1);
                } else {
                  //  showView(3);
                }
        } else {
           // showView(0);
        }


    }

    @Override
    public void sucess(JSONObject response, int code) {
        mRefreshLayout.setRefreshing(false);
        switch (code) {
            case 0:
                // banner数据成功回调
                ResultSingleBean rb10 = (ResultSingleBean) VolleyResponseHelper
                        .jsonToBean(response, 3);//先用的recycleview   的数据
                if (rb10.getRetCode() == 0) {

                    DiskDataHelper.getInstance().saveListToCache("recommend_banner", response);
                    bannerData = (BannerListBean) rb10.getRetObj();
                    bannerAdapter = new BannerAdapter(bannerData.getNewsList(),
                            getFragmentManager());
                    banner_pager.setAdapter(bannerAdapter);
                    banner_pager.startAutoScroll();
                    guidance_indicator.setViewPager(banner_pager);
                }
                break;


            case 1:

                // 下拉刷新数据返回处理
                ResultSingleBean rb1 = (ResultSingleBean) VolleyResponseHelper
                        .jsonToBean(response, 6);
                if (rb1.getRetCode() == 0) {
                    listData = (NewsListBean) rb1.getRetObj();
                    DiskDataHelper.getInstance().saveListToCache("fragment_newslist", response);
                    // 判断是否需要到底部自动加载
                    mAdapter = new FramgentRecommandAdapter(listData, getActivity());
                    if (Integer.valueOf(URLHelper.PAGESIZE) > listData
                            .getNewsList().size()) {
                        mAdapter.setCanLoadMore(false);
                        mAdapter.setFooterShow(true);
                    } else {
                        mAdapter.setCanLoadMore(true);
                    }
                    mAdapter.setOnItemClickListener(myListener);
                    mRecyclerView.setAdapter(mAdapter);
                    refreshRVLayout(listData.getNewsList().size());

                }
                if (listData != null)
                    if (listData.getNewsList().size() == 0) {
                      //  showView(1);
                    } else {
                      //  showView(3);
                    }

                break;
            case 2:
                // 滚动到底部加载数据返回处理
                ResultSingleBean rb2 = (ResultSingleBean) VolleyResponseHelper
                        .jsonToBean(response, 6);
                if (rb2.getRetCode() == 0) {
                    NewsListBean b = (NewsListBean) rb2.getRetObj();

                    // 判断是否需要到底部自动加载
                    if (Integer.valueOf(URLHelper.PAGESIZE) > b.getNewsList()
                            .size()) {
                        mAdapter.setCanLoadMore(false);
                    } else {
                        mAdapter.setCanLoadMore(true);
                    }
                    if (listData != null) {
                        listData.setPageInfo(b.getPageInfo());
                        listData.addNewsBeans(b.getNewsList());
                        mAdapter.notifyDataSetChanged();
                    }
                    refreshRVLayout(listData.getNewsList().size());
                } else {

                    if (mAdapter != null) {
                        mAdapter.setFooterShow(false);
                        mAdapter.notifyDataSetChanged();
                    }

                    CustomToast.showToast(rb2.getRetMessage(), getActivity());
                }
                break;
            default:
                break;
        }
    }

    private void refreshRVLayout(int num) {
        ViewGroup.LayoutParams mParams = mRecyclerView.getLayoutParams();
        mParams.height =  CommonUtils.dipToPixels(280)
        * num + CommonUtils.dipToPixels(80);

        mRecyclerView.setLayoutParams(mParams);
    }


    @Override
    public void err(String error, int code) {
        mRefreshLayout.setRefreshing(false);
        if (!loadedfromcache) {
           // showView(2);
            CustomToast.showToast(error, getActivity());
        }
    }
}
