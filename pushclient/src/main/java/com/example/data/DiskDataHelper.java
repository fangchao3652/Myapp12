package com.example.data;

import com.example.application.MyApplication;
import com.example.common.CommonUtils;
import com.example.common.StringUtils;
import com.example.common.TimeUtils;
import com.example.greenBean.CacheBean;
import com.example.greenDao.CacheBeanDao;
import com.example.greenDao.DaoSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by fc on 2015/1/19.
 * <p/>
 * 本地数据获取工具类
 */
public class DiskDataHelper {

    /**
     * 列表数据
     */
    public final static int CACHE_LIST = 1;

    private final static String TAG = "_Cache";

    private static DiskDataHelper mInstance;
    private DaoSession daoSession;

    private DiskDataHelper() {
        daoSession = MyApplication.getInstance().getDaoSession();
    }

    public static DiskDataHelper getInstance() {
        if (mInstance == null) {
            mInstance = new DiskDataHelper();
        }
        return mInstance;
    }

    /**
     * 根据类型从缓存中取出数据
     *
     * @param key  关键字
     * @param type 类型
     * @return
     */
    public synchronized JSONObject getJsonFromCache(String key, int type) {
        switch (type) {
            case CACHE_LIST://列表缓存
                return getListFromCache(key);
            default:
                return null;
        }
    }

    /**
     * 根据类型保存Json到缓存数据中去
     *
     * @param key    关键字
     * @param object Json对象
     * @param type   Json类型 对应CACHE
     * @return
     */
    public synchronized boolean saveJsonToCache(String key, JSONObject object, int type) {
        switch (type) {
            case CACHE_LIST://列表
                return saveListToCache(key, object);
            default:
                return false;
        }
    }

    /**
     * 保存列表数据
     *
     * @param key    关键字(接口名称)
     * @param object 列表JsonObject
     * @return
     */
    public boolean saveListToCache(String key, JSONObject object) {
        if (checkData(key, object)) {
            CacheBeanDao dao = daoSession.getCacheBeanDao();
            CacheBean bean = new CacheBean(key, object.toString(), TimeUtils.getCurrentTimeInLong() + "");
            dao.delete(bean);
            long result = dao.insertOrReplace(bean);
            if (result != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取缓存Json串
     *
     * @param key
     * @return
     */
    public JSONObject getListFromCache(String key) {
        JSONObject result = null;
        if (!StringUtils.isBlank(key)) {
            CacheBeanDao dao = daoSession.getCacheBeanDao();
            List<CacheBean> list = dao.queryBuilder().where(CacheBeanDao.Properties.JsonKey.eq(key)).list();
            if (list != null && list.size() > 0) {
                try {
                    CommonUtils.logWrite(TAG, list.get(0).getJsonContent());
                    if (checkTime(list.get(0).getCacheTime())) {
                        result = new JSONObject(list.get(0).getJsonContent());
                    } else {
                        dao.delete(list.get(0));
                    }
                } catch (JSONException e) {
                    return null;
                }
            }
        }
        return result;
    }

    /**
     * 清除数据缓存
     */
    public void clearDataCache() {
        try {
            CacheBeanDao dao = daoSession.getCacheBeanDao();
            dao.deleteByKey("recommend_group_1");
            dao.deleteByKey("recommend_group_2");
            dao.deleteByKey("recommend_group_3");
            dao.deleteByKey("recommend_group_4");
            dao.deleteByKey("recommend_group_5");
            dao.deleteByKey("recommend_group_6");
            dao.deleteByKey("recommend_banner");
        } catch (Exception e) {

        }
    }

    /**
     * 清除数据缓存
     */
    public void clearCache() {
        try {
            CacheBeanDao dao = daoSession.getCacheBeanDao();
            dao.deleteAll();
        } catch (Exception e) {

        }
    }

    /**
     * 检测保存数据是否正确
     *
     * @param key
     * @param object
     * @return
     */
    private boolean checkData(String key, JSONObject object) {
        if (StringUtils.isBlank(key)) {
            return false;
        } else if (object == null) {
            return false;
        } else if (StringUtils.isBlank(object.toString())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * check时间范围
     *
     * @param time
     * @return
     */
    private boolean checkTime(String time) {
        try {
            long timeCache = TimeUtils.getCurrentTimeInLong() - Long.valueOf(time);
            if (timeCache / 1000 / 60 / 60 / 24 < 30) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}
