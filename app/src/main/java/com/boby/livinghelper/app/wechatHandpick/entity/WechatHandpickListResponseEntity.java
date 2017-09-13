package com.boby.livinghelper.app.wechathandpick.entity;

import com.boby.livinghelper.app.common.entity.MessageResponseEntity;
import com.boby.livinghelper.util.GsonUtils;

/**
 * 微信精选实体类
 *
 * @author zbobin.com
 */

public class WechatHandpickListResponseEntity extends MessageResponseEntity{
    private WechatHandpickListEntity result;
    public static WechatHandpickListResponseEntity getIntance(String json){
        return (WechatHandpickListResponseEntity) GsonUtils.fromJson(json, WechatHandpickListResponseEntity.class);
    }

    public WechatHandpickListEntity getResult() {
        return result;
    }
}
