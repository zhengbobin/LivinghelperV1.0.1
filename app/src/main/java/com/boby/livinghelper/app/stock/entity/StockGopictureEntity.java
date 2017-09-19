package com.boby.livinghelper.app.stock.entity;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/9/19.
 */

public class StockGopictureEntity {

    private String minurl;           //分时K线图
    private String dayurl;           //日K线图
    private String weekurl;          //周K线图
    private String monthurl;         //月K线图

    public String getMinurl() {
        return minurl == null?"":minurl;
    }

    public String getDayurl() {
        return dayurl == null?"":dayurl;
    }

    public String getWeekurl() {
        return weekurl == null?"":weekurl;
    }

    public String getMonthurl() {
        return monthurl == null?"":monthurl;
    }
}
