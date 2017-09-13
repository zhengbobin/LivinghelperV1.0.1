package com.boby.livinghelper.app.wechathandpick.entity;

import java.util.ArrayList;

/**
 * 微信精选实体类
 *
 * @author zbobin.com
 */

public class WechatHandpickListEntity {

    private ArrayList<WechatHandpickEntity> list;
    private int totalPage;    //388
    private int ps;    //20
    private int pno;    //1

    public ArrayList<WechatHandpickEntity> getList() {
        return list;
    }

    public void setList(ArrayList<WechatHandpickEntity> list) {
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getPno() {
        return pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }
}
