package com.boby.livinghelper.app.stock.entity;

import com.boby.livinghelper.app.common.entity.MessageResponseEntity;
import com.boby.livinghelper.util.GsonUtils;
import java.util.ArrayList;

/**
 * 股票数据响应实体类
 *
 * @author zbobin.com
 * @date 2017/9/19.
 */

public class StockResponseEntity extends MessageResponseEntity {

    private ArrayList<StockResultEntity> result;

    public static StockResponseEntity getIntance(String json){
        return (StockResponseEntity) GsonUtils.fromJson(json, StockResponseEntity.class);
    }

    public ArrayList<StockResultEntity> getResult() {
        return result;
    }

}
