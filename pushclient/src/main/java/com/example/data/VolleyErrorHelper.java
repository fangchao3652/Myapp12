package com.example.data;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.application.MyApplication;
import com.example.fc.R;

import org.json.JSONObject;


/**
 * volley Http请求ERROR辅助类
 *
 * AuthFailureError：如果在做一个HTTP的身份验证，可能会发生这个错误。
 * NetworkError：Socket关闭，服务器宕机，DNS错误都会产生这个错误。
 * NoConnectionError：和NetworkError类似，这个是客户端没有网络连接。
 * ParseError：在使用JsonObjectRequest或JsonArrayRequest时，如果接收到的JSON是畸形，会产生异常。
 * SERVERERROR：服务器的响应的一个错误，最有可能的4xx或5xx HTTP状态代码。
 * TimeoutError：Socket超时，服务器太忙或网络延迟会产生这个异常
 * 。默认情况下，Volley的超时时间为2.5秒。如果得到这个错误可以使用RetryPolicy。
 *
 * @author xiaohao
 *
 */
public class VolleyErrorHelper {
    /**
     * 获取错误信息，用于提示用户
     *
     * @param error
     * @return
     */
    public static String getMessage(VolleyError error) {
        if (error instanceof TimeoutError) {
            return MyApplication.getInstance().getString(
                    R.string.volley_error_timeout);
        } else if (isNetworkProblem(error)) {
            return handleServerError(error);
        }

        else if (error instanceof ParseError) {
            return MyApplication.getInstance().getString(
                    R.string.volley_error_parse);
        } else if (isServerProblem(error)) {
            return handleServerError(error);
        }
        return MyApplication.getInstance().getString(R.string.generic_error);
    }

    /**
     * 确定错误是否与网络有关
     *
     * @param error
     * @return
     */
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError)
                || (error instanceof NoConnectionError);
    }

    /**
     * 确定错误是否与服务器有关
     *
     * @param error
     * @return
     */
    private static boolean isServerProblem(VolleyError error) {
        return (error instanceof ServerError)
                || (error instanceof AuthFailureError);
    }

    /**
     * 处理服务器错误异常信息
     *
     * @param error
     * @return String
     */
    private static String handleServerError(VolleyError error) {

        NetworkResponse response = error.networkResponse;
        if (error instanceof NoConnectionError) {
            return MyApplication.getInstance().getString(
                    R.string.volley_error_noConnection);
        }

        if (response != null) {
            switch (response.statusCode) {
                case 404:
                    return MyApplication.getInstance().getString(
                            R.string.volley_error_network);
                case 403:
                    return MyApplication.getInstance().getString(
                            R.string.volley_error_403);
                case 422:
                    break;
                case 401:
                    try {

                        JSONObject result = new JSONObject(response.data.toString());

                        if (result != null && result.has("error")) {
                            return result.optString("error");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return error.getMessage();
                default:
                    return MyApplication.getInstance().getString(
                            R.string.generic_server_down);
            }
        }
        return MyApplication.getInstance().getString(R.string.generic_error);
    }
}
