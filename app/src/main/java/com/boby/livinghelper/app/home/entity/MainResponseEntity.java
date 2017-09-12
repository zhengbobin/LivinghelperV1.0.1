package com.boby.livinghelper.app.home.entity;

import com.boby.livinghelper.app.common.entity.MessageResponseEntity;
import com.boby.livinghelper.util.GsonUtils;
import java.util.ArrayList;

/**
 * 首页类别响应实体类
 *
 * @author zbobin
 */

public class MainResponseEntity extends MessageResponseEntity{

    private ArrayList<MainEntity> main;

    public static MainResponseEntity getInstance(String json) {
        return (MainResponseEntity) GsonUtils.fromJson(json, MainResponseEntity.class);
    }

    public ArrayList<MainEntity> getMain() {
        return main;
    }
}
