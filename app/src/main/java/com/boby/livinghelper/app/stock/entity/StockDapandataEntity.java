package com.boby.livinghelper.app.stock.entity;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/9/19.
 */

public class StockDapandataEntity {

    private String  dot;                     //8.000
    private String  name;                    //某银行
    private String  nowPic;                  //-0.060
    private String  rate;                    //-0.74
    private String  traAmount;              //38804
    private String  traNumber;              //485335

    public String getDot() {
        return dot == null?"":dot;
    }

    public String getName() {
        return name == null?"":name;
    }

    public String getNowPic() {
        return nowPic == null?"":nowPic;
    }

    public String getRate() {
        return rate == null?"":rate;
    }

    public String getTraAmount() {
        return traAmount == null?"":traAmount;
    }

    public String getTraNumber() {
        return traNumber == null?"":traNumber;
    }
}
