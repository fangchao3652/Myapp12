package com.example.db;

import android.os.Environment;

import com.example.application.MyApplication;
import com.example.common.CommonUtils;
import com.example.common.StringUtils;
import com.example.fc.R;
import com.example.greenBean.BondedAreaBean;
import com.example.greenBean.BrandDetailBean;
import com.example.greenBean.CategoryDetailBean;
import com.example.greenBean.CountryBean;
import com.example.greenBean.DistrictBean;
import com.example.greenBean.StrategyGroupBean;
import com.example.greenDao.BrandDetailBeanDao;
import com.example.greenDao.CategoryDetailBeanDao;
import com.example.greenDao.DistrictBeanDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fc on 2015/1/20.
 * 数据库操作工具类
 */
public class SqliteHelper {


    public static final String DB_FILE_NAME = "databases"; // 保存的数据库文件名
    public static final String DB_NAME = "supuy_malls300.db"; // 保存的数据库文件名
    public static final String PACKAGE_NAME = CommonUtils.getAppPackageName();
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/" + DB_FILE_NAME; // 在手机里存放数据库的位置
    private final static int BUFFER_SIZE = 400000;

    public static void insertDatabase() {

        try {
            if (!(new File(DB_PATH + "/" + DB_NAME).exists())) {// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库

                InputStream is = MyApplication.getInstance().getResources()
                        .openRawResource(R.raw.supuy_malls300); // 欲导入的数据库

                // 创建databases文件夹
                File f = new File(DB_PATH);
                if (!f.exists()) {
                    f.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(DB_PATH + "/"
                        + DB_NAME);

                byte[] buffer = new byte[BUFFER_SIZE];

                int count = 0;

                while ((count = is.read(buffer)) > 0) {

                    fos.write(buffer, 0, count);

                }

                fos.close();

                is.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取省份数据集合
     *
     * @return
     */
    public static List<DistrictBean> getDistrict() {
        return MyApplication.getInstance().getDaoSession().getDistrictBeanDao()
                .queryBuilder().where(DistrictBeanDao.Properties.ParentID.eq("000000")).list();
    }

    /**
     * 获取市数据集合
     *
     * @return
     */
    public static List<DistrictBean> getCity(String provinceID) {
        return MyApplication.getInstance().getDaoSession().getDistrictBeanDao()
                .queryBuilder().where(DistrictBeanDao.Properties.ParentID.eq(provinceID))
                .list();
    }

    /**
     * 获取地区数据集合
     *
     * @return
     */
    public static List<DistrictBean> getArea(String cityID) {
        return MyApplication.getInstance().getDaoSession().getDistrictBeanDao()
                .queryBuilder().where(DistrictBeanDao.Properties.ParentID.eq(cityID)).list();
    }

    /**
     * 获取单个地址详情
     *
     * @return
     */
    public static DistrictBean getAddress(String areaCode) {
        return MyApplication.getInstance().getDaoSession().getDistrictBeanDao()
                .queryBuilder().where(DistrictBeanDao.Properties.AreaCode.eq(areaCode))
                .unique();
    }

    /**
     * 获取所有父 分类
     *
     * @return
     */
    public static List<CategoryDetailBean> getAllParentCategorys() {
        List<CategoryDetailBean> list = new ArrayList<>();

        // 获取跟分类数据
        list = MyApplication
                .getInstance()
                .getDaoSession()
                .getCategoryDetailBeanDao()
                .queryBuilder()
                .where(CategoryDetailBeanDao.Properties.ParentID
                        .eq("0")).list();
        return list;
    }

   /* *//**
     * 获取所有分类数据
     *
     * @return
     *//*
    public static List<CategoryBean> getCategory() {
        List<CategoryBean> rlist = new ArrayList<CategoryBean>();
        List<CategoryDetailBean> list = null;
        List<CategoryDetailBean> subList = null;
        CategoryBean b;
        // 获取跟分类数据
        list = MyApplication
                .getInstance()
                .getDaoSession()
                .getCategoryDetailBeanDao()
                .queryBuilder()
                .where(cc.android.supu.greenDao.CategoryDetailBeanDao.Properties.ParentID
                        .eq("0")).list();
        for (int i = 0; i < list.size(); i++) {
            b = new CategoryBean();
            b.setCategoryID(list.get(i).getCategoryID());
            b.setCategoryName(list.get(i).getCategoryName());
            subList = MyApplication
                    .getInstance()
                    .getDaoSession()
                    .getCategoryDetailBeanDao()
                    .queryBuilder()
                    .where(cc.android.supu.greenDao.CategoryDetailBeanDao.Properties.ParentID
                            .eq(b.getCategoryID())).list();
            b.setSubCategory(subList);
            rlist.add(b);
        }
        return rlist;
    }

    *//**
     * 根据品牌ID获取分类列表
     *
     * @param brandId
     * @return
     *//*
    public static List<CategoryBean> getCategoryById(String brandId) {
        List<CategoryBean> rlist = new ArrayList<CategoryBean>();
        List<CategoryDetailBean> list = null;
        List<CategoryDetailBean> lists = null;
        List<CategoryDetailBean> subList = null;
        CategoryBean b;
        list = MyApplication
                .getInstance()
                .getDaoSession()
                .getCategoryDetailBeanDao()
                .queryBuilder()
                .where(cc.android.supu.greenDao.CategoryDetailBeanDao.Properties.ParentID
                        .eq("0")).list();
        lists = getCategoryWithBrandId(brandId);
        for (int i = 0; i < list.size(); i++) {
            b = new CategoryBean();
            b.setCategoryID(list.get(i).getCategoryID());
            b.setCategoryName(list.get(i).getCategoryName());
            subList = new ArrayList<CategoryDetailBean>();
            for (int j = 0; j < lists.size(); j++) {
                if (lists.get(j).getParentID()
                        .equals(list.get(i).getCategoryID())) {
                    subList.add(lists.get(j));
                }
            }
            b.setSubCategory(subList);
            if (b.getSubCategory().size() > 0) {
                rlist.add(b);
            }
        }
        return rlist;
    }
*/
    /**
     * 获取所有品牌数据 根据sortorder排序
     *
     * @param number 想获取的条数
     * @return
     */
    public static List<BrandDetailBean> getAllBrand(int number) {
        return MyApplication
                .getInstance()
                .getDaoSession()
                .getBrandDetailBeanDao()
                .queryBuilder()
                .orderDesc(
                       BrandDetailBeanDao.Properties.SortOrder)
                .list().subList(0, number);
    }

    /**
     * 根据品牌id获得 品牌bean
     * supu3.0
     *
     * @param brandid 品牌id
     * @return
     */
    public static BrandDetailBean getBrandByBrandId(String brandid) {
        return MyApplication
                .getInstance()
                .getDaoSession()
                .getBrandDetailBeanDao()
                .queryBuilder().where(BrandDetailBeanDao.Properties.BrandID.eq(brandid))
                .unique();
    }

    /**
     * 根据分类id获得分类bean
     * supu3.0
     *
     * @param id 品牌id
     * @return
     */
    public static CategoryDetailBean getCategoryByCategoryId(String id) {
        return MyApplication
                .getInstance()
                .getDaoSession()
                .getCategoryDetailBeanDao()
                .queryBuilder().where(CategoryDetailBeanDao.Properties.CategoryID.eq(id))
                .unique();
    }

    /**
     * 根据分类id获取品牌集合
     *
     * @param CategoryId 分类id
     * @return
     */
    public static List<BrandDetailBean> getBrandWithCategoryId(String CategoryId) {
        return MyApplication
                .getInstance()
                .getDaoSession()
                .getBrandDetailBeanDao()
                .queryBuilder()
                .where(BrandDetailBeanDao.Properties.CategoryList
                        .like("%-" + CategoryId + "-%"))
                .orderAsc(
                        BrandDetailBeanDao.Properties.SortOrder)
                .list();
    }

    /**
     * 获取所有分类数据 根据 排序条件排序
     *
     * @return
     */
    public static List<CategoryDetailBean> getCategoryBySortOrder() {
        return MyApplication
                .getInstance()
                .getDaoSession()
                .getCategoryDetailBeanDao()
                .queryBuilder()
                .where(CategoryDetailBeanDao.Properties.ParentID
                        .eq("0"))
                .orderAsc(
                    CategoryDetailBeanDao.Properties.SortOrder)
                .list();
    }


    /**
     * 根据品牌ID获取分类列表
     *
     * @param brandId
     * @return
     */
    public static List<CategoryDetailBean> getCategoryWithBrandId(String brandId) {
        List<CategoryDetailBean> categoryList = new ArrayList<CategoryDetailBean>();
        BrandDetailBean bean = MyApplication
                .getInstance()
                .getDaoSession()
                .getBrandDetailBeanDao()
                .queryBuilder()
                .where(BrandDetailBeanDao.Properties.BrandID
                        .eq(brandId)).unique();
        String[] categoryDeatailIds = null;
        if (bean != null) {
            String categoryListStr = bean.getCategoryList();
            if (StringUtils.isBlank(categoryListStr)
                    || categoryListStr.length() < 3) {
                return null;
            } else {
                categoryListStr = categoryListStr.substring(1,
                        categoryListStr.length());
                categoryDeatailIds = categoryListStr.split("-");
            }
        }
        if (categoryDeatailIds != null) {
            for (int i = 0; i < categoryDeatailIds.length; i++) {
                CategoryDetailBean item = MyApplication
                        .getInstance()
                        .getDaoSession()
                        .getCategoryDetailBeanDao()
                        .queryBuilder()
                        .where(CategoryDetailBeanDao.Properties.CategoryID
                                .eq(categoryDeatailIds[i])).unique();
                if (item != null) {
                    categoryList.add(item);
                }
            }
        }
        return categoryList;
    }

    /**
     * 根据大的分类获取其子分类列表
     *
     * @param CategoryName
     * @return
     */
    public static List<CategoryDetailBean> getsubCategoryByParentName(String CategoryName) {
        List<CategoryDetailBean> list = null;
        CategoryDetailBean categoryDetailBean;
        categoryDetailBean = MyApplication
                .getInstance()
                .getDaoSession()
                .getCategoryDetailBeanDao()
                .queryBuilder()
                .where(CategoryDetailBeanDao.Properties.CategoryName
                        .eq(CategoryName)).unique();
        if (categoryDetailBean != null) {
            list = MyApplication
                    .getInstance()
                    .getDaoSession()
                    .getCategoryDetailBeanDao()
                    .queryBuilder()
                    .where(CategoryDetailBeanDao.Properties.ParentID
                            .eq(categoryDetailBean.getCategoryID())).list();
        }
        return list;
    }

    /**
     * 根据大的分类id获取其子分类列表
     *
     * @param parentid 大的分类id
     * @return
     */
    public static List<CategoryDetailBean> getsubCategoryByParentId(String parentid) {
        List<CategoryDetailBean> list = null;
        CategoryDetailBean categoryDetailBean;
        categoryDetailBean = MyApplication
                .getInstance()
                .getDaoSession()
                .getCategoryDetailBeanDao()
                .queryBuilder()
                .where(CategoryDetailBeanDao.Properties.CategoryID
                        .eq(parentid)).unique();
        if (categoryDetailBean != null) {
            list = MyApplication
                    .getInstance()
                    .getDaoSession()
                    .getCategoryDetailBeanDao()
                    .queryBuilder()
                    .where(CategoryDetailBeanDao.Properties.ParentID
                            .eq(categoryDetailBean.getCategoryID())).list();
        }
        return list;
    }

    /**
     * 获取海外直邮支持的国家
     *
     * @return
     */
    public static List<CountryBean> getCountry() {
        return MyApplication.getInstance().getDaoSession().getCountryBeanDao()
                .queryBuilder().list();
    }

    /**
     * 获取保税区仓库列表
     *
     * @return
     */
    public static List<BondedAreaBean> getBondedArea() {
        return MyApplication.getInstance().getDaoSession().getBondedAreaBeanDao()
                .queryBuilder().list();
    }

    /**
     * 获取首页商品分组数据
     *
     * @return
     */
    public static List<StrategyGroupBean> getStrategyGroup() {
        return MyApplication.getInstance().getDaoSession().getStrategyGroupBeanDao()
                .queryBuilder().list();
    }
}
