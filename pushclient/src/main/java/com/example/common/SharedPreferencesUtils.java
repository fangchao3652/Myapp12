package com.example.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.Bean.UserBean;
import com.example.application.MyApplication;

import java.util.Set;
import java.util.TreeSet;


/**
 * SharedPreferences保存信息共同
 *
 * @author fc
 */
public class SharedPreferencesUtils {

    /************************************************************************/
    /* SharedPreferences Key */
    /**
     * ********************************************************************
     */

    public static final String SHAREDPREFERENCES = "supuy_";// 2.0版本sharedPreferenceskey前缀
    public static final String LOGIN_USER_NAME = SHAREDPREFERENCES
            + "user_name";// 用户昵称key 默认值“”
    public static final String LOGIN_USER_ACCOUNT = SHAREDPREFERENCES
            + "user_account";// 用户name key 默认值“”

    public static final String LOGIN_USER_PWD = SHAREDPREFERENCES + "user_pwd";// 用户登录密码key默认值“”
    public static final String ISSAVE_PW = SHAREDPREFERENCES + "issave_pw";// 是否保存密码
    public static final String LOGIN_USER_ID = SHAREDPREFERENCES + "user_id";// 用户idkey
    // 默认值“”
    public static final String LOGIN_USER_HEAD = SHAREDPREFERENCES
            + "user_head";// 用户头像key 默认值“”

    public static final String LOGIN_USER_LEVEL = SHAREDPREFERENCES
            + "user_level";// 用户级别

    public static final String LOGIN_USER_PRICE = SHAREDPREFERENCES
            + "user_price";// 用户账户余额

    public static final String LOGIN_USER_SCORES = SHAREDPREFERENCES
            + "user_scores";// 用户积分
    public static final String LOGIN_USER_PAYCOUNT = SHAREDPREFERENCES
            + "paycount";// 用户待付款
    public static final String LOGIN_USER_NORECEIPT = SHAREDPREFERENCES
            + "noReceipt";// 用户待收货
    public static final String LOGIN_USER_NODELIVERY = SHAREDPREFERENCES
            + "noDelivery";// 用户待发货
    public static final String LOGIN_USER_EAID = SHAREDPREFERENCES + "eaid";// 用户待评价
    public static final String LOGIN_USER_CARTCOUNT = SHAREDPREFERENCES
            + "cartCount";// 购物车数量
    public static final String LOGIN_USER_MSG_COUNT = SHAREDPREFERENCES
            + "msgCount";// 未读消息数量

    public static final String LOGIN_USER_LOGIN = SHAREDPREFERENCES
            + "login_flg";// 是否登录成功key 默认值false
    public static final String IS_FIRST = SHAREDPREFERENCES + "is_first_3_0";// 是否第一次登录
    public static final String IS_POINT = SHAREDPREFERENCES + "is_point";// 是否第一次打开指示
    public static final String IS_GOODS_POINT = SHAREDPREFERENCES
            + "is_goods_point";// 是否第一次打开商品详情指示
    public static final String IS_SHOP_POINT = SHAREDPREFERENCES
            + "is_shop_point";// 是否第一次打开购物车页面
    public static final String IMG_QUALITY = SHAREDPREFERENCES + "img_quality";// 图片质量1=高清2=标清3=不显示
    public static final String SCREEN_WIDTH = SHAREDPREFERENCES
            + "screen_width";// 屏幕的宽
    public static final String SCREEN_HEIGHT = SHAREDPREFERENCES
            + "screen_height";// 屏幕的高
    public static final String IS_REMEMBER_PW = SHAREDPREFERENCES
            + "is_remember_pw";// 是否保存密码
    public static final String SEARCH_HISTORY = SHAREDPREFERENCES
            + "search_history";// 搜索历史记录
    public static final String LOAD_DISTRICT = SHAREDPREFERENCES
            + "load_district";// 是否加载过省市区信息
    public static final String THEME = SHAREDPREFERENCES
            + "theme";// 当前样式标志位
    public static final String SHOWSTYLE_1 = SHAREDPREFERENCES
            + "showstyle_goodslist";// 商品列表   退出时的展示方式（列表 表格）   false：表格  true:列表
    public static final String SHOWSTYLE_2 = SHAREDPREFERENCES
            + "showstyle_boutiquesales";//  精品特卖  退出时的展示方式（列表 表格）   false：表格  true:列表
    public static final String SHOWSTYLE = SHAREDPREFERENCES
            + "showstyle_baoshui";//保税区  退出时的展示方式（列表 表格）   false：表格  true:列表
    public static final String SHOWSTYLE_3 = SHAREDPREFERENCES
            + "showstyle_bonded";//全部 退出时的展示方式（列表 表格）   false：表格  true:列表
    public static final String SHOWSTYLE_4 = SHAREDPREFERENCES
            + "showstyle_sea";//全部 退出时的展示方式（列表 表格）   false：表格  true:列表

