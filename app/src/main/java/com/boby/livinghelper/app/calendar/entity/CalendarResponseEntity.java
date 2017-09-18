package com.boby.livinghelper.app.calendar.entity;

import com.boby.livinghelper.app.common.entity.MessageResponseEntity;
import com.boby.livinghelper.util.GsonUtils;

/**
 * 万年历响应实体类
 *
 * @author zbobin.com
 * @date 2017/9/18.
 */

public class CalendarResponseEntity extends MessageResponseEntity {

    private CalendarResultEntity result;

    public static CalendarResponseEntity getInstance(String json) {
        return (CalendarResponseEntity) GsonUtils.fromJson(json, CalendarResponseEntity.class);
    }

    public CalendarResultEntity getResult() {
        return result;
    }
}
