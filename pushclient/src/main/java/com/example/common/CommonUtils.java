package com.example.common;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.application.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 共同方法
 *
 * @author zhangxh
 */
public class CommonUtils {
    /**
     * 打印log信息 tag为当前类Class、在LogCat中绿色显示
     *
     * @param message
     */
    public static void logWrite(String tag, String message) {
        if (Constants.LOGPRINTLN) {
            Log.i(tag, message);
        }
    }

    /**
     * 获取ActionBar 高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) MyApplication
                .getInstance().getSystemService(
                        MyApplication.getInstance().CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 获取sd卡的缓存目录
     *
     * @return
     */
    public static File getExternalCacheDir() {
        return new File(Environment.getExternalStorageDirectory().getPath()
                + Constants.CACHEPATH);
    }

    /**
     * 获取缓存路径
     *
     * @return
     */
    public static String getExternalCachePath() {
        return Environment.getExternalStorageDirectory().getPath()
                + Constants.CACHEPATH;
    }

    /**
     * 返回当前应用的版本名 如果出现错误则返回beat
     */
    public static String getAppVersionName() {
        String versionName = "beat";
        try {
            PackageManager pm = MyApplication.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(MyApplication.getInstance()
                    .getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            return "beat";
        }
        return versionName;
    }

    /**
     * 返回当前应用的版本code 如果出现错误返回0
     */
    public static int getAppVersionCode() {
        int versionCode = 0;
        try {
            PackageManager pm = MyApplication.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(MyApplication.getInstance()
                    .getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            return 0;
        }
        return versionCode;
    }

    /**
     * 返回当前应用的包路径 如果出现错误返回null
     */
    public static String getAppPackageName() {
        String packageName = null;
        try {
            packageName = MyApplication.getInstance().getPackageName();
        } catch (Exception e) {
        }
        return packageName;
    }

    /**
     * 获取本手机ip如果获取错误则返回1.1.1.1
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            WifiManager wifiManager = (WifiManager) MyApplication.getInstance()
                    .getSystemService(Context.WIFI_SERVICE);
            if (wifiManager.isWifiEnabled()) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int i = wifiInfo.getIpAddress();
                return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "."
                        + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
            } else {

                for (Enumeration<NetworkInterface> en = NetworkInterface
                        .getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress();

                        }
                    }
                }
            }
        } catch (SocketException ex) {
            return Constants.LOCALIPADDRESS;
        }
        return Constants.LOCALIPADDRESS;
    }

    /**
     * dip转换为px
     *
     * @param dip
     * @return
     */
    public static int dipToPixels(int dip) {

        Resources r = MyApplication.getInstance().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int spTopx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取状态栏高度
     *
     * @param cont
     * @return
     */
    public static int getTop(Activity cont) {
        Rect frame = new Rect();
        cont.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 获取屏幕的宽 尺寸像素px
     *
     * @param cont
     * @return int
     */
    public static int getScreenWidthPX(Activity cont) {
        if (cont != null) {
            DisplayMetrics dm = new DisplayMetrics();
            // 取得窗口属性
            cont.getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 窗口的宽度
            return dm.widthPixels;
        } else {
            return 0;
        }
    }

    /**
     * 获取屏幕的宽度 dp
     *
     * @param cont
     * @return
     */
    public static int getScreenWidthDP(Activity cont) {
        if (cont != null) {
            DisplayMetrics dm = new DisplayMetrics();
            // 取得窗口属性
            cont.getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 窗口的宽度
            return (int) (dm.widthPixels / dm.density);
        } else {
            return 0;
        }
    }

    /**
     * 获取屏幕的高 尺寸像素px
     *
     * @param cont
     * @return int
     */
    public static int getScreenHeight(Activity cont) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        cont.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的高度
        return dm.heightPixels;
    }

    /**
     * Context
     *
     * @param context
     * @return
     */
    public static int get_Height_bycontext(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        (wm.getDefaultDisplay()).getMetrics(dm);

        int height = dm.heightPixels;
        return height;
    }

    /**
     * 获取屏幕的高 尺寸像素px
     *
     * @param cont
     * @return int
     */
    public static int getScreenHeightWithDP(Activity cont) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        cont.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
        return (int) (dm.heightPixels / dm.density);
    }

    /**
     * 获取手机的分辨率
     *
     * @return 480*800
     */
    public static String getScreenSize() {
        return SharedPreferencesUtils.getInstance().getScreenSize();

    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 获取资源文字
     *
     * @param id
     * @return
     */
    public static String getResString(int id) {
        return MyApplication.getInstance().getString(id);
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取设备id
     *
     * @return
     */
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取手机sim卡标示
     *
     * @return
     */
    public static String getSubscriberId() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 获取 客户端来源标识
     *
     * @return
     */
    public static String getSource() {
        return "渠道id";
    }

    /**
     * 获取语言
     *
     * @return 语言
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取运营商信息
     *
     * @return 运营商信息
     */
    public static String getOperatorName() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimOperatorName();
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 缓存
     */
    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static long getAutoFileOrFilesRealSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        } finally {
            file = null;
        }
        return blockSize;
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws java.io.FileNotFoundException
     * @throws Exception
     */
    private static long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
                fis.close();
                fis = null;
            } else {
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        if (flist != null) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSizes(flist[i]);
                } else {
                    size = size + getFileSize(flist[i]);
                }
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 友盟获取设备信息测试用
     *
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            WifiManager wifi = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (StringUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (StringUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(
                        context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param phoneNumber
     * @return boolean 返回类型
     * @Title: isPhoneNumberValid
     * @Description: 验证号码 手机号 固话均可
     * @date 2014年12月4日17:13:38
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
                + "(^0[3-9] {1}d{2}-?d{7,8}$)|"
                + "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
                + "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhoneNumber(String str) {
        // Pattern p1 = null, p2 = null;
        // Matcher m = null;
        // boolean b = false;
        // p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        // p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
        // if (str.length() > 9) {
        // str = str.substring(0, 3) + "-"
        // + str.substring(3 + 1, str.length());
        // m = p1.matcher(str);
        // b = m.matches();
        // } else {
        // m = p2.matcher(str);
        // b = m.matches();
        // }
        boolean isTrue = false;
        if (str.length() >= 8 && str.length() <= 16) {
            isTrue = true;
        }
        return isTrue;
    }

    /**
     * 屏蔽EditText的换行事件
     *
     * @param edt
     */
    public static void forbidEnterEvent(EditText edt) {
        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 根据文件名从assest中取出文件
     *
     * @param context
     * @param fileName 文件名
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context
                    .getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前手机系统的版本号
     *
     * @return
     */
    public static int getSystemVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 保存字符串为txt文件
     *
     * @param str
     */
    public static void saveFile(String str, String fileName) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            filePath = Environment.getExternalStorageDirectory().toString()
                    + File.separator + fileName;
        } else
            filePath = Environment.getDownloadCacheDirectory().toString()
                    + File.separator + fileName;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动闹钟
     *
     * @param context         上下文
     * @param action          广播aciton
     * @param triggerAtMillis 启动时间（绝对时间，毫秒）
     */
    public static void startAlarm(Context context, String action, long triggerAtMillis) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(action);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //手机休眠也可以启动闹钟，传递的时间是绝对时间，毫秒
        am.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
    }

    /**
     * 根据url获取跳转类型和参数
     *
     * @param url
     * @return Map  key分别是
     * type跳转类型 search：跳转搜索结果页面|goods：跳转商品详情页面|url直接跳转url
     * keyword搜索词
     * bid品牌id
     * categoryId大分类id
     * categorySubId小分类id
     * id商品id
     */
    public static Map<String, String> getJumpParms(String url) {
        CommonUtils.logWrite("supuy", url);
        Map<String, String> map = new HashMap<String, String>();
        // 四种类型 搜索 keyword、筛选 分类大id分类小id品牌id、商品详情 商品id、直接跳转url
        if (!StringUtils.isBlank(url)) {
            if (url.contains("Search?")) {
                // 搜索类型
                int start = url.indexOf("keyword=") + 8;
                int end = url.indexOf("&");
                String keyword = url.substring(start, end);
                // 品牌id
                String bid = "";
                if (url.contains("bid=")) {
                    int bidStart = url.indexOf("bid=");
                    int binEnd = url.indexOf("&", bidStart);

                    if (binEnd == -1) {
                        bid = url.substring(bidStart + 4);
                    } else {
                        bid = url.substring(bidStart + 4, binEnd);
                    }
                }
                map.put("type", "search");
                try {
                    map.put("keyword", URLDecoder.decode(keyword, "utf-8"));
                } catch (Exception e) {

                }
                map.put("bid", bid);
            } else if (url.contains("products")) {
                int start = url.indexOf("products/") + 9;
                int end = url.indexOf(".html");
                String str = url.substring(start, end);

                String[] ids = str.split("-");
                if (ids.length > 1) {
                    // 搜索
                    map.put("type", "search");
                    map.put("keyword", "");
                    map.put("categoryId", ids[0]);
                    map.put("categorySubId", ids[1]);
                    map.put("bid", ids[2]);
                } else {
                    // 商品
                    map.put("type", "goods");
                    map.put("id", ids[0]);
                }
            } else {
                map.put("type", "url");
            }
        }
        return map;
    }
}
