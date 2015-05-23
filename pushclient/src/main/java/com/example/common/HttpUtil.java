package com.example.common;

/**
 * Created by supumall on 2015/5/21.
 */



/**
 * Http请求工具类
 * @author lizhangqu
 * @date 2015-2-1
 */

/**
 * @author Administrator
 */
public class HttpUtil {
   /* // Host地址
    public static final String HOST = "211.87.155.19";
    // 基础地址
    public static final String URL_BASE = "http://211.87.155.19/(pnottoey51t1tf45b0ullojg)";
    // 验证码地址
    public static final String URL_CODE = "http://211.87.155.19/(pnottoey51t1tf45b0ullojg)/CheckCode.aspx";
    // 登陆地址
    public static final String URL_LOGIN = "http://211.87.155.19/(pnottoey51t1tf45b0ullojg)/Default2.aspx";
    // 登录成功的首页
    public static String URL_MAIN = "http://211.87.155.19/(pnottoey51t1tf45b0ullojg)/xs_main.aspx?xh=1108010224";
    // 请求地址
    public static String URL_QUERY = "http://211.87.155.19/(pnottoey51t1tf45b0ullojg)/QUERY";
    *//**
     * 请求参数
     *//*
    public static String Button1 = "";
    public static String hidPdrs = "";
    public static String hidsc = "";
    public static String lbLanguage = "";
    public static String RadioButtonList1 = "学生";
    public static String __VIEWSTATE = "dDwyODE2NTM0OTg7Oz5K4/a3o5bbNExGrhnw9E1HvQLfjg==";
    public static String TextBox2 = "371328199212061031";
    public static String txtSecretCode = null;
    public static String txtUserName = "1108010224";

    // 静态初始化
    static {
        client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
        // 设置请求头
        client.addHeader("Host", HOST);
        client.addHeader("Referer", URL_LOGIN);
        client.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
    }

    private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象

    *//**
     * get,用一个完整url获取一个string对象
     *
     * @param urlString
     * @param res
     *//*
    public static void get(String urlString, AsyncHttpResponseHandler res) {
        client.get(urlString, res);
    }

    *//**
     * get,url里面带参数
     *
     * @param urlString
     * @param params
     * @param res
     *//*
    public static void get(String urlString, RequestParams params,
                           AsyncHttpResponseHandler res) {
        client.get(urlString, params, res);
    }

    *//**
     * get,下载数据使用，会返回byte数据
     *
     * @param uString
     * @param bHandler
     *//*
    public static void get(String uString, BinaryHttpResponseHandler bHandler) {
        client.get(uString, bHandler);
    }

    *//**
     * post,不带参数
     *
     * @param urlString
     * @param res
     *//*
    public static void post(String urlString, AsyncHttpResponseHandler res) {
        client.post(urlString, res);
    }

    *//**
     * post,带参数
     *
     * @param urlString
     * @param params
     * @param res
     *//*
    public static void post(String urlString, RequestParams params,
                            AsyncHttpResponseHandler res) {
        client.post(urlString, params, res);
    }

    *//**
     * post,返回二进制数据时使用，会返回byte数据
     *
     * @param uString
     * @param bHandler
     *//*
    public static void post(String uString, BinaryHttpResponseHandler bHandler) {
        client.post(uString, bHandler);
    }

    *//**
     * 返回请求客户端
     *
     * @return
     *//*
    public static AsyncHttpClient getClient() {
        return client;
    }

    *//**
     * 获得登录时所需的请求参数
     *
     * @return
     *//*
    public static RequestParams getLoginRequestParams() {
        // 设置请求参数
        RequestParams params = new RequestParams();
        params.add(__VIEWSTATE, __VIEWSTATE);
        params.add(Button1, Button1);
        params.add(hidPdrs, hidPdrs);
        params.add(hidsc, hidsc);
        params.add(lbLanguage, lbLanguage);
        params.add(RadioButtonList1, RadioButtonList1);
        params.add(TextBox2, TextBox2);
        params.add(txtSecretCode, txtSecretCode);
        params.add(txtUserName, txtUserName);
        return params;
    }

    *//**
     * 登录后查询信息封装好的函数
     *
     * @param context
     * @param linkService
     * @param urlName
     * @param callback
     *//*
    public static void getQuery(final Context context, LinkService linkService,
                                final String urlName, final QueryCallback callback) {
        final MaterialDialog dialog = new MaterialDialog.Builder(context)

                .content("正在获取" + urlName).build();
        ;


        dialog.show();
        String link = linkService.getLinkByName(urlName);
        if (link != null) {
            HttpUtil.URL_QUERY = HttpUtil.URL_QUERY.replace("QUERY", link);
        } else {
            Toast.makeText(context, "链接出现错误", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtil.getClient().addHeader("Referer", HttpUtil.URL_MAIN);
        HttpUtil.getClient().setURLEncodingEnabled(true);
        HttpUtil.get(HttpUtil.URL_QUERY, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                if (callback != null) {
                    callback.handleResult(arg2);
                }
                Toast.makeText(context, urlName + "获取成功！！！", Toast.LENGTH_LONG)
                        .show();
                dialog.dismiss();
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                dialog.dismiss();
                Toast.makeText(context, urlName + "获取成功！！！", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    *//**
     * 接口回调
     *
     * @author lizhangqu
     *         <p/>
     *         2015-2-22
     *//*
    public interface QueryCallback {
        public String handleResult(byte[] result);
    }*/
}