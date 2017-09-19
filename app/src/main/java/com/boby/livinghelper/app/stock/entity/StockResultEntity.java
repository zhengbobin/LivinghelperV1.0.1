package com.boby.livinghelper.app.stock.entity;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/9/19.
 */

public class StockResultEntity {

    private StockDataEntity data;
    private StockDapandataEntity dapandata;
    private StockGopictureEntity gopicture;

    public StockDataEntity getData() {
        return data;
    }

    public StockDapandataEntity getDapandata() {
        return dapandata;
    }

    public StockGopictureEntity getGopicture() {
        return gopicture;
    }
}
