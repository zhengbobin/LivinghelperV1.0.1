package com.boby.livinghelper.app.news.entity;

import java.util.ArrayList;

/**
 * 功能描述
 *
 * @author android_bing
 * @version V1.0.0
 * @date 2017/2/14
 */

public class NewsResultEntity {

    private String stat;
    private ArrayList<NewsEntity> data;

    public String getStat() {
        return stat;
    }

    public ArrayList<NewsEntity> getData() {
        return data;
    }
}
