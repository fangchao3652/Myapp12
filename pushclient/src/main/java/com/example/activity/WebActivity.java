package com.example.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.R;
import com.example.common.StringUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;


/**
 * 链接地址页面
 */
@EActivity(R.layout.activity_web)
public class WebActivity extends BaseActionBarActivity {

    @Extra
    String title;
    @Extra
    String webUrl;

    @ViewById(R.id.webView)
    WebView webView;
    @ViewById(R.id.view_loading)
    RelativeLayout view_loading;//加载视图
    @ViewById(R.id.view_loading_default)
    LinearLayout view_loading_default;
    @ViewById(R.id.view_loading_error)
    LinearLayout view_loading_error;
    @ViewById(R.id.view_loading_empty)
    LinearLayout view_loading_empty;


    @AfterViews
    void initData() {
        if (!StringUtils.isBlank(title)) {
            setTitle(title);
        } else {
            setTitle("详情");
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setInitialScale(25);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showView(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showView(3);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                showView(1);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        });
        webView.loadUrl(webUrl);
    }

    @Click({R.id.view_loading_error})
    void click(View view) {
        switch (view.getId()) {
            case R.id.view_loading_error:
                webView.loadUrl(webUrl);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 切换视图
     *
     * @param type 0:加载中 1:加载失败 2:内容为空 3:加载成功
     */
    private void showView(int type) {
        switch (type) {
            case 0:
                view_loading_default.setVisibility(View.VISIBLE);
                view_loading_error.setVisibility(View.GONE);
                view_loading_empty.setVisibility(View.GONE);
                break;
            case 1:
                view_loading_default.setVisibility(View.GONE);
                view_loading_error.setVisibility(View.GONE);
                view_loading_empty.setVisibility(View.VISIBLE);
                break;
            case 2:
                view_loading_default.setVisibility(View.GONE);
                view_loading_error.setVisibility(View.VISIBLE);
                view_loading_empty.setVisibility(View.GONE);
                break;
            case 3:
                view_loading.setVisibility(View.GONE);
                break;
        }
    }
}
