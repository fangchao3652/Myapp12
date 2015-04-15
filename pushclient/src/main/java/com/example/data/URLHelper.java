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
    /************************************************************************/
    /* URL */

    /************************************************************************/
    /**
     * 接口版本号
     */
    public static final String URLCODE = "2.0";
    /**
     * 线上数据url
     */
    public static final String ONLINEURL = "http://api.supuy.com/";
    /**
     * 测试数据url 飞麟机器-- http://192.168.0.95:43826/
     * 内网测试地址--http://192.168.1.11:8008/ 外网测试地址---http://222.173.115.218:8008/
     */
    public static final String TESTURL = "http://222.173.115.218:8008/";
    /**
     * 项目中使用的url
     */
    public static final String BALSEURL = Constants.ISDEBUG ? TESTURL
            : ONLINEURL;

    /**
     * 线上数据url
     */
    public static final String ONLINEURLIMAGE = "http://soft.fit-find.com/Images/";
    /**
     * 测试数据url
     */
    public static final String TESTURLIMAGE = "http://192.168.1.61:8082/Images/";

    /**
     * 默认的图片地址
     */
    public static final String IMGURL = Constants.ISDEBUG ? TESTURLIMAGE
            : ONLINEURLIMAGE;

    /**
     * 测试推送url
     */
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

    public static final String VERSION = "v1/";
    public static final String VERSION_3 = "v2/";
    /************************************************************************/
    /* 模块module定义 */
    /**
     * ********************************************************************
     */
    public static final String MODULE_COMMON = "common/";// 公共模块
    public static final String MODULE_ACCOUNT = "account/";//
    public static final String MODULE_PRODUCT = "product/";//
    public static final String MODULE_ORDER = "order/";//
    public static final String MODULE_MESSAGE = "user/";// 消息
    public static final String MODULE_TICKET = "ticket/";// 优惠券详情 使用优惠价
    public static final String MODULE_PAYMENT = "payment/";//
    public static final String MODULE_FEEDBACK = "feedback/";// 反馈意见
    public static final String MODULE_FAVORITE = "favorite/";// 我的收藏
    /************************************************************************/
    /**
     * *******************************************************************
     */
    //3.0.0新增接口
    public static final String MOUDLE_PROMOTION = "promotion/";//促销
    public static final String MOUDLE_TEMAI = "temai/";//精品特卖
    public static final String MOUDLE_STRATEGY = "strategy/";//首页推荐
    public static final String MOUDLE_PRODUCT = "product/";//海外直邮国家
    /***********************************************************************/


    /* method定义 */
    /**
     * ********************************************************************
     */
    public static final String M_GETINDEXTOPGOODS = "GetIndexTopGoods";// 获取促销商品
    public static final String M_GETBANNER = "GetBanner";// 获取引导页
    public static final String M_GETPICLIST = "GetPicList";// 获取焦点图
    public static final String M_LOGIN = "Login";// 登陆
    public static final String M_REGISTER = "Register";// 注册
    public static final String M_TICKET = "GetTicket";// 获取优惠券可用详情
    public static final String M_GETALLCATEGORY = "GetAllCategory";// 获得所有分类列表
    public static final String M_GETALLBRAND = "GetAllBrand";// 获得所有品牌列表
    public static final String M_GETGOODSLIST = "GetGoodsList";// 获得商品列表
    public static final String M_GETGOODLISTBYCOMMENTSTATE = "GetGoodListByCommentState";// 获得商品列表
    public static final String M_GETGOODSDETAILS = "GetGoodsDetails";// 获得商品详情根据商品编码
    public static final String M_GETGOODSDETAILSBYBARCODE = "GetGoodsDetailsByBarcode";// 获得商品详情根据商品条形码
    public static final String M_GETGOODSCOMMENT = "GetGoodsComment";// 获得商品评论
    public static final String M_GETSHOPPINGCART = "GetShoppingCart";// 获得购物车
    public static final String M_GETORDERLIST = "GetOrderList";// 获得订单列表
    public static final String M_GETUSERMESSAGE = "GetUserMessage";// 获得消息列表
    public static final String M_GETORDER = "GetOrder";// 获得订单详情
    public static final String M_GETTICKETLIST = "GetTicketList";// 获得优惠券列表
    public static final String M_POSTMODIFYSHOPPINGCART = "PostModifyShoppingCart";// 添加或修改购物车
    public static final String M_GETCONSIGNEELIST = "GetConsigneeList";// 获取收货地址列表
    public static final String M_GETALLDISTRICT = "GetAllDistrict";// 获取全部省市区
    public static final String M_GETDEFAULTCONSIGNEE = "GetDefaultConsignee";// 获取默认的收货地址
    public static final String M_GETPAYMENTSHIPPING = "GetPaymentShipping";// 根据地区和支付方式获得支付方式及送货方式
    public static final String M_GETINVOICEINFOS = "GetInvoiceInfos";// 获得所有开票信息
    public static final String M_GETSHIPPINGFREIGHT = "GetShippingFreight";// 获得运费
    public static final String M_POSTSUBMITORDER = "PostSubmitOrder";// 提交订单
    public static final String M_POSTFEEDBACK = "AddFeedBack";// 提交反馈意见
    public static final String M_POSTBINDTICKET = "PostBindTicket";// 提交反馈意见
    public static final String M_POSTADDORMOIDFYCONSIGNEE = "PostAddOrMoidfyConsignee";// 添加或修改收货地址
    public static final String M_POSTDELETECONSIGNEE = "PostDeleteConsignee";// 删除收货地址
    public static final String M_GETFAVORITES = "GetFavorites";// 获取收藏列表
    public static final String M_GETALIPAYDATA = "GetAlipaydata";// 获取支付宝支付数据
    public static final String M_ADDGOODSCOMMENT = "AddGoodsComment";// 添加商品评论
    public static final String M_GETUPPAYDATA = "GetUPPayData";// 获取银联支付数据
    public static final String M_GETLOGISTICOFORDER = "GetLogisticOfOrder";// 获取订单物流信息
    public static final String M_GETHOTWORD = "GetHotWord";// 获取订单物流信息
    public static final String M_GETUPDATE = "GetUpdate";// 检查更新
    public static final String M_ADDFAVORITES = "AddFavorites";// 添加收藏
    public static final String M_GETTICKETINFO = "GetTicketInfo";// 获取优惠券可用信息
    public static final String M_GetActivity = "GetActivity";// 获取活动详细信息
    public static final String M_GETMEMBERINFO = "GetMemberInfo";// 获取会员信息
    public static final String M_POSTUSERSMSGTOREAD = "PostUserMessageToRead";// 	更改消息状态为已读
    public static final String M_REMOVECOLLECTIONS = "RemoveFavorites";// 	删除收藏
    /************************************************************************/

    /**
     * 3.0.0新增接口
     */

    public static final String M_GETPROMOTIONLIST = "GetPromotionList";//今日特价
    public static final String M_GETTEMAITOPAGER = "GetTemaiToPager";//精品特卖列表
    public static final String M_GETTEMAIDETAILD = "GetTemaiDetaild";//精品特卖商品列表
    public static final String M_GETBONDEDAREA = "GetBondedArea";//获取支持的保税区仓库
    public static final String M_GETBONDEDAREALIST = "GetBondedAreaList";//保税区商品列表
    public static final String M_GETGLOBALPURCHASELIST = "GetGlobalPurchaseList";//海外直邮商品列表
    public static final String M_GETSTRATERYBYID = "GetStrategyById";//首页推荐商品列表
    public static final String M_GETSTRATEGYGROUP = "GetStrategyGroup";//获取首页推荐商品分组
    public static final String M_GETGLOBALPURCHASECOUNTRY = "GetGlobalPurchaseCountry";//获取海外直邮的国家
    public static final String M_CHANGEPRODUCTNUMINCART = "ChangeProductNumInCart";//变更购物车选中状态
    public static final String M_GETPAYMENT = "GetPayment";//获取支付相关选项
    public static final String M_GETPAYMENTDATA = "GetPaymentData";//获取支付加密数据
    /***********************************************************************/

    /* params Key */
    /**
     * ********************************************************************
     */
    public static final String P_SIGN = "sign";
    public static final String P_METHOD = "method";
    public static final String P_ACCOUNT = "Account";
    public static final String P_CONTENT = "Content";
    public static final String P_PASSWORD = "Password";
    public static final String P_PAGEINDEX = "PageIndex";
    public static final String P_PAGESIZE = "PageSize";
    public static final String P_BARCODE = "Barcode";
    public static final String P_SEARCHKEY = "SearchKey";
    public static final String P_CATEGORYID = "CategoryId";
    public static final String P_BRANDID = "BrandId";
    public static final String P_STARTPRICE = "StartPrice";
    public static final String P_ENDPRICE = "EndPrice";
    public static final String P_SORTFIELD = "SortField";
    public static final String P_GOODSSN = "GoodsSN";
    public static final String P_STATE = "State";
    public static final String P_IDS = "Ids";
    public static final String P_STATETYPES = "StateTypes";
    public static final String P_STARTTIME = "StartTime";
    public static final String P_ENDTIME = "EndTime";
    public static final String P_ORDERSN = "OrderSN";
    public static final String P_ISUSED = "IsUsed";
    public static final String P_ISDEFAULT = "IsDefault";
    public static final String P_GOODS = "goods";
    public static final String P_AREAID = "AreaId";
    public static final String P_PAYMENTID = "PaymentId";
    public static final String P_PAYMENTTYPES = "PaymentTypes";
    public static final String P_PROVID = "ProvinceId";
    public static final String P_SHIPPINGID = "ShippingId";
    public static final String P_ORDERINDEX = "OrderIndex";
    public static final String P_CARTITEMS = "CartItems";

    public static final String P_CONSIGNEE = "Consignee";
    public static final String P_PROVINCEID = "ProvinceId";
    public static final String P_CITYID = "CityId";
    public static final String P_ZIPCODE = "ZipCode";
    public static final String P_ADDRESS = "Address";
    public static final String P_MOBILE = "Mobile";
    public static final String P_TEL = "Tel";
    public static final String P_EMAIL = "Email";
    public static final String P_ALLCASH = "AllCash";
    public static final String P_TICKETNO = "TicketNo";
    public static final String P_TICKETCODE = "TicketCode";
    public static final String P_REMARK = "Remark";
    public static final String P_INVOICEID = "InvoiceId";
    public static final String P_INVOICETYPE = "InvoiceTypes";
    public static final String P_INVOICETITLE = "InvoiceTitle";
    public static final String P_CONSIGNEEID = "ConsigneeId";
    public static final String P_GOODSSCORE = "GoodsScore";
    public static final String P_SERVICESCORE = "ServicesScore";
    public static final String P_DELIVERYSPEEDSCORE = "DeliverySpeedScore";
    public static final String p_GOODCOMMENTSTATE = "GoodCommentState";
    public static final String P_NUMBER = "number";
    public static final String p_BABYBIRTHDAY = "BabyBirthday";
    public static final String p_ACTIVITYID = "ActivityId";


    public static final String P_FLASHID = "FlashId";
    public static final String P_GROUPID = "GroupId";
    public static final String P_WAREHOUSENAME = "WarehouseName";
    public static final String P_WAREHOUSE = "Warehouse";
    public static final String P_USERREALNAME = "UserRealName";
    public static final String P_USERIDCODE = "UserIdCode";
    public static final String P_USERCASHNUMBER = "UserCashNumber";
    /************************************************************************/


    /* params mod Value */
    /************************************************************************/

    /************************************************************************/
    /* 其他 */
    /**
     * ********************************************************************
     */
    private static Map<String, String> params;
    private static final String TOKEN = "3B51ACFFC9244DC481CF9454E207429A";
    public static final String PAGESIZE = "20";// 默认的一页条数

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
     * 删除收货地址反馈参数
     *
     * @param ConsigneeId
     * @return
     */
    public static Map<String, String> PostDeleteConsignee(String ConsigneeId) {
        params = new TreeMap<String, String>();
        params.put(P_CONSIGNEEID, ConsigneeId);
        params.put(P_SIGN, md5(getSign(M_POSTDELETECONSIGNEE, params)));
        params.put(P_METHOD, M_POSTDELETECONSIGNEE);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 删除收藏
     *
     * @param goodsSNS
     * @return
     */
    public static Map<String, String> PostDeleteCollectionS(String goodsSNS) {
        params = new TreeMap<String, String>();
        params.put(P_GOODSSN, goodsSNS);
        params.put(P_SIGN, md5(getSign(M_REMOVECOLLECTIONS, params)));
        params.put(P_METHOD, M_REMOVECOLLECTIONS);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 提交反馈意见
     *
     * @param content
     * @return
     */
    public static Map<String, String> postFeedBackParams(String content) {
        params = new TreeMap<String, String>();
        params.put(P_CONTENT, content);
        params.put(P_SIGN, md5(getSign(M_POSTFEEDBACK, params)));
        params.put(P_METHOD, M_POSTFEEDBACK);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 添加商品评论
     *
     * @param goodsSN
     * @param content
     * @param goodsScore
     * @param serviceScore
     * @param DeliverySpeedScore
     * @return
     */
    public static Map<String, String> addGoodsComment(String orderSN, String goodsSN,
                                                      String content, int goodsScore, int serviceScore,
                                                      int DeliverySpeedScore) {
        params = new TreeMap<String, String>();
        params.put(P_CONTENT, content);
        params.put(P_DELIVERYSPEEDSCORE, String.valueOf(DeliverySpeedScore));
        params.put(P_ORDERSN, orderSN);
        params.put(P_GOODSSN, goodsSN);
        params.put(P_GOODSSCORE, String.valueOf(goodsScore));
        params.put(P_SERVICESCORE, String.valueOf(serviceScore));
        params.put(P_SIGN, md5(getSign(M_ADDGOODSCOMMENT, params)));
        params.put(P_METHOD, M_ADDGOODSCOMMENT);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 绑定优惠券
     *
     * @param ticketno
     * @return
     */
    public static Map<String, String> postBindTicket(String ticketno) {
        params = new TreeMap<String, String>();
        params.put(P_TICKETNO, ticketno);
        params.put(P_SIGN, md5(getSign(M_POSTBINDTICKET, params)));
        params.put(P_METHOD, M_POSTBINDTICKET);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取登陆参数
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    public static Map<String, String> getLoginParams(String account,
                                                     String password) {
        params = new TreeMap<String, String>();
        params.put(P_ACCOUNT, account);
        params.put(P_PASSWORD, password);
        params.put(P_SIGN, md5(getSign(M_LOGIN, params)));
        params.put(P_METHOD, M_LOGIN);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 检查更新
     *
     * @return
     */
    public static Map<String, String> getUpdate() {
        params = new TreeMap<String, String>();
        params.put(P_SIGN, md5(getSign(M_GETUPDATE, params)));
        params.put(P_METHOD, M_GETUPDATE);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 添加或修改收货地址
     *
     * @param Consignee
     * @param Address
     * @param AreaID
     * @param CityID
     * @param ProvinceID
     * @param Tel
     * @param Mobile
     * @param ZipCode
     * @param IsDefault
     * @return
     */
    public static Map<String, String> PostAddOrMoidfyConsignee(
            String Consignee,// 收货人
            String ConsigneeId,//地址信息编号
            String Address,// 地址
            String AreaID,// 区域编号
            String CityID,// 市编号
            String ProvinceID,// 省编号
            String Tel,// 电话
            String Mobile,// 手机
            String ZipCode,// 邮编
            String IsDefault// 是否默认地址
    ) {
        params = new TreeMap<String, String>();
        params.put(P_CONSIGNEE, Consignee);
        params.put(P_CONSIGNEEID, ConsigneeId);
        params.put(P_ADDRESS, Address);
        params.put(P_AREAID, AreaID);
        params.put(P_CITYID, CityID);
        params.put(P_PROVID, ProvinceID);
        params.put(P_TEL, Tel);
        params.put(P_MOBILE, Mobile);
        params.put(P_ZIPCODE, ZipCode);// N
        params.put(P_ISDEFAULT, IsDefault);// N

        params.put(P_SIGN, md5(getSign(M_POSTADDORMOIDFYCONSIGNEE, params)));
        params.put(P_METHOD, M_POSTADDORMOIDFYCONSIGNEE);
        CommonUtils.logWrite("params:", params.toString());
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
        params.put(P_EMAIL, email);
        params.put(p_BABYBIRTHDAY, BabyBirthday);
        params.put("Recommend", recommend);
        params.put(P_SIGN, md5(getSign(M_REGISTER, params)));
        params.put(P_METHOD, M_REGISTER);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取商品列表参数
     *
     * @param Page       当前页数
     * @param BarCode    商品条形码
     * @param SearchKey  搜索词
     * @param CategoryId 分类编号
     * @param BrandId    品牌编号
     * @param StartPrice 大于？价格
     * @param EndPrice   小于？价格
     * @param SortField  排序字段
     * @return
     */
    public static Map<String, String> getGoodsListParams(int page,
                                                         String barCode, String searchKey, String categoryId,
                                                         String brandId, String startPrice, String endPrice, String sortField, String warehouseName) {
        params = new TreeMap<String, String>();
        params.put(P_PAGEINDEX, String.valueOf(page));
        params.put(P_BARCODE, barCode);
        params.put(P_SEARCHKEY, searchKey);
        params.put(P_CATEGORYID, categoryId);
        params.put(P_BRANDID, brandId);
        params.put(P_STARTPRICE, startPrice);
        params.put(P_ENDPRICE, endPrice);
        params.put(P_SORTFIELD, sortField);
        params.put(P_WAREHOUSENAME, warehouseName);
        params.put(P_PAGESIZE, PAGESIZE);

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETGOODSLIST, params)));
        params.put(P_METHOD, M_GETGOODSLIST);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取商品详情
     *
     * @param goodsSN 商品编号
     * @param barcode 商品条形码
     * @return
     */
    public static Map<String, String> getGoodsDetailsParams(String goodsSN,
                                                            String barcode) {
        params = new TreeMap<String, String>();
        String method = "";
        if (!StringUtils.isBlank(goodsSN)) {
            // 按照商品编码查询商品详情
            params.put(P_GOODSSN, goodsSN);
            method = M_GETGOODSDETAILS;
        } else if (!StringUtils.isBlank(barcode)) {
            // 按照商品条形码查询商品详情
            params.put(P_BARCODE, barcode);
            method = M_GETGOODSDETAILSBYBARCODE;
        }

        params.put(P_SIGN, md5(getSign(method, params)));
        params.put(P_METHOD, method);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取商品评论
     *
     * @param page
     * @param goodsSN
     * @param GoodCommentState 区分好评，中评和差评，默认全部 全部 0 好评 1 中评 2 差评 3
     * @return
     */
    public static Map<String, String> getGoodsCommentParams(int page,
                                                            String goodsSN, String GoodCommentState) {
        params = new TreeMap<String, String>();
        params.put(P_PAGEINDEX, String.valueOf(page));
        params.put(P_PAGESIZE, PAGESIZE);
        params.put(P_GOODSSN, goodsSN);
        params.put(p_GOODCOMMENTSTATE, GoodCommentState);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETGOODSCOMMENT, params)));
        params.put(P_METHOD, M_GETGOODSCOMMENT);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获得搜索热词列表
     *
     * @param number 获取的热词条数默认10条
     * @return
     */
    public static Map<String, String> getHotWord(String number) {
        params = new TreeMap<String, String>();
        params.put(P_NUMBER, number);

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETHOTWORD, params)));
        params.put(P_METHOD, M_GETHOTWORD);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取订单物流信息
     *
     * @param orderSN 订单编号
     * @return
     */
    public static Map<String, String> getLogisticOfOrderParams(String orderSN) {
        params = new TreeMap<String, String>();
        params.put(P_ORDERSN, orderSN);

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETLOGISTICOFORDER, params)));
        params.put(P_METHOD, M_GETLOGISTICOFORDER);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获得订单列表
     *
     * @param page
     * @param state      状态StateTypes 0：0- 全部，1-未完成，2-已取消 默认值 0 StateTypes 1：0- 全部1-待付款
     *                   2-待收货 3-待发货
     * @param stateTypes // 状态类型，跟State字段配合使用
     * @param StartTime  10位时间戳
     * @param EndTime    10位时间戳
     * @return
     */
    public static Map<String, String> getOrderList(int page, int stateTypes,
                                                   int state, String startTime, String endTime) {
        params = new TreeMap<String, String>();
        params.put(P_PAGEINDEX, String.valueOf(page));
        params.put(P_PAGESIZE, PAGESIZE);
        params.put(P_STATE, String.valueOf(state));
        params.put(P_STATETYPES, String.valueOf(stateTypes));
        params.put(P_STARTTIME, startTime);
        params.put(P_ENDTIME, endTime);

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETORDERLIST, params)));
        params.put(P_METHOD, M_GETORDERLIST);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获得站内消息列表
     *
     * @param page
     * @param state
     * @return
     */
    public static Map<String, String> getUserMessage(int page, int state) {
        params = new TreeMap<String, String>();
        params.put(P_PAGEINDEX, String.valueOf(page));
        params.put(P_PAGESIZE, PAGESIZE);
        params.put(P_STATE, String.valueOf(state));
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETUSERMESSAGE, params)));
        params.put(P_METHOD, M_GETUSERMESSAGE);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    public static Map<String, String> getUserMessageToread(String id) {
        params = new TreeMap<String, String>();


        params.put(P_IDS, id);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_POSTUSERSMSGTOREAD, params)));
        params.put(P_METHOD, M_POSTUSERSMSGTOREAD);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }


    /**
     * 获得优惠券可用详情
     *
     * @param TicketNo
     * @return
     */
    public static Map<String, String> getTicket(String TicketNo) {
        params = new TreeMap<String, String>();
        params.put(P_TICKETNO, TicketNo);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_TICKET, params)));
        params.put(P_METHOD, M_TICKET);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获得订单详情参数
     *
     * @param orderSN 订单编号
     * @return
     */
    public static Map<String, String> getOrder(String orderSN) {
        params = new TreeMap<String, String>();
        params.put(P_ORDERSN, orderSN);

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETORDER, params)));
        params.put(P_METHOD, M_GETORDER);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取支付宝支付数据
     *
     * @param orderSN
     * @return
     */
    public static Map<String, String> getAlipaydata(String orderSN) {
        params = new TreeMap<String, String>();
        params.put(P_ORDERSN, orderSN);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETALIPAYDATA, params)));
        params.put(P_METHOD, M_GETALIPAYDATA);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取银联支付数据
     *
     * @param orderSN
     * @return
     */
    public static Map<String, String> getUPPayData(String orderSN) {
        params = new TreeMap<String, String>();
        params.put(P_ORDERSN, orderSN);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETUPPAYDATA, params)));
        params.put(P_METHOD, M_GETUPPAYDATA);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取优惠券列表
     *
     * @param page   当前页码
     * @param isUsed 是否已使用(0-未使用，1-已使用)
     * @return
     */
    public static Map<String, String> getTicketList(int page, int isUsed) {
        params = new TreeMap<String, String>();
        params.put(P_PAGEINDEX, String.valueOf(page));
        params.put(P_PAGESIZE, PAGESIZE);
        params.put(P_ISUSED, String.valueOf(isUsed));
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETTICKETLIST, params)));
        params.put(P_METHOD, M_GETTICKETLIST);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取优惠券可用信息
     *
     * @param ticketNo 优惠券编码
     * @return
     */
    public static Map<String, String> getTicketInfo(String ticketNo,String cartItems) {
        params = new TreeMap<String, String>();
        params.put(P_TICKETNO, ticketNo);
        params.put(P_CARTITEMS, cartItems);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETTICKETINFO, params)));
        params.put(P_METHOD, M_GETTICKETINFO);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取已购买商品列表（待评价）
     *
     * @param page
     * @param State
     * @param StartTime
     * @param EndTime
     * @return
     */
    public static Map<String, String> getGoodListByCommentState(int page,
                                                                int State, String StartTime, String EndTime) {
        params = new TreeMap<String, String>();
        params.put(P_PAGEINDEX, String.valueOf(page));
        params.put(P_PAGESIZE, PAGESIZE);
        params.put(P_STATE, String.valueOf(State));
        params.put(P_STARTTIME, StartTime);
        params.put(P_ENDTIME, EndTime);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETGOODLISTBYCOMMENTSTATE, params)));
        params.put(P_METHOD, M_GETGOODLISTBYCOMMENTSTATE);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取我的收藏列表
     *
     * @param pageIndex
     * @param State     收藏的商品属性 我的全部收藏 0 降价商品 1 促销活动 2
     * @return
     */
    public static Map<String, String> getFavoritesList(int pageIndex,
                                                       String State) {
        params = new TreeMap<String, String>();
        params.put(P_PAGEINDEX, String.valueOf(pageIndex));
        params.put(P_PAGESIZE, PAGESIZE);
        params.put(P_STATE, State);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETFAVORITES, params)));
        params.put(P_METHOD, M_GETFAVORITES);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 添加或修改购物车
     *
     * @param goodsSN   商品编号
     * @param amount    数量
     * @param operation 运算方式(+=,-=,=,默认+=)
     * @return
     */
    public static Map<String, String> postModifyShoppingCart(String goodsSN,
                                                             String amount, String operation) {
        params = new TreeMap<String, String>();
        params.put(P_GOODS, getAddCartString(goodsSN, amount, operation));
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_POSTMODIFYSHOPPINGCART, params)));
        params.put(P_METHOD, M_POSTMODIFYSHOPPINGCART);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 修改购物车选中状态
     *
     * @param cartItems
     * @return
     */
    public static Map<String, String> getChangeProductNumInCart(String cartItems) {
        params = new TreeMap<String, String>();
        params.put(P_CARTITEMS, cartItems);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_CHANGEPRODUCTNUMINCART, params)));
        params.put(P_METHOD, M_CHANGEPRODUCTNUMINCART);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 添加购物车
     *
     * @param goodsSN 商品编码
     * @return
     */
    public static Map<String, String> addFavorites(String goodsSN) {
        params = new TreeMap<String, String>();
        params.put(P_GOODSSN, goodsSN);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_ADDFAVORITES, params)));
        params.put(P_METHOD, M_ADDFAVORITES);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取活动详细信息
     *
     * @param ActivityId
     * @return
     */
    public static Map<String, String> getActivity(String ActivityId) {
        params = new TreeMap<String, String>();
        params.put(p_ACTIVITYID, ActivityId);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GetActivity, params)));
        params.put(P_METHOD, M_GetActivity);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 组装加入购物车的字符串
     *
     * @param goodsSN
     * @param amount
     * @param operation
     * @return
     */
    private static String getAddCartString(String goodsSN, String amount,
                                           String operation) {
        // 商品编号:数量:运算方式(+=,-=,=,默认+=)“,” 分割多个商品
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(goodsSN);
        strBuff.append(":");
        strBuff.append(amount);
        strBuff.append(":");
        strBuff.append(operation);
        return strBuff.toString();
    }

    /**
     * 根据地区和支付方式获得支付方式及送货方式
     *
     * @param areaId    地区编号
     * @param paymentId 支付方式编号 可不传
     * @return
     */
    public static Map<String, String> getPaymentShipping(String areaId,
                                                         String wareHouse) {
        params = new TreeMap<String, String>();
        params.put(P_AREAID, areaId);
        params.put(P_WAREHOUSE, wareHouse);

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETPAYMENTSHIPPING, params)));
        params.put(P_METHOD, M_GETPAYMENTSHIPPING);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获得运费
     *
     * @param provId     省编号
     * @param areaId     区域编号
     * @param shippingID 配送方式id
     * @return
     */
    public static Map<String, String> getShippingFreight(String provId,
                                                         String areaId, String paymentID, String cartItems) {
        params = new TreeMap<String, String>();
        params.put(P_PROVID, provId);
        params.put(P_AREAID, areaId);
        params.put(P_PAYMENTID, paymentID);
        params.put(P_CARTITEMS, cartItems);

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETSHIPPINGFREIGHT, params)));
        params.put(P_METHOD, M_GETSHIPPINGFREIGHT);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 提交订单
     *
     * @param consignee    联系人
     * @param provinceid   省编号
     * @param cityId       市编号
     * @param areaId       县/区编号
     * @param zipCode      邮政编码 非必须
     * @param address      地址
     * @param mobile       手机 和电话必须有一个不为空
     * @param tel          电话 和手机必须有一个不为空
     * @param email        电子邮件非必须
     * @param shippingId   配送方式编号
     * @param paymentId    支付方式编号
     * @param allCash      现金账户支付金额 非必须
     * @param ticketNo     优惠券编号非必须
     * @param remark       留言 非必须
     * @param invoiceId    发票类型ID非必须
     * @param invoiceTitle 发票抬头 非必须
     * @return
     */
    public static Map<String, String> submitOrder(String consignee,
                                                  String provinceid, String cityid, String areaid, String zipcode,
                                                  String address, String mobile, String tel, String email,
                                                  String shippingid, String paymentid, String allcash,
                                                  String ticketno, String remark, String invoiceid,
                                                  String invoicetitle) {
        params = new TreeMap<String, String>();
        params.put(P_CONSIGNEE, consignee);
        params.put(P_PROVINCEID, provinceid);
        params.put(P_CITYID, cityid);
        params.put(P_AREAID, areaid);
        params.put(P_ZIPCODE, zipcode);
        params.put(P_ADDRESS, address);
        params.put(P_MOBILE, mobile);
        params.put(P_TEL, tel);
        params.put(P_EMAIL, email);
        params.put(P_SHIPPINGID, shippingid);
        params.put(P_PAYMENTID, paymentid);
        params.put(P_ALLCASH, allcash);
        params.put(P_TICKETNO, ticketno);
        params.put(P_REMARK, remark);
        params.put(P_INVOICEID, invoiceid);
        params.put(P_INVOICETITLE, invoicetitle);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_POSTSUBMITORDER, params)));
        params.put(P_METHOD, M_POSTSUBMITORDER);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 提交订单
     *
     * @param consigneeId    收货地址ID
     * @param paymentId      支付方式
     * @param userRealName   真实姓名
     * @param userIdCode     身份证号码
     * @param invoiceTypes   发票类型
     * @param invoiceTitle   发票抬头
     * @param ticketCode     优惠券编号
     * @param userCashNumber 使用现金账户钱数
     * @param remark         备注
     * @param cartItems      选中商品字符串
     * @return
     */
    public static Map<String, String> getSubmitOrder(String consigneeId, String paymentId,
                                                     String userRealName, String userIdCode,
                                                     String invoiceTypes, String invoiceTitle,
                                                     String ticketCode, String userCashNumber,
                                                     String remark, String cartItems) {
        params = new TreeMap<String, String>();
        params.put(P_CONSIGNEEID, consigneeId);
        params.put(P_PAYMENTID, paymentId);
        params.put(P_USERREALNAME, userRealName);
        params.put(P_USERIDCODE, userIdCode);
        params.put(P_INVOICETYPE, invoiceTypes);
        params.put(P_INVOICETITLE, invoiceTitle);
        params.put(P_TICKETCODE, ticketCode);
        params.put(P_USERCASHNUMBER, userCashNumber);
        params.put(P_REMARK, remark);
        params.put(P_CARTITEMS, cartItems);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_POSTSUBMITORDER, params)));
        params.put(P_METHOD, M_POSTSUBMITORDER);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 根据精品特卖Id获取商品列表
     *
     * @param flashId   精品特卖Id
     * @param pageIndex 页数
     * @return
     */
    public static Map<String, String> getTemaiDetaild(String flashId, int pageIndex, String OrderIndex) {
        params = new TreeMap<String, String>();

        params.put(P_FLASHID, flashId);
        params.put(P_PAGEINDEX, String.valueOf(pageIndex));
        params.put(P_PAGESIZE, PAGESIZE);
        params.put(P_ORDERINDEX, OrderIndex);
        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETTEMAIDETAILD, params)));
        params.put(P_METHOD, M_GETTEMAIDETAILD);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 分页获取精品特卖列表
     *
     * @param pageIndex
     * @return
     */
    public static Map<String, String> getTemaiToPager(int pageIndex) {
        params = new TreeMap<String, String>();

        params.put(P_PAGEINDEX, String.valueOf(pageIndex));
        params.put(P_PAGESIZE, PAGESIZE);

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETTEMAITOPAGER, params)));
        params.put(P_METHOD, M_GETTEMAITOPAGER);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    /**
     * 获取首页推荐商品分类列表
     *
     * @param groupId
     * @return
     */
    public static Map<String, String> getStrategyById(int groupId) {
        params = new TreeMap<String, String>();

        params.put(P_GROUPID, groupId + "");

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETSTRATERYBYID, params)));
        params.put(P_METHOD, M_GETSTRATERYBYID);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }

    public static Map<String, String> getPaymentData(String OrderSN, String PaymentTypes) {
        params = new TreeMap<String, String>();

        params.put(P_ORDERSN, OrderSN);
        params.put(P_PAYMENTTYPES, PaymentTypes);

        // 以下为共同参数
        params.put(P_SIGN, md5(getSign(M_GETPAYMENTDATA, params)));
        params.put(P_METHOD, M_GETPAYMENTDATA);
        CommonUtils.logWrite("params:", params.toString());
        return params;
    }


    /**
     * 获取sign加密字符串
     *
     * @param methon
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

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String toHexString(byte[] b) { // byte to String
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}
