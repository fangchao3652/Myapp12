package com.example.activity;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Bean.NewsDetailsBean;
import com.example.Bean.ResultSingleBean;
import com.example.R;
import com.example.common.String2TimeUtil;
import com.example.data.DataHelper;
import com.example.data.PostDataHelper;
import com.example.data.URLHelper;
import com.example.data.VolleyResponseHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fc on 2015/4/27.
 */
@EActivity(R.layout.activity_newsdatail)
public class NewsDetailsActivity extends BaseActionBarActivity implements DataHelper.DataListener {
    @Extra
    String id;
    @ViewById(R.id.webView)
    WebView webView;

    NewsDetailsBean detailsBean;
    @ViewById(R.id.view_loading)
    RelativeLayout view_loading;//加载视图
    @ViewById(R.id.view_loading_default)
    LinearLayout view_loading_default;
    @ViewById(R.id.view_loading_error)
    LinearLayout view_loading_error;
    @ViewById(R.id.view_loading_empty)
    LinearLayout view_loading_empty;
    @ViewById(R.id.tv_loading_empty)
    TextView tv_loading_empty;
    private NetworkImageGetter mImageGetter;

    private static String getHtml(NewsDetailsBean bean, String content) {
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
        sb.append(bean.getTitle());
        sb.append("</a>");
        sb.append("</h2>");


        sb.append("<font size=\"-1px\" color=\"#F68C0F\">");
        sb.append(String2TimeUtil
                .dateString2GoodExperienceFormat(bean.getPublishTime()));
        sb.append("</font>");
        sb.append(" 来自" + bean.getAuthor());

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
        showView(0);
        initdata(1);
        // mImageGetter = new NetworkImageGetter();
    }

    @Click({R.id.view_loading_error})
    void click(View v) {
        switch (v.getId()) {
            case R.id.view_loading_error:

                showView(0);
                initdata(1);
                break;
        }
    }

    private void initdata(int tag) {
        DataHelper piclist = new PostDataHelper(URLHelper.getURL(
                URLHelper.MOUDLE_Newsinfo, URLHelper.M_GetNewsDetails),
                URLHelper.getNewsDetailParams(id), this, tag);
        piclist.execute();
    }

    @Override
    public void sucess(JSONObject response, int code) {
        ResultSingleBean rb1 = (ResultSingleBean) VolleyResponseHelper
                .jsonToBean(response, 7);
        if (rb1.getRetCode() == 0) {
            detailsBean = (NewsDetailsBean) rb1.getRetObj();
            if (detailsBean != null) {
               /* detatil_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                detatil_tv.setText(Html.fromHtml(detailsBean.getNewBodyHtml(),mImageGetter,null));*/
                showView(3);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webView.loadDataWithBaseURL("", getHtml(detailsBean, detailsBean.getNewBodyHtml()), "text/html", "utf-8", "");

                Log.e("newsdetails", detailsBean.getNewBodyHtml());
            }
        }
    }

    /**
     * 切换视图
     *
     * @param type 0:加载中 1:加载为空 2:内容失败 3:加载成功
     */
    private void showView(int type) {
        switch (type) {
            case 0:
                view_loading_default.setVisibility(View.VISIBLE);
                view_loading_error.setVisibility(View.GONE);
                view_loading_empty.setVisibility(View.GONE);
                view_loading.setVisibility(View.VISIBLE);
                break;
            case 1:
                view_loading_default.setVisibility(View.GONE);
                view_loading_error.setVisibility(View.GONE);
                view_loading_empty.setVisibility(View.VISIBLE);
                view_loading.setVisibility(View.VISIBLE);
                break;
            case 2:
                view_loading_default.setVisibility(View.GONE);
                view_loading_error.setVisibility(View.VISIBLE);
                view_loading_empty.setVisibility(View.GONE);
                view_loading.setVisibility(View.VISIBLE);
                break;
            case 3:
                view_loading.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void err(String error, int code) {
        showView(2);
    }

    private final class NetworkImageGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String source) {

            Drawable drawable = null;
            String picName = source.substring(source.lastIndexOf("/") + 1);
            // 封装路径
            File file = new File(getCacheDir().getAbsolutePath(), picName);
            // 判断是否以http开头
            if (source.startsWith("http")) {
                // 判断路径是否存在
                if (file.exists()) {
                    // 存在即获取drawable
                    drawable = Drawable.createFromPath(file.getAbsolutePath());
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                } else {
                    // 不存在即开启异步任务加载网络图片
                    AsyncLoadNetworkPic networkPic = new AsyncLoadNetworkPic();
                    networkPic.execute(source);
                }
            }
            return drawable;
        }
    }


    private final class AsyncLoadNetworkPic extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... params) {
            // 加载网络图片
            loadNetPic(params);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // 当执行完成后再次为其设置一次
            // detatil_tv.setText(Html.fromHtml(detailsBean.getNewBodyHtml(), mImageGetter, null));
        }

        /**
         * 加载网络图片
         */
        private void loadNetPic(String... params) {
            String path = params[0];
            String picName = path.substring(path.lastIndexOf("/") + 1);

            File file = new File(getCacheDir().getAbsolutePath(), picName);
            //File file = new File(Environment.getExternalStorageDirectory(), picName);

            InputStream in = null;

            FileOutputStream out = null;

            try {
                URL url = new URL(path);

                HttpURLConnection connUrl = (HttpURLConnection) url.openConnection();

                connUrl.setConnectTimeout(5000);

                connUrl.setRequestMethod("GET");

                if (connUrl.getResponseCode() == 200) {

                    in = connUrl.getInputStream();

                    out = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];

                    int len;

                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } else {
                    Log.i(TAG, connUrl.getResponseCode() + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
