package com.example.fragment;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.example.Bean.FreshNews;
import com.example.Bean.FreshNewsDetailsBean;
import com.example.Bean.ResultSingleBean;
import com.example.R;
import com.example.common.String2TimeUtil;
import com.example.data.DataHelper;
import com.example.data.GetDataHelper;
import com.example.data.VolleyResponseHelper;
import com.example.view.matchview.MatchTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

/**
 * Created by fc on 15/4/24.
 */
@EFragment(R.layout.fragment_fresh_news_detail)
public class FreshNewsDetailFragment extends BaseFragment implements DataHelper.DataListener {
    @FragmentArg
    FreshNews freshNews;
    @ViewById(R.id.webView)
    WebView webView;
    @ViewById(R.id.rl_include)
    RelativeLayout rl_include;
    @ViewById(R.id.tv_loading)
    MatchTextView tv_loading;
    @ViewById(R.id.tv_error)
    MatchTextView tv_error;

    private static String getHtml(FreshNews freshNews, String content) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html dir=\"ltr\" lang=\"zh\">");
        sb.append("<head>");
        sb.append("<meta name=\"viewport\" content=\"width=100%; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" />");
        sb.append("<link rel=\"stylesheet\" href='file:///android_asset/style.css' type=\"text/css\" media=\"screen\" />");
        sb.append("</head>");
        sb.append("<body style=\"padding:0px 8px 8px 8px;\">");
        sb.append("<div id=\"pagewrapper\">");
        sb.append("<div id=\"mainwrapper\" class=\"clearfix\">");
        sb.append("<div id=\"maincontent\">");
        sb.append("<div class=\"post\">");
        sb.append("<div class=\"posthit\">");
        sb.append("<div class=\"postinfo\">");
        sb.append("<h2 class=\"thetitle\">");
        sb.append("<a>");
        sb.append(freshNews.getTitle());
        sb.append("</a>");
        sb.append("</h2>");
        sb.append("<font size=\"-1px\" color=\"#F68C0F\">");
        sb.append(String2TimeUtil
                .dateString2GoodExperienceFormat(freshNews.getDate()));
        sb.append("</font>");
        sb.append(" @" + freshNews.getAuthor().getName());

        sb.append("</div>");
        sb.append("<div class=\"entry\">");
        sb.append(content);

        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    @AfterViews
    void init() {

        initdata(1);
    }

    private void initdata(int tag) {
        // freshNews = (FreshNews) getArguments().getSerializable("FreshNews");
        // 搜索数据
        showView(0);
        DataHelper piclist = new GetDataHelper(FreshNews.getUrlFreshNewsDetail(freshNews.getId())
                , this, tag);
        piclist.execute();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    public void sucess(JSONObject response, int code) {
        ResultSingleBean rb1 = (ResultSingleBean) VolleyResponseHelper
                .jsonToBean(response, 9);
        if (rb1.getRetCode() == 0) {
            showView(1);
            FreshNewsDetailsBean db = (FreshNewsDetailsBean) rb1.getRetObj();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webView.loadDataWithBaseURL("", getHtml(freshNews, db.getContent()), "text/html", "utf-8", "");

        } else {
            showView(2);
        }
    }

    @Override
    public void err(String error, int code) {
        showView(2);
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
}
