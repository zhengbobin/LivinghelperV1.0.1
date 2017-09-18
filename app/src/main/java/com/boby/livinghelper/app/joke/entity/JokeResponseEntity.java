package com.boby.livinghelper.app.joke.entity;

import com.boby.livinghelper.app.common.entity.MessageResponseEntity;
import com.boby.livinghelper.util.GsonUtils;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/9/18.
 */

public class JokeResponseEntity extends MessageResponseEntity {

    private JokeResultEntity result;

    public static JokeResponseEntity getInstance(String json) {
        return (JokeResponseEntity) GsonUtils.fromJson(json, JokeResponseEntity.class);
    }

    public JokeResultEntity getResult() {
        return result;
    }
}
