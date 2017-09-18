package com.boby.livinghelper.app.joke.entity;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/9/18.
 */

public class JokeDataEntity {

    private String content;//内容
    private String updatetime;//更新时间

    public String getContent() {
        return content == null?"":content;
    }

    public String getUpdatetime() {
        return updatetime == null?"":updatetime;
    }
}
