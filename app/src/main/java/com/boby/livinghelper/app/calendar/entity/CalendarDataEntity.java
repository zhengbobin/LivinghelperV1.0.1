package com.boby.livinghelper.app.calendar.entity;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/9/18.
 */

public class CalendarDataEntity {

    private String holiday;         //假日
    private String avoid;           //忌
    private String animalsYear;    //属相
    private String desc;            //假日描述
    private String weekday;         //周几
    private String suit;            //宜
    private String lunarYear;       //纪年
    private String lunar;           //农历
    private String year_month;       //年份和月份
    private String date;            //具体日期

    public String getHoliday() {
        return holiday == null?"":holiday;
    }

    public String getAvoid() {
        return avoid == null?"":avoid;
    }

    public String getAnimalsYear() {
        return animalsYear == null?"":animalsYear;
    }

    public String getDesc() {
        return desc == null?"":desc;
    }

    public String getWeekday() {
        return weekday == null?"":weekday;
    }

    public String getSuit() {
        return suit == null?"":suit;
    }

    public String getLunarYear() {
        return lunarYear == null?"":lunarYear;
    }

    public String getLunar() {
        return lunar == null?"":lunar;
    }

    public String getYear_month() {
        return year_month == null?"":year_month;
    }

    public String getDate() {
        return date == null?"":date;
    }
}