    public static final String IS_OPEN_SENT = SHAREDPREFERENCES
            + "is_open_sent";//是否开启推送
    public static final String PUSH_START = SHAREDPREFERENCES
            + "pushtime_start";//推送安静时段 开始时间
    public static final String PUSH_LAST = SHAREDPREFERENCES
            + "pushtime_last";//推送安静时段 持续时间
    public static final String AGGREMENT_SHOWAGAIN_BAO = SHAREDPREFERENCES
            + "showagein_baoshuiqu";//保税区 购买流程及须知 不再提醒
    public static final String AGGREMENT_SHOWAGAIN_SEA = SHAREDPREFERENCES
            + "showagein_sea";//海外直邮 购买流程及须知 不再提醒
    public static final String IS_BIG_TXT_GOODSDETAIL = SHAREDPREFERENCES
            + "is_big_txt_goodsdetail";//是否 使用大字体
    public static final String IS_NIGHT = SHAREDPREFERENCES
            + "is_night";//是否开启NIGHT夜间模式
    public static final String SHOWSTYLE_BOUDED = SHAREDPREFERENCES + "showstyle_bouded";

    public static final String DATA_SYNCHRONOUS_TIME = SHAREDPREFERENCES
            + "data_synchronous_time";// 基础数据同步时间

    public static final String RECOMMEND_POSITION = SHAREDPREFERENCES + "recommend_position";


    public static final String TOPIC_SUBSCRIBE = SHAREDPREFERENCES + "topic_subscribe";


    /************************************************************************/
    /* others */
    /**
     * ********************************************************************
     */

    private static SharedPreferences sharedPreferences = null;
    private static SharedPreferencesUtils mSharedPreferencesUtils = null;
    private static Editor mEditor = null;

