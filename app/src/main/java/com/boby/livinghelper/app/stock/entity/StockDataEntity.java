package com.boby.livinghelper.app.stock.entity;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/9/19.
 */

public class StockDataEntity {

    private String gid;                      //股票编号
    private String increPer;                //涨跌百分比
    private String increase;                //涨跌额
    private String name;                    //股票名称
    private String todayStartPri;          //今日开盘价
    private String yestodEndPri;           //昨日收盘价
    private String nowPri;                  //当前价格
    private String todayMax;                //今日最高价
    private String todayMin;                //今日最低价
    private String competitivePri;         //竞买价
    private String reservePri;              //竞卖价
    private String traNumber;               //成交量
    private String traAmount;               //成交金额
    private String buyOne;                  //买一
    private String buyOnePri;               //买一报价
    private String buyTwo;                  //买二
    private String buyTwoPri;               //买二报价
    private String buyThree;                //买三
    private String buyThreePri;             //买三报价
    private String buyFour;                 //买四
    private String buyFourPri;              //买四报价
    private String buyFive;                 //买五
    private String buyFivePri;              //买五报价
    private String sellOne;                 //卖一
    private String sellOnePri;              //卖一报价
    private String sellTwo;                 //卖二
    private String sellTwoPri;              //卖二报价
    private String sellThree;               //卖三
    private String sellThreePri;            //卖三报价
    private String sellFour;                 //卖四
    private String sellFourPri;              //卖四报价
    private String sellFive;                 //卖五
    private String sellFivePri;              //卖五报价
    private String date;                     //日期
    private String time;                     //时间

    public String getGid() {
        return gid == null?"":gid;
    }

    public String getIncrePer() {
        return increPer == null?"":increPer;
    }

    public String getIncrease() {
        return increase == null?"":increase;
    }

    public String getName() {
        return name == null?"":name;
    }

    public String getTodayStartPri() {
        return todayStartPri == null?"":todayStartPri;
    }

    public String getYestodEndPri() {
        return yestodEndPri == null?"":yestodEndPri;
    }

    public String getNowPri() {
        return nowPri == null?"":nowPri;
    }

    public String getTodayMax() {
        return todayMax == null?"":todayMax;
    }

    public String getTodayMin() {
        return todayMin == null?"":todayMin;
    }

    public String getCompetitivePri() {
        return competitivePri == null?"":competitivePri;
    }

    public String getReservePri() {
        return reservePri == null?"":reservePri;
    }

    public String getTraNumber() {
        return traNumber == null?"":traNumber;
    }

    public String getTraAmount() {
        return traAmount == null?"":traAmount;
    }

    public String getBuyOne() {
        return buyOne == null?"":buyOne;
    }

    public String getBuyOnePri() {
        return buyOnePri == null?"":buyOnePri;
    }

    public String getBuyTwo() {
        return buyTwo == null?"":buyTwo;
    }

    public String getBuyTwoPri() {
        return buyTwoPri == null?"":buyTwoPri;
    }

    public String getBuyThree() {
        return buyThree == null?"":buyThree;
    }

    public String getBuyThreePri() {
        return buyThreePri == null?"":buyThreePri;
    }

    public String getBuyFour() {
        return buyFour == null?"":buyFour;
    }

    public String getBuyFourPri() {
        return buyFourPri == null?"":buyFourPri;
    }

    public String getBuyFive() {
        return buyFive == null?"":buyFive;
    }

    public String getBuyFivePri() {
        return buyFivePri == null?"":buyFivePri;
    }

    public String getSellOne() {
        return sellOne == null?"":sellOne;
    }

    public String getSellOnePri() {
        return sellOnePri == null?"":sellOnePri;
    }

    public String getSellTwo() {
        return sellTwo == null?"":sellTwo;
    }

    public String getSellTwoPri() {
        return sellTwoPri == null?"":sellTwoPri;
    }

    public String getSellThree() {
        return sellThree == null?"":sellThree;
    }

    public String getSellThreePri() {
        return sellThreePri == null?"":sellThreePri;
    }

    public String getSellFour() {
        return sellFour == null?"":sellFour;
    }

    public String getSellFourPri() {
        return sellFourPri == null?"":sellFourPri;
    }

    public String getSellFive() {
        return sellFive == null?"":sellFive;
    }

    public String getSellFivePri() {
        return sellFivePri == null?"":sellFivePri;
    }

    public String getDate() {
        return date == null?"":date;
    }

    public String getTime() {
        return time == null?"":time;
    }
}
