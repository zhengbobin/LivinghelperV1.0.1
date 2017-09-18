package com.boby.livinghelper.util;

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
}
