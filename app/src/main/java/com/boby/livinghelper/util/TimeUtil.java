package com.boby.livinghelper.util;

import java.util.Calendar;

/**
 * 时间处理工具
 *
 * @author zbobin.com
 * @date 2017/9/18.
 */

public class TimeUtil {

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳（除以1000，获取秒级的时间戳）
     */
    public static long getCurrentTimestamp() {
        LogUtil.e("timestamp", System.currentTimeMillis()/1000 + "");
        return System.currentTimeMillis()/1000;
    }

    /**
     * 获取当前年月日（格式：YYYY-MM-DD,如月份和日期小于10,则取个位,如:2017-1-1）
     * @return YYYY-MM-DD
     */
    public static String getYearMonthDay() {
        String dateString;
        try {
            Calendar ca = Calendar.getInstance();
            int year = ca.get(Calendar.YEAR); //获取年份
            int month = ca.get(Calendar.MONTH) + 1; //获取月份
            int day = ca.get(Calendar.DATE); //获取日

            dateString = year + "-" + month + "-" + day;
            LogUtil.e("getYearMonthDay", dateString);
        } catch (Exception ex) {
            return null;
        }

        return dateString;
    }
}
