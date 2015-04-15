package com.example.data;

import com.example.Bean.BaseBean;
import com.example.Bean.ResultBean;
import com.example.Bean.ResultSingleBean;
import com.example.Bean.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


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
    public static final String JSON_TOPGOODSLIST = "TopGoodsList";
    public static final String JSON_PICLIST = "PicList";
    public static final String JSON_MEMBER = "Member";
    public static final String JSON_CATEGORYLIST = "CategoryList";
    public static final String JSON_BRANDLIST = "BrandList";
    public static final String JSON_GOODS = "Goods";
    public static final String JSON_ORDER = "Order";
    public static final String JSON_CONSIGNEELIST = "Consigneelist";
    public static final String JSON_DISTRICTLIST = "DistrictList";
    public static final String JSON_CONSIGNEE = "Consignee";
    public static final String JSON_INVOICELIST = "InvoiceList";
    public static final String JSON_SHIPPINGFEE = "ShippingFee";
    public static final String JSON_ALIPAYDATA = "AliPayData";
    public static final String JSON_TICKET = "Ticket";
    /**
     * 网络请求超时时间
     */
    public static final int INITIALTIMEOUTMS = 10 * 1000;

    /**
     * 将json转化成ResultBean
     *
     * @param json 待解析的josn串
     * @param type 1基本解析2促销商品3焦点图（首页banner数据）4
     *             登陆5所有分类6获取商品列表7获取所有品牌8获取商品详情9获取商品评论列表10获取购物车11获得订单列表12获取订单详情13获取优惠券列表
     *             14获取收货地址列表15获取全部省市区16获取默认收货地址(包含相关的支付方式、配送方式)17
     *             根据地区和支付方式获得支付方式及送货方式18获得所有开票信息19获取运费20站内消息21我的收藏22支付宝数据23待评价24获取银联支付数据25优惠券详情
     *             26获取订单物流信息27获取搜索热词28检查更新29注册30获取优惠券是否可用信息31获取活动详细信息32今日特价列表信息33精品特卖商品列表34精品特卖列表
     *             35首页商品分组基础数据36海外直邮国家基础数据37保税区仓库基础数据38首页推荐39购物车列表40提交订单41修改站内消息为已读42所有仓库的支付方式
     *             43获取支付相关数据
     *
     *             4 登录  29：注册
     * @return
     */
    public static ResultBean jsonToBean(JSONObject json, int type) {
        ResultBean rBean = null;
        switch (type) {
            case 1:
                rBean = base(json);
                break;
           /* case 2:
                rBean = getIndexTopGoods(json);
                break;
            case 3:
                rBean = getPicList(json);
                break;*/
           /* case 4:
                rBean = login(json);
                break;
            case 5:
                rBean = getAllCategory(json);
                break;
            case 6:
                rBean = getGoodsList(json);
                break;
            case 7:
                rBean = getAllBrand(json);
                break;
            case 8:
                rBean = getGoodsDetails(json);
                break;
            case 9:
                rBean = getGoodsComment(json);
                break;
            case 10:
                rBean = getShoppingCart(json);
                break;
            case 11:
                rBean = getOrderList(json);
                break;
            case 12:
                rBean = getOrder(json);
                break;
            case 13:
                rBean = getTicketList(json);
                break;
            case 14:
                rBean = getConsigneeList(json);
                break;
            case 15:
                rBean = getAllDistrict(json);
                break;
            case 16:
                rBean = getDefaultConsignee(json);
                break;
            case 17:
                rBean = getPaymentShipping(json);
                break;
            case 18:
                rBean = getInvoiceInfos(json);
                break;
            case 19:
                rBean = getShippingFreight(json);
                break;
            case 20:
                rBean = getUserMessage(json);
                break;
            case 21:
                rBean = getMyCollectiontList(json);
                break;
            case 22:
                rBean = getAlipaydata(json);
                break;
            case 23:
                rBean = getToEaidList(json);
                break;
            case 24:
                rBean = getUPPayData(json);
                break;
            case 25:
                rBean = getTicket(json);
                break;
            case 26:
                rBean = getLogisticOfOrder(json);
                break;
            case 27:
                rBean = getHotWord(json);
                break;
            case 28:
                rBean = getUpdate(json);
                break;*/

            case 29:
                rBean = register(json);
                break;
          /*  case 30:
                rBean = getTicketInfo(json);
                break;
            case 31:
                rBean = getActivity(json);
                break;
            case 32:
                rBean = getPromotion(json);
                break;
            case 33:
                rBean = getBoutiqueBean(json);
                break;
            case 34:
                rBean = getBoutiqueSaleBean(json);
                break;
            case 35:
                rBean = getStrategyGroup(json);
                break;
            case 36:
                rBean = getCountry(json);
                break;
            case 37:
                rBean = getBondedArea(json);
                break;
            case 38:
                rBean = getStrategyBaseBean(json);
                break;
            case 39:
                rBean = getCartListBean(json);
                break;
            case 40:
                rBean = getSubmitOrderList(json);
                break;
            case 41:
                rBean = getPostUserMessageToRead(json);
                break;
            case 42:
                rBean = getPayment(json);
                break;
            case 43:
                rBean = getPaymentData(json);
                break;*/
            default:
                rBean = null;
                break;
        }
        if (rBean != null && rBean.getRetCode() == 10001) {
            rBean.setRetMessage("系统异常,请稍后重试");
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
                Object o = message.opt(JSON_MEMBER);
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
    /**
     * 促销商品
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getIndexTopGoods(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_TOPGOODSLIST);
                Type type = new TypeToken<ArrayList<TopGoodsBean>>() {
                }.getType();
                if (o != null) {
                    list = gson.fromJson(o.toString(), type);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 焦点图（banner）
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getPicList(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_PICLIST);
                Type type = new TypeToken<ArrayList<BannerBean>>() {
                }.getType();
                if (o != null) {
                    list = gson.fromJson(o.toString(), type);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 登陆
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean login(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_MEMBER);
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

    *//**
     * 注册 29
     *
     * @param json
     * @return
     *//*
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

    *//**
     * 获取优惠券详细信息
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getTicket(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_TICKET);
                TicketDetailBean bean = null;
                if (o != null) {
                    bean = gson.fromJson(o.toString(), TicketDetailBean.class);
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

    *//**
     * 获取订单物流信息
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getLogisticOfOrder(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                LogisticBean bean = null;
                if (message != null) {
                    bean = gson
                            .fromJson(message.toString(), LogisticBean.class);
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

    *//**
     * 获取优惠券是否可用
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getTicketInfo(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                TicketInfoBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            TicketInfoBean.class);
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

    *//**
     * 所有分类
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getAllCategory(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_CATEGORYLIST);
                Type type = new TypeToken<ArrayList<CategoryBean>>() {
                }.getType();
                if (o != null) {
                    list = gson.fromJson(o.toString(), type);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 获取搜索热词
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getHotWord(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                Gson gson = new Gson();
                Object message = json.opt(JSON_DATA);
                Type type = new TypeToken<ArrayList<HotWordBean>>() {
                }.getType();
                if (message != null) {
                    list = gson.fromJson(message.toString(), type);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 获取商品列表
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getGoodsList(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                GoodsListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            GoodsListBean.class);
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

    *//**
     * 获取活动详细信息
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getActivity(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                ActivityListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            ActivityListBean.class);
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

    *//**
     * 所有品牌
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getAllBrand(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_BRANDLIST);
                Type type = new TypeToken<ArrayList<BrandBean>>() {
                }.getType();
                if (o != null) {
                    list = gson.fromJson(o.toString(), type);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 首页商品分组基础数据
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getStrategyGroup(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                Gson gson = new Gson();
                Object message = json.opt(JSON_DATA);
                Type type = new TypeToken<ArrayList<StrategyGroupBean>>() {
                }.getType();
                if (message != null) {
                    list = gson.fromJson(message.toString(), type);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 海外直邮国家基础数据
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getCountry(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                List<String> listStr = new ArrayList();
                Gson gson = new Gson();
                Object message = json.opt(JSON_DATA);
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                if (message != null) {
                    listStr = gson.fromJson(message.toString(), type);
                }
                CountryBean cb;
                for (int i = 0; i < listStr.size(); i++) {
                    cb = new CountryBean(listStr.get(i));
                    list.add(cb);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 保税区仓库基础数据
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getBondedArea(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                List<String> listStr = new ArrayList();
                Gson gson = new Gson();
                Object message = json.opt(JSON_DATA);
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                if (message != null) {
                    listStr = gson.fromJson(message.toString(), type);
                }
                BondedAreaBean cb;
                for (int i = 0; i < listStr.size(); i++) {
                    cb = new BondedAreaBean(listStr.get(i));
                    list.add(cb);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 获取商品详情
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getGoodsDetails(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_GOODS);
                GoodsDetailBean bean = null;
                if (o != null) {
                    bean = gson.fromJson(o.toString(), GoodsDetailBean.class);
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

    *//**
     * 获取商品评论列表
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getGoodsComment(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                CommentListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            CommentListBean.class);
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

    *//**
     * 获取购物车
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getShoppingCart(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                CartListBean bean = null;
                if (message != null) {
                    bean = gson
                            .fromJson(message.toString(), CartListBean.class);
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

    *//**
     * 获取订单列表
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getOrderList(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                OrderListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            OrderListBean.class);
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

    *//**
     * 获取消息列表 20
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getUserMessage(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                MessageListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            MessageListBean.class);
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

    *//**
     * 获取订单详情
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getOrder(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_ORDER);
                OrderDetailBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(o.toString(), OrderDetailBean.class);
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

    *//**
     * 提交订单
     *
     * @param json
     * @return
     *//*
    public static ResultSingleBean getSubmitOrderList(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                OrderListBaseBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(), OrderListBaseBean.class);
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

    *//**
     * 修改站内消息为已读
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getPostUserMessageToRead(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                rBean.setRetObj(null);
            }
        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 获取优惠券列表
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getTicketList(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                TicketListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            TicketListBean.class);
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

    *//**
     * 待评价
     *
     * @param json 23
     * @return
     *//*
    private static ResultSingleBean getToEaidList(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                ToEaidListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            ToEaidListBean.class);
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

    *//**
     * 获取银联支付数据
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getUPPayData(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                UPPayBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(), UPPayBean.class);
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

    *//**
     * 获取收藏列表
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getMyCollectiontList(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                MyCollectionListBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            MyCollectionListBean.class);
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

    *//**
     * 获取收货地址
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getConsigneeList(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_CONSIGNEELIST);
                Type type = new TypeToken<ArrayList<ConsigneeBean>>() {
                }.getType();
                if (o != null) {
                    list = gson.fromJson(o.toString(), type);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 获取全部省市区
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getAllDistrict(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_DISTRICTLIST);
                Type type = new TypeToken<ArrayList<DistrictBean>>() {
                }.getType();
                if (o != null) {
                    list = gson.fromJson(o.toString(), type);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 获取默认收货地址
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getDefaultConsignee(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                ComputeBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(), ComputeBean.class);
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

    *//**
     * 根据地区和支付方式获得支付方式及送货方式
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getPaymentShipping(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                PaymentShipBaseBean bean = null;
                if (message != null) {
                    bean = gson.fromJson(message.toString(),
                            PaymentShipBaseBean.class);
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

    *//**
     * 获取全部开票信息
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getInvoiceInfos(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                List<BaseBean> list = new ArrayList();
                Gson gson = new Gson();
                JSONObject message = json.optJSONObject(JSON_DATA);
                Object o = message.opt(JSON_INVOICELIST);
                Type type = new TypeToken<ArrayList<InvoiceBean>>() {
                }.getType();
                if (o != null) {
                    list = gson.fromJson(o.toString(), type);
                }
                rBean.setListBean(list);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 获得运费
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getShippingFreight(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                JSONObject message = json.optJSONObject(JSON_DATA);
                String shippingfee = message.optString(JSON_SHIPPINGFEE);
                rBean.setRetObj(shippingfee);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 获取支付宝支付数据
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getAlipaydata(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));

            if (rBean.getRetCode() == 0) {
                JSONObject message = json.optJSONObject(JSON_DATA);
                String aliPayData = message.optString(JSON_ALIPAYDATA);
                rBean.setRetObj(aliPayData);
            }
        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 检查更新
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getUpdate(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                Object o = json.opt(JSON_DATA);
                AppVersionBaseBean bean = null;
                if (o != null) {
                    bean = gson
                            .fromJson(o.toString(), AppVersionBaseBean.class);
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

    *//**
     * 今日特价
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getPromotion(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                Object o = json.opt(JSON_DATA);
                List<BaseBean> bean = null;
                Type type = new TypeToken<ArrayList<PromotionBaseBean>>() {
                }.getType();
                if (o != null) {
                    bean = gson
                            .fromJson(o.toString(), type);
                }
                rBean.setListBean(bean);
            }
        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 精品特卖商品列表
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getBoutiqueBean(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                Object o = json.opt(JSON_DATA);
                BoutiqueListBean bean = null;
                if (o != null) {
                    bean = gson
                            .fromJson(o.toString(), BoutiqueListBean.class);
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

    *//**
     * 精品特卖列表
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getBoutiqueSaleBean(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                Object o = json.opt(JSON_DATA);
                BoutiqueSaleListBean bean = null;
                if (o != null) {
                    bean = gson
                            .fromJson(o.toString(), BoutiqueSaleListBean.class);
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

    *//**
     * 首页推荐
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getStrategyBaseBean(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                Object o = json.opt(JSON_DATA);
                StrategyBean bean = null;
                if (o != null) {
                    bean = gson
                            .fromJson(o.toString(), StrategyBean.class);
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

    *//**
     * 购物车列表
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getCartListBean(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                Object o = json.opt(JSON_DATA);
                List<BaseBean> bean = null;
                Type type = new TypeToken<ArrayList<CartListBean>>() {
                }.getType();
                if (o != null) {
                    bean = gson
                            .fromJson(o.toString(), type);
                }
                rBean.setListBean(bean);
            }
        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 支付方式
     *
     * @param json
     * @return
     *//*
    private static ResultListBean getPayment(JSONObject json) {
        ResultListBean rBean = new ResultListBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                Object o = json.opt(JSON_DATA);
                List<BaseBean> bean = null;
                Type type = new TypeToken<ArrayList<PayBean>>() {
                }.getType();
                if (o != null) {
                    bean = gson
                            .fromJson(o.toString(), type);
                }
                rBean.setListBean(bean);
            }
        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }

    *//**
     * 获取支付相关数据
     *
     * @param json
     * @return
     *//*
    private static ResultSingleBean getPaymentData(JSONObject json) {
        ResultSingleBean rBean = new ResultSingleBean();
        try {
            rBean.setRetCode(json.optInt(JSON_ERRORCODE));
            rBean.setRetMessage(json.optString(JSON_MESSAGE));
            if (rBean.getRetCode() == 0) {
                Gson gson = new Gson();
                Object o = json.opt(JSON_DATA);
                PayDataBean bean = null;
                if (o != null) {
                    bean = gson
                            .fromJson(o.toString(), PayDataBean.class);
                }
                rBean.setRetObj(bean);
            }

        } catch (Exception e) {
            rBean.setRetCode(1);
            rBean.setRetMessage("数据异常");
            return rBean;
        }
        return rBean;
    }*/
}
