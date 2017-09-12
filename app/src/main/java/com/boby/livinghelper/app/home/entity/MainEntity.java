package com.boby.livinghelper.app.home.entity;

/**
 * 首页类别实体类
 *
 * @author zbobin
 */

public class MainEntity {

    private String id;          //类别标识
    private String main_name;   //类别名称
    private String main_img;    //类别icon
    private String main_url;    //类别URL

    public String getId() {
        return id == null?"":id;
    }

    public String getMain_name() {
        return main_name == null?"":main_name;
    }

    public String getMain_img() {
        return main_img == null?"":main_img;
    }

    public String getMain_url() {
        return main_url == null?"":main_url;
    }
}
