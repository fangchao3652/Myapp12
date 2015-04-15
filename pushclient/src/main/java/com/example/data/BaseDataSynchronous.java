package com.example.data;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import cc.android.supu.application.MyApplication;
import cc.android.supu.bean.BaseBean;
import cc.android.supu.bean.CategoryBean;
import cc.android.supu.bean.ResultListBean;
import cc.android.supu.bean.greenBean.BondedAreaBean;
import cc.android.supu.bean.greenBean.BrandDetailBean;
import cc.android.supu.bean.greenBean.CategoryDetailBean;
import cc.android.supu.bean.greenBean.CountryBean;
import cc.android.supu.bean.greenBean.DistrictBean;
import cc.android.supu.bean.greenBean.StrategyGroupBean;
import cc.android.supu.common.CommonUtils;
import cc.android.supu.greenDao.BondedAreaBeanDao;
import cc.android.supu.greenDao.BrandDetailBeanDao;
import cc.android.supu.greenDao.CategoryDetailBeanDao;
import cc.android.supu.greenDao.CountryBeanDao;
import cc.android.supu.greenDao.DistrictBeanDao;
import cc.android.supu.greenDao.StrategyGroupBeanDao;

/**
 * 同步基础数据共同
 * Created by zhangxinhao on 2015/3/10.
 */
public class BaseDataSynchronous implements DataHelper.DataListener {

    /**
     * 同步数据成功与否监听
     */
    public interface DataSynchronousListener {
        /**
         * 同步数据成功
         */
        public void sucess();

        /**
         * 同步数据失败
         */
        public void fail();
    }

    private DataSynchronousListener mDataSynchronousListener = null;

    private String TAG = "BaseDataSynchronous";
    Context context;
    /**
     * 数据更新成功标志
     */
    private boolean r1 = false, r2 = false, r3 = false, r4 = false, r5 = false, r6 = false;

    /**
     * 是否重新设置了数据同步闹钟
     */
    private boolean alrmFlg = false;

    public BaseDataSynchronous(Context context, DataSynchronousListener mDataSynchronousListener) {
        this.context = context;
        this.mDataSynchronousListener = mDataSynchronousListener;
    }

    /**
     * 读取网络获取基础数据
     */
    public void getDataWithNetWork() {
        CommonUtils.logWrite(TAG, "数据同步开始");
        // 获取省市区信息
        DataHelper piclist = new PostDataHelper(URLHelper.getURL(
                URLHelper.MODULE_COMMON, URLHelper.M_GETALLDISTRICT),
                URLHelper.getBaseParams(URLHelper.M_GETALLDISTRICT), this, 0);
        piclist.execute();

        // 获取分类类表
        DataHelper category = new PostDataHelper(URLHelper.getURL(
                URLHelper.MODULE_COMMON, URLHelper.M_GETALLCATEGORY),
                URLHelper.getBaseParams(URLHelper.M_GETALLCATEGORY), this, 1);
        category.execute();

        // 获取品牌列表
        DataHelper brand = new PostDataHelper(URLHelper.getURL(
                URLHelper.MODULE_COMMON, URLHelper.M_GETALLBRAND),
                URLHelper.getBaseParams(URLHelper.M_GETALLBRAND), this, 2);
        brand.execute();
        //获取首页推荐商品筛选分组
        DataHelper group = new PostDataHelper(URLHelper.getURL(URLHelper.VERSION_3,
                URLHelper.MOUDLE_STRATEGY, URLHelper.M_GETSTRATEGYGROUP),
                URLHelper.getBaseParams(URLHelper.M_GETSTRATEGYGROUP), this, 3);
        group.execute();
        //获取支持的海外直邮国家
        DataHelper country = new PostDataHelper(URLHelper.getURL(URLHelper.VERSION_3,
                URLHelper.MOUDLE_PRODUCT, URLHelper.M_GETGLOBALPURCHASECOUNTRY),
                URLHelper.getBaseParams(URLHelper.M_GETGLOBALPURCHASECOUNTRY), this, 4);
        country.execute();
        //获取支持的保税区仓库
        DataHelper bondedArea = new PostDataHelper(URLHelper.getURL(URLHelper.VERSION_3,
                URLHelper.MOUDLE_PRODUCT, URLHelper.M_GETBONDEDAREA),
                URLHelper.getBaseParams(URLHelper.M_GETBONDEDAREA), this, 5);
        bondedArea.execute();
        // 获取支付方式
        DataHelper mHelper = new PostDataHelper(URLHelper.getURL(
                URLHelper.MODULE_PAYMENT, URLHelper.M_GETPAYMENT),
                URLHelper.getBaseParams(URLHelper.M_GETPAYMENT), this, 6);
        mHelper.execute();
    }

