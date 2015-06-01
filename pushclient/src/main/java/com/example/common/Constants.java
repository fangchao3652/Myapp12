package com.example.common;

/**
 * 常量类
 *
 * @author zxh
 */
public class Constants {



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
     * sd卡的缓存路径
     */
    public static final String CACHEPATH = "/supuy/cache/images/";
    public static final int BRANDSNUM_FROM_SQLITE = 80;//品牌分类显示的条数
}
