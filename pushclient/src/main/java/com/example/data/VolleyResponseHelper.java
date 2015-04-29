package com.example.data;

import com.example.Bean.Author;
import com.example.Bean.CustomFields;
import com.example.Bean.FreshNews;
import com.example.Bean.FreshNewsDetailsBean;
import com.example.Bean.FreshNewsListBean;
import com.example.Bean.NewsDetailsBean;
import com.example.Bean.NewsListBean;
import com.example.Bean.ResultBean;
import com.example.Bean.ResultSingleBean;
import com.example.Bean.Tags;
import com.example.Bean.UserBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * json解析辅助类
 *
 * @author zxh
 */
public class VolleyResponseHelper {
    /************************************************************************/
    /* json Key */
    /**
     * ********************************************************************
     */
    public static final String JSON = "JSON";
    public static final String JSON_ERRORCODE = "ErrorCode";
    public static final String JSON_DATA = "Data";
    public static final String JSON_MESSAGE = "Message";

    /**
     * 网络请求超时时间
     */
    public static final int INITIALTIMEOUTMS = 10 * 1000;

    /**
     * 将json转化成ResultBean
     *
     * @param json 待解析的josn串
     * @param type 1基本解析2促销商品3焦点图（首页banner数据）4
     *             登陆5所有分类6获取news列表7获取news详情8新鲜事9获取新鲜事详情10获取购物车11获得订单列表12获取订单详情13获取优惠券列表
     *             14获取收货地址列表15获取全部省市区16获取默认收货地址(包含相关的支付方式、配送方式)17
     *             根据地区和支付方式获得支付方式及送货方式18获得所有开票信息19获取运费20站内消息21我的收藏22支付宝数据23待评价24获取银联支付数据25优惠券详情
     *             26获取订单物流信息27获取搜索热词28检查更新29注册30获取优惠券是否可用信息31获取活动详细信息32今日特价列表信息33精品特卖商品列表34精品特卖列表
     *             35首页商品分组基础数据36海外直邮国家基础数据37保税区仓库基础数据38首页推荐39购物车列表40提交订单41修改站内消息为已读42所有仓库的支付方式
     *             43获取支付相关数据
     *             <p/>
     *             4 登录  29：注册
     * @return
     */
    public static ResultBean jsonToBean(JSONObject json, int type) {
        ResultBean rBean = null;
        switch (type) {
            case 1:
                rBean = base(json);
                break;

            case 4:
                rBean = login(json);
                break;

            case 6:
                rBean = getNewsList(json);
                break;
            case 7://详情
                rBean = getNewsDetails(json);
                break;
            case 8:
                rBean = getFreshlist(json);
                break;
            case 9:
                rBean = getFreshDetail(json);
                break;
            case 29:
                rBean = register(json);
                break;

            default:
                rBean = null;
                break;
        }
        if (rBean != null && rBean.getRetCode() == 10001) {
            rBean.setRetMessage("系统异常,请稍后重试");
        }
        return rBean;
    }

    private static ResultBean getFreshDetail(JSONObject jsonObject) {

        ResultSingleBean rBean = new ResultSingleBean();

        if (jsonObject.opt("status").equals("ok")) {


            JSONObject contentObject = jsonObject.optJSONObject("post");
            String content = contentObject.optString("content");
            FreshNewsDetailsBean detailsBean = new FreshNewsDetailsBean();
            detailsBean.setContent(content);
            rBean.setRetCode(0);
            rBean.setRetObj(detailsBean);
        }

        return rBean;
    }

    private static ResultBean getFreshlist(JSONObject json) {
        // JSONArray postsArray = resultObj.optJSONArray("posts");
        ResultSingleBean rBean = new ResultSingleBean();
        try {

            JSONArray postsArray = json.optJSONArray("posts");

            ArrayList<FreshNews> freshNewses = new ArrayList<>();

            for (int i = 0; i < postsArray.length(); i++) {

                FreshNews freshNews = new FreshNews();
                JSONObject jsonObject = postsArray.getJSONObject(i);
                freshNews.setId(jsonObject.optString("id"));
                freshNews.setUrl(jsonObject.optString("url"));
                freshNews.setTitle(jsonObject.optString("title"));
                freshNews.setDate(jsonObject.optString("date"));
                freshNews.setComment_count(jsonObject.optString("comment_count"));
                freshNews.setAuthor(Author.parse(jsonObject.optJSONObject("author")));
                freshNews.setCustomFields(CustomFields.parse(jsonObject.optJSONObject("custom_fields")));
                freshNews.setTags(Tags.parse(jsonObject.optJSONArray("tags")));
                freshNewses.add(freshNews);

            }
            FreshNewsListBean bean = null;
            bean = new FreshNewsListBean();
            bean.setPosts(freshNewses);
            if (bean != null) {
                rBean.setRetObj(bean);
                rBean.setRetCode(0);
                rBean.setRetMessage("ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常...");
            return rBean;
        }
        return rBean;

    }

    private static ResultBean getNewsDetails(JSONObject json) {

        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                JSONObject message1 = message.optJSONObject("New");
                NewsDetailsBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message1.toString(),
                            NewsDetailsBean.class);
                }
                rBean.setRetObj(bean);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    /**
     * 基本解析
     *
     * @param json
     * @return
     */
    private static ResultBean base(JSONObject json) {
        ResultBean rBean = new ResultBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    /**
     * 登陆
     *
     * @param json
     * @return
     */
    private static ResultSingleBean login(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);

                UserBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(), UserBean.class);
                }
                rBean.setRetObj(bean);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    /**
     * 注册 29
     *
     * @param json
     * @return
     */
    private static ResultSingleBean register(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                Object o = json.opt(JSON_DATA);
                UserBean bean = null;
                if (o != null) {
                    bean = gson.fromJson(o.toString(), UserBean.class);
                }
                rBean.setRetObj(bean);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    private static ResultSingleBean getNewsList(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                NewsListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            NewsListBean.class);
                }
                rBean.setRetObj(bean);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }


}
