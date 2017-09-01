package com.boby.livinghelper.util.network;

/**
 * 功能描述  网络数据获取事件的回调接口
 *
 * @author zbobin.com
 */
public interface OnNetworkDataEnverListener {
    /**
     * 请求失败的回调
     *
     * @param networkRquestEntity 代表具体某个网络请求，同一个界面多个请求可以使用 requestIdentifier 来标识
     * @param statuesCode         标准网络响应码
     * @param responseString      响应字符串
     */
    void onRequestFailed(NetworkRquestEntity networkRquestEntity, int statuesCode
            , String responseString);

    /**
     * 请求成功的回调,此处仅代表网络访问成功,得到服务器响应
     *
     * @param networkRquestEntity 代表具体某个网络请求，同一个界面多个请求可以使用 requestIdentifier 来标识
     * @param statusCode          标准网络响应码  200 成功
     * @param responseString      响应字符串,业务层的状态码在这个字段里
     */
    void onRequestSuceeessfully(NetworkRquestEntity networkRquestEntity, int statusCode
            , String responseString);

    /**
     * 网络响应扩展接口，提供开始事件和结束事件 考虑有些业务逻辑可能需要
     */
    interface OnNetworkDataEventExListener extends OnNetworkDataEnverListener {
        /**
         * 网络请求开始
         *
         * @param entity
         */
        void onRequestStart(NetworkRquestEntity entity);

        /**
         * 网络请求结束
         *
         * @param entity
         */
        void onRequestFinshed(NetworkRquestEntity entity);
    }
}
