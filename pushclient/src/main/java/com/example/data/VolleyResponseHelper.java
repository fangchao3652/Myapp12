package com.example.data;

import com.example.Bean.Author;
import com.example.Bean.BannerListBean;
import com.example.Bean.CourseListBean;
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
     * @param type 1基本解析 3焦点图（首页banner数据）
     *             4 登录  29：注册  6获取news列表 7获取news详情 8新鲜事 9获取新鲜事详情   31课程表
     *
     *
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
            case 3:
                rBean = getBannerList(json);
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
            case 31://课程表
                rBean = getCourses(json);
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




    private static ResultSingleBean getCourses(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                CourseListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            CourseListBean.class);
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




private static ResultSingleBean getBannerList(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                BannerListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            BannerListBean.class);
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