    /**
     * ********************************************************************
     */
    /* Singleton method */
    private SharedPreferencesUtils() {
        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.getInstance()
                    .getSharedPreferences(SHAREDPREFERENCES,
                            Context.MODE_PRIVATE);
            mEditor = sharedPreferences.edit();
        }
    }

    /**
     * ********************************************************************
     */
    public synchronized static SharedPreferencesUtils getInstance() {
        if (mSharedPreferencesUtils == null) {
            mSharedPreferencesUtils = new SharedPreferencesUtils();
        }
        return mSharedPreferencesUtils;
    }

    /************************************************************************/
    /* others method */
    /************************************************************************/

    /**
     * 保存用户信息
     *
     * @return
     */
    public boolean editUserMessage(UserBean uBean) {
        boolean editflg = false;
        mEditor.putString(LOGIN_USER_NAME, uBean.getSName());
        mEditor.putString(LOGIN_USER_ID, uBean.getMemberId());
        if (!StringUtils.isBlank(uBean.getMemberPwd())) {
            mEditor.putString(LOGIN_USER_PWD, uBean.getMemberPwd());
        }
      /*  mEditor.putString(LOGIN_USER_LEVEL, uBean.getLevel());
        mEditor.putString(LOGIN_USER_PRICE, uBean.getPrice());
        mEditor.putString(LOGIN_USER_SCORES, uBean.getScores());
        mEditor.putString(LOGIN_USER_HEAD, uBean.getImageUrl());
        mEditor.putString(LOGIN_USER_PAYCOUNT, uBean.getPaycount());
        mEditor.putString(LOGIN_USER_NORECEIPT, uBean.getNoReceipt());
        mEditor.putString(LOGIN_USER_NODELIVERY, uBean.getNoDelivery());
        mEditor.putString(LOGIN_USER_MSG_COUNT, uBean.getIssee());
        mEditor.putString(LOGIN_USER_EAID, uBean.getEaid());
        mEditor.putString(LOGIN_USER_CARTCOUNT, uBean.getCartCount());*/
        // 是否已经登陆
        mEditor.putBoolean(LOGIN_USER_LOGIN, true);
        editflg = mEditor.commit();
        return editflg;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserBean getUserMessage() {
        UserBean uBean = new UserBean();
        uBean.setSName(sharedPreferences.getString(LOGIN_USER_NAME, ""));
        uBean.setMemberId(sharedPreferences.getString(LOGIN_USER_ID, ""));
        uBean.setMemberPwd(sharedPreferences.getString(LOGIN_USER_PWD, ""));
      /*  uBean.setPassWord(sharedPreferences.getString(LOGIN_USER_PWD, ""));
        uBean.setLevel(sharedPreferences.getString(LOGIN_USER_LEVEL, ""));
        uBean.setPrice(sharedPreferences.getString(LOGIN_USER_PRICE, ""));
        uBean.setScores(sharedPreferences.getString(LOGIN_USER_SCORES, ""));
        uBean.setImageUrl(sharedPreferences.getString(LOGIN_USER_HEAD, ""));
        uBean.setPaycount(sharedPreferences.getString(LOGIN_USER_PAYCOUNT, ""));
        uBean.setCartCount(sharedPreferences
                .getString(LOGIN_USER_CARTCOUNT, ""));
        uBean.setIssee(sharedPreferences.getString(LOGIN_USER_MSG_COUNT, "0"));
        uBean.setNoReceipt(sharedPreferences
                .getString(LOGIN_USER_NORECEIPT, ""));
        uBean.setNoDelivery(sharedPreferences.getString(LOGIN_USER_NODELIVERY,
                ""));
        uBean.setEaid(sharedPreferences.getString(LOGIN_USER_EAID, ""));*/
        return uBean;
    }

    /**
     * 清除用户信息
     *
     * @return
     */
    public boolean cleanUserMessage() {
        boolean editflg = false;

        if (!getIsSavePw()) {
            mEditor.putString(LOGIN_USER_ID, "");
            mEditor.putString(LOGIN_USER_PWD, "");
        }
        mEditor.putString(LOGIN_USER_LEVEL, "");
        mEditor.putString(LOGIN_USER_PRICE, "");
        mEditor.putString(LOGIN_USER_SCORES, "");
        mEditor.putString(LOGIN_USER_HEAD, "");
        mEditor.putString(LOGIN_USER_PAYCOUNT, "");
        mEditor.putString(LOGIN_USER_NORECEIPT, "");
        mEditor.putString(LOGIN_USER_NODELIVERY, "");
        mEditor.putString(LOGIN_USER_EAID, "");
        mEditor.putString(LOGIN_USER_CARTCOUNT, "");
        // 是否已经登陆
        mEditor.putBoolean(LOGIN_USER_LOGIN, false);
        editflg = mEditor.commit();
        return editflg;
    }

    /**
     * 是否已经登陆
     *
     * @return 登陆true
     */
    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN_USER_LOGIN, false);
    }

    /**
     * 是否第一次登陆应用
     *
     * @return
     */
    public boolean isFirstLogin() {
        return sharedPreferences.getBoolean(IS_FIRST, true);
    }

    /**
     * 修改第一次登录标志位为false
     *
     * @return
     */
    public boolean editIsFirstLogin() {
        mEditor.putBoolean(IS_FIRST, false);
        return mEditor.commit();
    }

    public Set<String> getTopics() {
        Set<String> defStringsault = new TreeSet<>();
        defStringsault.add("Fangchao");
        return sharedPreferences.getStringSet(TOPIC_SUBSCRIBE, defStringsault);
    }

    public boolean putTopics(Set<String> topiclist) {
        mEditor.putStringSet(TOPIC_SUBSCRIBE, topiclist);
        return mEditor.commit();
    }

    /**
     * @param b
     * @param flag 1.商品列表 //2.精品特卖   //3.保税区
     * @return
     */
    public boolean editShowStyle(Boolean b, int flag) {
        switch (flag) {
            case 1:
                mEditor.putBoolean(SHOWSTYLE_1, b);

                break;
            case 2:
                mEditor.putBoolean(SHOWSTYLE_2, b);
                break;
            case 3:
                mEditor.putBoolean(SHOWSTYLE_3, b);
                break;
            case 4:
                mEditor.putBoolean(SHOWSTYLE_4, b);
                break;
            default:
                mEditor.putBoolean(SHOWSTYLE, b);
                break;
        }
        return mEditor.commit();

    }

    /**
     * 得到 展示方式
     *
     * @param flag 1.商品列表 //2.精品特卖   //3.保税区
     * @return
     */
    public boolean getShowStyle(int flag) {
        boolean b = false;
        switch (flag) {
            case 1:
                b = sharedPreferences.getBoolean(SHOWSTYLE_1, true);
                break;
            case 2:
                b = sharedPreferences.getBoolean(SHOWSTYLE_2, true);
                break;
            case 3:
                b = sharedPreferences.getBoolean(SHOWSTYLE_3, false);
                break;
            case 4:
                b = sharedPreferences.getBoolean(SHOWSTYLE_4, false);
                break;
            default:
                b = sharedPreferences.getBoolean(SHOWSTYLE, false);
                break;
        }
        return b;
    }

    /**
     * 保税区 购买须知 是否不再显示
     *
     * @param b false 下次还显示   true：不再显示
     * @return
     */
    public boolean editShowAgain_Bao(boolean b) {
        mEditor.putBoolean(AGGREMENT_SHOWAGAIN_BAO, b);
        return mEditor.commit();
    }

    /**
     * 保税区购买须知 是否不再显示
     *
     * @return
     */
    public boolean getShowAgain_Bao() {
        return sharedPreferences.getBoolean(AGGREMENT_SHOWAGAIN_BAO, false);
    }

    /**
     * 海外直邮 购买须知 是否不再显示
     *
     * @param b false：下次还显示   true：不再显示
     * @return
     */
    public boolean editShowAgain_Sea(boolean b) {
        mEditor.putBoolean(AGGREMENT_SHOWAGAIN_SEA, b);
        return mEditor.commit();
    }

    /**
     * 海外直邮 购买须知 是否不再显示
     *
     * @return
     */
    public boolean getShowAgain_Sea() {
        return sharedPreferences.getBoolean(AGGREMENT_SHOWAGAIN_SEA, false);
    }

    /**
     * 修改开启推送 标记位
     *
     * @param b
     * @return
     */
    public boolean editIsopensent(Boolean b) {
        mEditor.putBoolean(IS_OPEN_SENT, b);
        return mEditor.commit();
    }

    /**
     * 是否开启推送
     *
     * @return
     */
    public boolean getIsopensent() {
        return sharedPreferences.getBoolean(IS_OPEN_SENT, true);
    }

    /**
     * 修改开启夜间模式 标记位
     *
     * @param b
     * @return
     */
    public boolean editIsnight(Boolean b) {
        mEditor.putBoolean(IS_NIGHT, b);
        return mEditor.commit();
    }

    /**
     * 是否开启夜间模式
     *
     * @return
     */
    public boolean getIsnight() {
        return sharedPreferences.getBoolean(IS_NIGHT, true);
    }

    /**
     * 修改 是否使用大字号 标志位
     *
     * @param b
     * @return
     */
    public boolean editIsBigTxt(Boolean b) {
        mEditor.putBoolean(IS_BIG_TXT_GOODSDETAIL, b);
        return mEditor.commit();
    }

    /**
     * 是否 大字号
     *
     * @return
     */
    public boolean getIsBigTxt() {
        return sharedPreferences.getBoolean(IS_BIG_TXT_GOODSDETAIL, false);
    }


    /**
     * 是否第一次打开指示图片
     *
     * @return
     */
    public boolean isPoint() {
        return sharedPreferences.getBoolean(IS_POINT, true);
    }

    /**
     * 修改是否第一次打开指示图片为false
     *
     * @return
     */
    public boolean editisPoint() {
        mEditor.putBoolean(IS_POINT, false);
        return mEditor.commit();
    }

    /**
     * 是否第一次打开购物车指示图片
     *
     * @return
     */
    public boolean isShopPoint() {
        return sharedPreferences.getBoolean(IS_SHOP_POINT, true);
    }

    /**
     * 修改是否第一次打开购物车指示图片为false
     *
     * @return
     */
    public boolean editisShopPoint() {
        mEditor.putBoolean(IS_SHOP_POINT, false);
        return mEditor.commit();
    }

    /**
     * 是否第一次打开商品详情指示图片
     *
     * @return
     */
    public boolean isGoodsPoint() {
        return sharedPreferences.getBoolean(IS_GOODS_POINT, true);
    }

    /**
     * 修改是否第一次打开商品详情指示图片为false
     *
     * @return
     */
    public boolean editisGoodsPoint() {
        mEditor.putBoolean(IS_GOODS_POINT, false);
        return mEditor.commit();
    }

    /**
     * 是否加载过省市区信息
     *
     * @return
     */
    public boolean isLoadDistrict() {
        return sharedPreferences.getBoolean(LOAD_DISTRICT, false);
    }

    /**
     * 修改是否加载过省市区标志位true
     *
     * @return
     */
    public boolean editIsLoadDistrict() {
        mEditor.putBoolean(LOAD_DISTRICT, true);
        return mEditor.commit();
    }

    /**
     * 获取用户id
     *
     * @return
     */
    public String getUserId() {
        return sharedPreferences.getString(LOGIN_USER_ID, "");
    }

    /**
     * 获取是否保存密码标志位
     *
     * @return
     */
    public boolean getIsSavePw() {
        return sharedPreferences.getBoolean(ISSAVE_PW, true);
    }

    /**
     * 修改是否保存密码标志位
     *
     * @return
     */
    public boolean editIsSavePw(boolean flg) {
        mEditor.putBoolean(ISSAVE_PW, flg);
        return mEditor.commit();
    }

    /**
     * 获取屏幕分辨率
     *
     * @return 默认480x800
     */
    public String getScreenSize() {
        return sharedPreferences.getString(SCREEN_WIDTH, "480") + "x"
                + sharedPreferences.getString(SCREEN_HEIGHT, "800");
    }

    /**
     * 保存屏幕的宽度px
     *
     * @param width
     * @return
     */
    public boolean editScreenWidth(int width) {
        mEditor.putString(SCREEN_WIDTH, String.valueOf(width));
        return mEditor.commit();
    }

    /**
     * 保存屏幕的高度px
     *
     * @param height
     * @return
     */
    public boolean editScreenHeight(int height) {
        mEditor.putString(SCREEN_HEIGHT, String.valueOf(height));
        return mEditor.commit();
    }

    /**
     * 安静时段开始时间
     *
     * @param starttime
     * @return
     */
    public boolean putPushStarttime(int starttime) {
        mEditor.putInt(PUSH_START, starttime);
        return mEditor.commit();
    }

    /**
     * 获取开始时间
     *
     * @return
     */
    public int getPushStarttime() {
        return sharedPreferences.getInt(PUSH_START, 23);
    }

    /**
     * 设置 安静时段持续时间
     *
     * @param lastTime
     * @return
     */
    public boolean putLastTime(int lastTime) {
        mEditor.putInt(PUSH_LAST, lastTime);
        return mEditor.commit();
    }

    /**
     * 获取持续时间
     *
     * @return
     */
    public int getLastTime() {
        return sharedPreferences.getInt(PUSH_LAST, 7);
    }

    /**
     * 设置图片质量
     *
     * @param quality 图片质量1=高清2=标清3=不显示
     * @return
     */
    public boolean putImgQuerity(int quality) {
        mEditor.putInt(IMG_QUALITY, quality);
        return mEditor.commit();

    }

    /**
     * 获取图片质量
     *
     * @return 图片质量1=高清2=标清3=不显示
     */
    public int getImgQuerity() {
        return sharedPreferences.getInt(IMG_QUALITY, 2);
    }


    public boolean editisNightTheme(boolean theme) {
        mEditor.putBoolean(THEME, theme);
        return mEditor.commit();
    }

    public boolean isNightTheme() {
        return sharedPreferences.getBoolean(THEME, false);
    }

    /**
     * 获取应用上次同步基础数据的时间
     *
     * @return 时间戳
     */
    public long getDataSynchronousTime() {
        return sharedPreferences.getLong(DATA_SYNCHRONOUS_TIME, TimeUtils.getCurrentTimeInLong());
    }

    /**
     * 修改应用同步基础数据的时间
     *
     * @param DataSynchronousTime 时间戳
     * @return
     */
    public boolean editDataSynchronousTime(long DataSynchronousTime) {
        mEditor.putLong(DATA_SYNCHRONOUS_TIME, DataSynchronousTime);
        return mEditor.commit();
    }

    /**
     * 获取首页分类位置
     *
     * @return
     */
    public int getRecommendPosition() {
        return sharedPreferences.getInt(RECOMMEND_POSITION, 1);
    }

    /**
     * 修改首页分类位置
     *
     * @param position
     * @return
     */
    public boolean editRecommendPosition(int position) {
        mEditor.putInt(RECOMMEND_POSITION, position);
        return mEditor.commit();
    }

}
