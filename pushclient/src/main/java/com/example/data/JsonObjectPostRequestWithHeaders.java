package com.example.data;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.common.CommonUtils;
import com.example.common.SharedPreferencesUtils;
import com.example.common.StringUtils;

import org.apache.http.client.methods.HttpHead;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.ContentHandler;
import java.util.HashMap;
import java.util.Map;



/**
 * 自定义请求头的postRequest
 *
 * @author Zxh
 */
public class JsonObjectPostRequestWithHeaders extends Request<JSONObject> {
    private Map<String, String> mMap;
    private Listener<JSONObject> mListener;

    /**
     * @param url           请求的url
     * @param map           请求的参数
     * @param listener      请求正确数据监听
     * @param errorListener 请求错误数据监听
     */
    public JsonObjectPostRequestWithHeaders(String url,
                                            Map<String, String> map, Listener<JSONObject> listener,
                                            ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mMap = map;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
      Map<String, String> headers = new HashMap<String, String>();
       //headers.put("charset", HTTP.UTF_8);

//        headers.put("Language",
//                StringUtils.utf8Encode(CommonUtils.getLanguage()));// 语言
//       /* headers.put("MemberId", SharedPreferencesUtils.getInstance()
//                .getUserId());// 用户id
//        headers.put("Platform", "android");// 客户端类型（iphone or android）
//        headers.put("Udid",
//                StringUtils.utf8Encode(CommonUtils.getDeviceId()));// 设备号，symbian和android有可能返回的是imei。
//        headers.put("PhoneModel",
//                StringUtils.utf8Encode(CommonUtils.getPhoneModel()));// 手机型号
//        headers.put("Imsi",
//                StringUtils.utf8Encode(StringUtils.isBlank(CommonUtils.getSubscriberId()) ?
//                        "" : CommonUtils.getSubscriberId()));// 手机sim卡标示
//        headers.put("Imei",
//                StringUtils.utf8Encode(CommonUtils.getDeviceId()));// 手机硬件识别号
//        headers.put("Source",
//                StringUtils.utf8Encode(CommonUtils.getSource()));// 客户端来源标识
//
//        headers.put("Operator",
//                StringUtils.utf8Encode(CommonUtils.getOperatorName()));// 运营商信息
//        headers.put("SmsNumber", "");// 获取短信中心号码（暂获取不到）
//        headers.put("ScreenSize",
//                StringUtils.utf8Encode(CommonUtils.getScreenSize()));// 屏幕分辨率
//        headers.put("ApiVersion", StringUtils.utf8Encode(URLHelper.URLCODE));// 接口版本号
//        headers.put("ClientVersion",
//                StringUtils.utf8Encode(CommonUtils.getAppVersionName()));// 客户端版本号*/
        return super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }
    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/x-www-form-urlencoded;charset=%s", PROTOCOL_CHARSET);
    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }
}
