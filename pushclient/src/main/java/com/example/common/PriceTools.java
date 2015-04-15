package com.example.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 价格数值计算类
 *
 * @author Zxh
 */
public class PriceTools {

    /**
     * 将价格格式化为0.00型 的String，例："78.5"--->"¥78.50"
     *
     * @return String
     */
    public static String formatStr(String str) {
        if (StringUtils.isBlank(str))
            return "--";
        else {
            DecimalFormat df = new DecimalFormat();
            String pattern = "¥0.00";
            df.applyPattern(pattern);
            str = df.format(Double.parseDouble(str));
        }
        return str;
    }

    /**
     * 将价格格式化为0.00型 的String，例："78.5"--->"78.50"
     *
     * @return String
     */
    public static String formatPriceStr(String str) {
        if (StringUtils.isBlank(str))
            return "--";
        else {
            DecimalFormat df = new DecimalFormat();
            String pattern = "0.00";
            df.applyPattern(pattern);
            str = df.format(Double.parseDouble(str));
        }
        return str;
    }

    public static String formatDouble(double str) {
        return formatStr(String.valueOf(str));
    }

    /**
     * 检查用户输入的价格区间，是否符合规范
     *
     * @return
     */
    public static boolean checkPriceInterval(String fromPrice, String toPrice) {
        if (fromPrice.equals(".") && toPrice.equals(".")||fromPrice.equals("0") && toPrice.equals("0")) {
            return false;
        }
        if (StringUtils.isBlank(fromPrice) || StringUtils.isBlank(toPrice)
                || fromPrice.equals(".") || toPrice.equals(".")) {
            return true;
        } else if (Double.parseDouble(fromPrice) <= Double.parseDouble(toPrice)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 价格字符串转为int型，例如："78.5"--->7850
     *
     * @param str
     * @return
     */
    public static double stringToInt(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0d;
        }
    }

    /**
     * 比较两个价格字符串的数值大小
     *
     * @param str1
     * @param str2
     * @return 1：str1的数值 > str2的数值; -1:str1的数值 < str2的数值; 0:str1的数值 = str2的数值
     * -2:不符价格格式
     */
    public static int compare(String str1, String str2) {
        str1 = formatStr(str1);
        str2 = formatStr(str2);
        int pointIndex_1 = str1.indexOf('.');
        int pointIndex_2 = str2.indexOf('.');
        long pointBefore_1 = Long.parseLong(str1.substring(0, pointIndex_1));
        long pointBefore_2 = Long.parseLong(str2.substring(0, pointIndex_2));

        if (pointBefore_1 > pointBefore_2) {
            return 1;
        } else if (pointBefore_1 < pointBefore_2) {
            return -1;
        } else {
            long pointAfter_1 = Long.parseLong(str1.substring(pointIndex_1 + 1,
                    str1.length()));
            long pointAfter_2 = Long.parseLong(str2.substring(pointIndex_2 + 1,
                    str2.length()));
            if (pointAfter_1 > pointAfter_2) {
                return 1;
            } else if (pointAfter_1 < pointAfter_2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * 金钱的加法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(d1));
        BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 金钱的减法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double subtract(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(d1));
        BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.subtract(b2).doubleValue();
    }
}
