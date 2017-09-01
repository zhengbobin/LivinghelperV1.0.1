package com.boby.livinghelper.app.news.entity;

import com.boby.livinghelper.app.common.entity.MessageResponseEntity;
import com.boby.livinghelper.util.GsonUtils;

/**
 * 功能描述
 *
 * @author zbobin.com
 * @version V1.0.0
 * @date 2017/2/14
 */

public class NewsResponseEntity extends MessageResponseEntity {

    private NewsResultEntity result;

    public static NewsResponseEntity getIntance(String json){
        return (NewsResponseEntity) GsonUtils.fromJson(json, NewsResponseEntity.class);
    }

    public NewsResultEntity getResult() {
        return result;
    }
}
