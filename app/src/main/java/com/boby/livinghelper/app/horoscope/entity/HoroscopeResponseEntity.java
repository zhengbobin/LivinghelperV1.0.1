package com.boby.livinghelper.app.horoscope.entity;

import com.boby.livinghelper.app.common.entity.MessageResponseEntity;
import com.boby.livinghelper.util.GsonUtils;

/**
 * 星座运势响应实体类
 *
 * @author zbobin.com
 * @date 2017/9/19.
 */

public class HoroscopeResponseEntity extends MessageResponseEntity{

    /*今日或明日运势格式*/
    private String name;          //星座名称
    private String datetime;     //日期
    private String date;          //20170919
    private String all;           //综合指数
    private String color;         //幸运色
    private String health;        //健康指数
    private String love;          //爱情指数
    private String money;         //财运指数
    private String number;        //幸运数字
    private String QFriend;       //速配星座
    private String summary;       //今日概述
    private String work;           //工作指数

    public static HoroscopeResponseEntity getInstance(String json) {
        return (HoroscopeResponseEntity) GsonUtils.fromJson(json, HoroscopeResponseEntity.class);
    }

    public String getName() {
        return name == null?"":name;
    }

    public String getDatetime() {
        return datetime == null?"":datetime;
    }

    public String getDate() {
        return date == null?"":date;
    }

    public String getAll() {
        return all == null?"":all;
    }

    public String getColor() {
        return color == null?"":color;
    }

    public String getHealth() {
        return health == null?"":health;
    }

    public String getLove() {
        return love == null?"":love;
    }

    public String getMoney() {
        return money == null?"":money;
    }

    public String getNumber() {
        return number == null?"":number;
    }

    public String getQFriend() {
        return QFriend == null?"":QFriend;
    }

    public String getSummary() {
        return summary == null?"":summary;
    }

    public String getWork() {
        return work == null?"":work;
    }
}
