package com.example.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理共同类
 * Created by zhangxinhao on 2015/1/22.
 */
public class TimeUtils {
    /**
     * 默认的时间样式
     */
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DEFAULT2_DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");


    /**
     * 将时间戳转化成制定的格式
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }


    /**
     * 获取制定格式的当前时间
     *
     * @param dateFormat 时间格式
     * @return 带格式的时间字符串
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    /**
     * 获取当月第一天
     *
     * @param m 0当月 -1上月 1下月 类推
     * @return 带格式的时间字符串 yyyy-MM-dd
     */
    public static String getFirstDayOfMonth(int m) {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, m);
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = DEFAULT_DATE_FORMAT.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取当月最后一天
     *
     * @param m 0当月 -1上月 1下月 类推
     * @return 带格式的时间字符串 yyyy-MM-dd
     */
    public static String getDefaultDay(int m) {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, m);
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        str = DEFAULT_DATE_FORMAT.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取当天的指定的时间
     *
     * @param hour   小时24小时制
     * @param minute 分钟
     * @param second 秒
     * @return
     */
    public static long getTimeOfDay(int hour, int minute, int second) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.HOUR_OF_DAY, hour);
        lastDate.set(Calendar.MINUTE, minute);
        lastDate.set(Calendar.SECOND, second);
        return lastDate.getTimeInMillis();
    }

    /**
     * 获取给定时间戳之后xx天的时间戳
     *
     * @param time 给定的时间戳
     * @param day  之后xx天
     * @return
     */
    public static long getTimeAfterDays(long time, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取两个时间戳之间的间隔天数
     *
     * @param time1 开始时间，时间戳
     * @param time2 结束时间，时间戳
     * @return
     */
    public static int compareTimeForDay(long time1, long time2) {
        long time = Math.abs(time1 - time2);
        return (int) (time / 1000 / 60 / 60 / 24);
    }

    /**
     * 获取某个时间是今天、明天、或者其他
     *
     * @param time
     * @return 0:今天  1:明天  2:其他
     */
    public static int compareTimeForDay(long time) {
        Date date = new Date();
        date.setHours(24);
        if (time - date.getTime() < 0) {
            return 0;
        } else if (time - date.getTime() - 24 * 60 * 60 * 1000 < 0) {
            return 1;
        } else {
            return 2;
        }
    }
}
