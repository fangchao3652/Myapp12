package com.example.data;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.application.MyApplication;
import com.example.common.CommonUtils;

import org.json.JSONObject;

import java.util.Map;


/**
 * post方式请求数据
 *
 * @author Zxh
 *
 */
public class PostDataHelper implements DataHelper {
    private String url;
    private Map<String, String> params;
    private DataListener listener;
    private int code;
    // 请求是否被取消
    private boolean isCancel = false;

    /**
     * 构造函数
     *
     * @param url
     *            请求的url
     * @param params
     *            参数
     * @param listener
     *            数据监听 可以为null
     * @param code
     *            返回标识
     */
    public PostDataHelper(String url, Map<String, String> params,
                          DataListener listener, int code) {
        this.url = url;
        this.params = params;
        this.listener = listener;
        this.code = code;
    }

    @Override
    public String getURL() {
        return this.url;
    }

    @Override
    public Map<String, String> getParams() {
        return this.params;
    }

    @Override
    public Request execute() {
        JsonObjectPostRequestWithHeaders bannerReq = new JsonObjectPostRequestWithHeaders(
                getURL(), getParams(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CommonUtils.logWrite("sucess--", response.toString());
                if (listener != null && !isCancel) {
                    listener.sucess(response, code);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtils.logWrite("Error--", error.toString());
                if (listener != null && !isCancel) {
                    listener.err(VolleyErrorHelper.getMessage(error),
                            code);
                }
            }
        });

        bannerReq.setTag(getURL());
        bannerReq.setRetryPolicy(new DefaultRetryPolicy(
                VolleyResponseHelper.INITIALTIMEOUTMS, 1, 1.0f));
        return MyApplication.getInstance().getRequestQueue().add(bannerReq);
    }

    @Override
    public Request execute(int timeoutms) {
        JsonObjectPostRequestWithHeaders bannerReq = new JsonObjectPostRequestWithHeaders(
                getURL(), getParams(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CommonUtils.logWrite("sucess--", response.toString());
                if (listener != null && !isCancel) {
                    listener.sucess(response, code);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtils.logWrite("Error--", error.toString());
                if (listener != null && !isCancel) {
                    listener.err(VolleyErrorHelper.getMessage(error),
                            code);
                }
            }
        });

        bannerReq.setTag(getURL());
        bannerReq.setRetryPolicy(new DefaultRetryPolicy(timeoutms, 1, 1.0f));
        return MyApplication.getInstance().getRequestQueue().add(bannerReq);
    }

    @Override
    public void cancel() {
        isCancel = true;
        MyApplication.getInstance().getRequestQueue().cancelAll(getURL());
    }
}
