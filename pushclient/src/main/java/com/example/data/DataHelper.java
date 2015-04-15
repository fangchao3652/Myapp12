package com.example.data;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.Map;

/**
 * 获取数据接口
 *
 * @author Zxh
 *
 */
public interface DataHelper {
    /**
     * 获取数据成功失败监听
     *
     * @author Zxh
     *
     */
    public interface DataListener {
        public void sucess(JSONObject response, int code);

        public void err(String error, int code);
    }

    /**
     * 获取请求的url
     *
     * @return
     */
    public String getURL();

    /**
     * 获取请求的参数
     *
     * @return
     */
    public Map<String, String> getParams();

    /**
     * 执行请求
     *
     * @return
     */
    public Request execute();

    /**
     * 执行请求
     *
     * @param timeoutms
     * @return
     */
    public Request execute(int timeoutms);

    /**
     * 取消请求
     *
     * @return
     */
    public void cancel();

}
