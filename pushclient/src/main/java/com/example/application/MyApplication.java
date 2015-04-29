package com.example.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.db.SqliteHelper;
import com.example.greenDao.DaoMaster;
import com.example.greenDao.DaoSession;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


/**
 * 全局Application
 *
 * @author zhangxh
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private boolean notshowagain_bao = false;

    public boolean isNotshowagain_sea() {
        return notshowagain_sea;
    }

    public void setNotshowagain_sea(boolean notshowagain_sea) {
        this.notshowagain_sea = notshowagain_sea;
    }

    public boolean isNotshowagain_bao() {
        return notshowagain_bao;
    }

    public void setNotshowagain_bao(boolean notshowagain_bao) {
        this.notshowagain_bao = notshowagain_bao;
    }

    private  boolean notshowagain_sea = false;

    /**
     * google 的volley全局队列
     */
    private RequestQueue mRequestQueue;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        getRequestQueue();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        Fresco.initialize(this);
        initImageLoader();
    }
    // 初始化ImageLoader
    public  void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
    /**
     * @return Volley队列
     */
    public RequestQueue getRequestQueue() {
        // volley队列在第一次请求的时候创建
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    // **********greenDao相关****************start*******
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    /**
     * 取得DaoMaster
     *
     * @return
     */
    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(instance,
                    SqliteHelper.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @return
     */
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    // **********greenDao相关****************end*******


}
