package com.example.common;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.cache.DiskLruImageCache;
import com.example.application.MyApplication;


/**
 * Created by Xuzj on 2015/1/15.
 * 图片信息缓存类
 */
public class BitmapCache implements com.android.volley.cache.ImageCache {
    private LruCache<String, Bitmap> mLruCache;
    private DiskLruImageCache diskLruImageCache;
    private static BitmapCache mInstance;

    public static BitmapCache getInstance() {
        if (mInstance == null) {
            mInstance = new BitmapCache();
        }
        return mInstance;
    }

    private BitmapCache() {
        initDiskCache();
        initLruCache();
    }

    /**
     * 获取文件的缓存路径
     *
     * @return
     */

    public String getDirectory() {

        String dir = CommonUtils.getExternalCachePath();
        return dir;
    }

    private void initDiskCache() {
//        diskLruImageCache = new DiskLruImageCache(CommonUtils.getExternalCachePath(), 20 * 1024 * 1024, Bitmap.CompressFormat.PNG, 70);
        diskLruImageCache = new DiskLruImageCache(MyApplication.getInstance().getCacheDir().getAbsolutePath() + "/images", 20 * 1024 * 1024, Bitmap.CompressFormat.PNG, 70);
    }

    private void initLruCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                    return bitmap.getByteCount() / 1024;
                } else {
                    return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
                }
            }
        };
    }

    @Override
    public Bitmap getBitmap(String key) {
        Bitmap bitmap = mLruCache.get(getKey(key));
        if (bitmap == null) {
            bitmap = diskLruImageCache.getBitmap(getKey(key));
            if (bitmap != null) {
                mLruCache.put(getKey(key), bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        mLruCache.put(getKey(key), bitmap);
        if (!diskLruImageCache.containsKey(getKey(key))) {
            diskLruImageCache.putBitmap(getKey(key), bitmap);
        }
    }

    @Override
    public void invalidateBitmap(String url) {
        mLruCache.remove(url);
    }

    @Override
    public void clear() {
        mLruCache.evictAll();
    }

    private String getKey(String url) {
        String[] strs = url.split("/");
        return strs[strs.length - 1];
    }


}
