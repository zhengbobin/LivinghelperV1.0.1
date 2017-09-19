package com.boby.livinghelper.app.movie.entity;

import com.boby.livinghelper.app.common.entity.MessageResponseEntity;
import com.boby.livinghelper.util.GsonUtils;

/**
 * 在线电影票响应实体类
 *
 * @author zbobin.com
 * @date 2017/9/19.
 */

public class MovieResponseEntity extends MessageResponseEntity{

    private MovieEntity result;

    public static MovieResponseEntity getIntance(String json){
        return (MovieResponseEntity) GsonUtils.fromJson(json, MovieResponseEntity.class);
    }

    public MovieEntity getResult() {
        return result;
    }
}