    @Override
    public void sucess(JSONObject response, int code) {
        switch (code) {

            case 0:
                // 地址返回数据
                final ResultListBean rb0 = (ResultListBean) VolleyResponseHelper
                        .jsonToBean(response, 15);
                if (rb0.getRetCode() == 0) {
                    Thread mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<BaseBean> list = rb0.getListBean();
                            DistrictBeanDao dao = MyApplication.getDaoSession()
                                    .getDistrictBeanDao();
                            for (int i = 0; i < list.size(); i++) {
                                DistrictBean bean = (DistrictBean) list.get(i);
                                dao.insertOrReplace(bean);
                            }
                            CommonUtils.logWrite("TAG", "省市区信息插入数据库成功");
                            r1 = true;
                            dataSynchronousSucess();
                        }
                    });
                    mThread.start();
                } else {
                    dataSynchronousFail();
                }
                break;
            case 1:
                final ResultListBean rb1 = (ResultListBean) VolleyResponseHelper
                        .jsonToBean(response, 5);
                if (rb1.getRetCode() == 0) {

                    Thread mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            List<BaseBean> categoryData = rb1.getListBean();

                            // 将数据插入数据库---start
                            CategoryDetailBeanDao dao = MyApplication.getDaoSession()
                                    .getCategoryDetailBeanDao();
                            for (int i = 0; i < categoryData.size(); i++) {
                                CategoryBean b = (CategoryBean) categoryData.get(i);
                                dao.insertOrReplace(b);
                                List<CategoryDetailBean> list = b.getSubCategory();
                                for (int j = 0; j < list.size(); j++) {
                                    CategoryDetailBean c = list.get(j);
                                    dao.insertOrReplace(c);
                                }
                            }
                            // 将数据插入数据库---end
                            CommonUtils.logWrite("TAG", "分类数据插入数据库成功");
                            r2 = true;
                            dataSynchronousSucess();
                        }
                    });
                    mThread.start();
                } else {
                    dataSynchronousFail();
                }

                break;
            case 2:
                final ResultListBean rb2 = (ResultListBean) VolleyResponseHelper
                        .jsonToBean(response, 7);
                if (rb2.getRetCode() == 0) {

                    Thread mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<BaseBean> brandData = rb2.getListBean();

                            // 将数据插入数据库---start
                            BrandDetailBeanDao dao = MyApplication.getDaoSession()
                                    .getBrandDetailBeanDao();
                            for (int i = 0; i < brandData.size(); i++) {
                                BrandDetailBean b = (BrandDetailBean) brandData.get(i);
                                dao.insertOrReplace(b);
                            }
                            // 将数据插入数据库---end
                            CommonUtils.logWrite("TAG", "品牌数据插入数据库成功");
                            r3 = true;
                            dataSynchronousSucess();
                        }
                    });
                    mThread.start();
                } else {
                    dataSynchronousFail();
                }
                break;
            case 3:
                //首页推荐商品分组
                final ResultListBean rb3 = (ResultListBean) VolleyResponseHelper
                        .jsonToBean(response, 35);
                if (rb3.getRetCode() == 0) {

                    Thread mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<BaseBean> strategyGroupData = rb3.getListBean();

                            // 将数据插入数据库---start
                            StrategyGroupBeanDao dao = MyApplication.getDaoSession()
                                    .getStrategyGroupBeanDao();
                            for (int i = 0; i < strategyGroupData.size(); i++) {
                                StrategyGroupBean b = (StrategyGroupBean) strategyGroupData.get(i);
                                dao.insertOrReplace(b);
                            }
                            // 将数据插入数据库---end
                            CommonUtils.logWrite("TAG", "首页推荐商品分组插入数据库成功");
                            r4 = true;
                            dataSynchronousSucess();
                        }
                    });
                    mThread.start();
                } else {
                    dataSynchronousFail();
                }
                break;
            case 4:
                //海外直邮国家
                final ResultListBean rb4 = (ResultListBean) VolleyResponseHelper
                        .jsonToBean(response, 36);
                if (rb4.getRetCode() == 0) {

                    Thread mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<BaseBean> countryData = rb4.getListBean();

                            // 将数据插入数据库---start
                            CountryBeanDao dao = MyApplication.getDaoSession()
                                    .getCountryBeanDao();
                            for (int i = 0; i < countryData.size(); i++) {
                                CountryBean b = (CountryBean) countryData.get(i);
                                dao.insertOrReplace(b);
                            }
                            // 将数据插入数据库---end
                            CommonUtils.logWrite("TAG", "海外直邮国家插入数据库成功");
                            r5 = true;
                            dataSynchronousSucess();
                        }
                    });
                    mThread.start();
                } else {
                    dataSynchronousFail();
                }
                break;
            case 5:
                //保税区仓库
                final ResultListBean rb5 = (ResultListBean) VolleyResponseHelper
                        .jsonToBean(response, 37);
                if (rb5.getRetCode() == 0) {

                    Thread mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<BaseBean> bondedAreaData = rb5.getListBean();

                            // 将数据插入数据库---start
                            BondedAreaBeanDao dao = MyApplication.getDaoSession()
                                    .getBondedAreaBeanDao();
                            for (int i = 0; i < bondedAreaData.size(); i++) {
                                BondedAreaBean b = (BondedAreaBean) bondedAreaData.get(i);
                                dao.insertOrReplace(b);
                            }
                            // 将数据插入数据库---end
                            CommonUtils.logWrite("TAG", "保税区仓库插入数据库成功");
                            r6 = true;
                            dataSynchronousSucess();
                        }
                    });
                    mThread.start();
                } else {
                    dataSynchronousFail();
                }
                break;
            case 6:
                // 支付方式
                ResultListBean rb6 = (ResultListBean) VolleyResponseHelper
                        .jsonToBean(response, 42);
                if (rb6.getRetCode() == 0) {
                    DiskDataHelper.getInstance().saveListToCache("payment", response);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void err(String error, int code) {
        //数据同步失败
        dataSynchronousFail();
        CommonUtils.logWrite("-------" + code, error);
    }

    /**
     * 数据同步成功
     */
    private void dataSynchronousSucess() {
        if (r1 && r2 && r3 && r4 && r5 && r6) {

            if (mDataSynchronousListener != null) {
                mDataSynchronousListener.sucess();
            }

        }
    }

    /**
     * 数据同步失败
     */
    private void dataSynchronousFail() {
        if (!alrmFlg) {
            alrmFlg = true;
            if (mDataSynchronousListener != null) {
                mDataSynchronousListener.fail();
            }

        }
    }
}
