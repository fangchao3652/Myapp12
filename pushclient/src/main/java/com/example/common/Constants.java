package com.example.common;

/**
 * 常量类
 *
 * @author zxh
 */
public class Constants {

    /**
     * 银联spid
     */
    public static final String SPID = "0281";
    /**
     * 系统运营方ID
     */
    public static final String SYSPROVIDER = "11564520";

    /**
     * log的开关上线时设置为false
     */
    public static final boolean LOGPRINTLN = true;
    /**
     * 是否开发模式上线时设置为false
     */
    public static final boolean ISDEBUG = false;
    /**
     * 友盟统计的开关上线时设置为false
     */
    public static final boolean UMENG = true;

    /**
     * 获取ip错误时使用的默认ip
     */
    public static final String LOCALIPADDRESS = "1.1.1.1";

    /**
     * 用户协议url
     */
    public static final String AGREEMENT_URL = "http://www.supuy.com/agreement.html";

    /**
     * 购买须知
     */
    public static final String NEEDKNOW_URL = "http://buy.api.supuy.com/html/Inform.html";
    /**
     * sd卡的缓存路径
     */
    public static final String CACHEPATH = "/supuy/cache/images/";
    public static final int BRANDSNUM_FROM_SQLITE = 80;//品牌分类显示的条数
}
