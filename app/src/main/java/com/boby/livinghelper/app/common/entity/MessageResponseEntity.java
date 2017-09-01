package com.boby.livinghelper.app.common.entity;


import com.boby.livinghelper.util.GsonUtils;

/**
 * 信息响应体
 *
 * @author android_bing
 * @date 2014/11/12.
 */
public class MessageResponseEntity {
    private String status; // 状态码 0成功，401系统错误
    private String msg; // 返回的文本信息

    //***
    private String error_code;
    private String reason;

    public static MessageResponseEntity getInstance(String json) {
        return (MessageResponseEntity) GsonUtils.fromJson(json, MessageResponseEntity.class);
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getError_code() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }
}
