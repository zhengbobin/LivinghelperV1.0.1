package com.boby.livinghelper.app.common.entity;

import java.io.Serializable;

/**
 * 给 CommonWebViewActivity 传输数据的entity
 *
 * @author zbobin.com
 * @version V1.0.0
 * @date 2017/2/15
 */

public class WebIntentEntity implements Serializable {

    // 跳转地址 或者是html代码
    private String url;
    private String title;
    private boolean isShowShare;

    public WebIntentEntity(String url, String title, boolean isShowShare) {
        this.url = url;
        this.title = title;
        this.isShowShare = isShowShare;
    }

    public WebIntentEntity(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public WebIntentEntity(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public boolean isShowShare() {
        return isShowShare;
    }
}
