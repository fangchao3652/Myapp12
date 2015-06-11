package com.example.data;

import android.util.Log;

import com.example.common.CommonUtils;
import com.example.common.Constants;
import com.example.common.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * 网络请求url组装辅助类
 *
 * @author fc
 */
public class URLHelper {

    public static final String BALSEURL = "http://172.28.1.9:81/api/";
    //public static final String BALSEURL = "http://115.28.131.31:81/api/";
    //public static final String BALSEURL = "http://hagal.theadventus.com/MEASystem/api/";
    // public static final String BALSEURL ="http://10.1.108.131/api/student/";

    public static final String TESTPUSH = "192.168.1.61";
    /**
     * 线上推送url
     */
    public static final String ONLINEPUSH = "soft.fit-find.com";
    /**
     * 项目中使用的推送地址
     */
    public static final String BALSEPUSH = Constants.ISDEBUG ? TESTPUSH
            : ONLINEPUSH;

    public static final String VERSION = "";
////////////////////////////////////////fangfa
    public static final String M_LOGIN = "UserLogin";// 登陆
    public static final String M_REGISTER = "Register";// 注册
    public static final String M_GetNewsTitle = "GetNewsTitle";// 新闻列表
    public static final String M_GetNewsDetails = "GetNewsModel";// 新闻列表

    public static final String MOUDLE_Student = "student/";//模块
    public static final String MOUDLE_Newsinfo = "NewsInfo/";//模块



    public static final String P_SIGN = "sign";
    public static final String P_METHOD = "method";
    public static final String P_ACCOUNT = "sNo";
    public static final String P_CONTENT = "Content";
    public static final String P_PASSWORD = "sPwd";
    public static final String P_PAGEINDEX = "PageIndex";
    public static final String P_PAGESIZE = "PageSize";
    public static final String P_DeviceId = "deviceId";
    public static final String PAGESIZE = "20";// 默认的一页条数
    private static final String TOKEN = "3B51ACFFC9244DC481CF9454E207429A";
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static Map<String, String> params;

    /**
     * 获取基本参数
     *
     * @param method 方法名
     * @return
     */
    public static Map<String, String> getBaseParams(String method) {
        params = new TreeMap<String, String>();
        params.put(P_SIGN, md5(getSign(method, params)));
        params.put(P_METHOD, method);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取url
     *
     * @param module 模块
     * @param method 方法名
     * @return
     */
    public static String getURL(String module, String method) {
        CommonUtils.logWrite("URL", BALSEURL + VERSION + module + method);
        return BALSEURL + VERSION + module + method;
    }

    public static String getURL(String version, String module, String method) {
        CommonUtils.logWrite("URL", BALSEURL + version + module + method);
        return BALSEURL + version + module + method;
    }

    /**
     * 获取登陆参数
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    public static Map<String, String> getLoginParams(String account,
                                                     String password, String p_DeviceId) {
        params = new TreeMap<String, String>();
        params.put(P_ACCOUNT, account);
        params.put(P_PASSWORD, password);
        // params.put(P_SIGN, md5(getSign(M_LOGIN, params)));
        // params.put(P_METHOD, M_LOGIN);
        params.put(P_DeviceId, p_DeviceId);

        CommonUtils.logWrite("params:", params.toString());
        return params;
    }


    public static Map<String,String> getNewsDetailParams(String id) {
        params = new TreeMap<String, String>();
        params.put("newId", String.valueOf(id));
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }
    public static Map<String, String> getNewsListParams(int pageIndex ,int type) {
        params = new TreeMap<String, String>();
        params.put("pageIndex", String.valueOf(pageIndex));
        params.put("newType", String.valueOf(type));
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }


    public static Map<String, String> getCourseListParams(String account,String week) {
        params = new TreeMap<String, String>();
        params.put("MemberId", account);
        params.put("Week", week);
        CommonUtils.logWrite("params:getCourseListParams ", params.toString());
        return params;
    }

    /**
     * 注册
     *
     * @param account
     * @param password
     * @param recommend
     * @param email
     * @param BabyBirthday
     * @return
     */
    public static Map<String, String> getRegisterParams(String account,
                                                        String password, String recommend, String email, String BabyBirthday) {
        params = new TreeMap<String, String>();
        params.put(P_ACCOUNT, account);
        params.put(P_PASSWORD, password);

        params.put("Recommend", recommend);
        params.put(P_SIGN, md5(getSign(M_REGISTER, params)));
        params.put(P_METHOD, M_REGISTER);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取sign加密字符串
     *
     * @param method
     * @param p
     * @return
     */
    private static String getSign(String method, Map<String, String> p) {
        StringBuffer str = new StringBuffer();
        str.append("method=" + StringUtils.utf8Encode(method));
        if (p != null && !p.isEmpty()) {
            // 有参数
            for (Entry<String, String> entry : p.entrySet()) {
                str.append("&" + entry.getKey() + "=" + entry.getValue());
            }

        }
        str.append("&token=" + StringUtils.utf8Encode(TOKEN));
        return str.toString().toLowerCase();
    }

    /**
     * 组装url私用
     *
     * @param url
     * @param params
     * @return
     */
    private static String paramsToUrl(String url, Map<String, Object> params) {
        String paramStr = "";

        // 如果prams不为空
        if (null != params) {
            Iterator<Entry<String, Object>> iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, Object> entry = (Entry<String, Object>) iter
                        .next();
                String key = entry.getKey();
                Object val = entry.getValue();
                paramStr += paramStr = "&" + key + "=" + val;
            }
        }

        if (!StringUtils.isBlank(paramStr)) {
            paramStr = paramStr.replaceFirst("&", "?");
            url += paramStr;
        }
        if (Constants.LOGPRINTLN) {
            Log.w("URL-", url);
        }
        return url;
    }

    /**
     * MD5加密字符
     */
    private static String md5(String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(source.getBytes());
            byte[] mess = digest.digest();
            return toHexString(mess);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return source;

    }

    private static String toHexString(byte[] b) { // byte to String
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }


}
