package com.boby.livinghelper.util.network;

import com.boby.livinghelper.util.StringUtils;
import com.loopj.android.http.RequestParams;

/**
 * 功能描述  网络请求实体
 *
 * @author zbobin.com
 */
public class NetworkRquestEntity {
    public String url;
    public RequestParams params;
    public boolean isShowDilaog;
    private int requestIdentifier; // 用来标识 一个界面多个网络请求的访问

    /**
     * 默认会显示加载对话框
     *
     * @param url    不能为null，必须是完整的
     * @param params 参数 不能为null
     */
    public NetworkRquestEntity(String url, RequestParams params) {
        this(url, params, true);
    }

    /**
     * 默认会显示加载对话框
     *
     * @param url          不能为null，必须是完整的
     * @param params       参数 不能为null
     * @param isShowDilaog 是否显示对话框
     */
    public NetworkRquestEntity(String url, RequestParams params, boolean isShowDilaog) {
        this.url = url;
        this.params = params;
        this.isShowDilaog = isShowDilaog;
    }

    public void setRequestIdentifier(int requestIdentifier) {
        this.requestIdentifier = requestIdentifier;
    }


    public int getRequestIdentifier() {
        return requestIdentifier;
    }

    public RequestParams getParams() {
        return params;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIsShowDilaog(boolean isShowDilaog) {
        this.isShowDilaog = isShowDilaog;
    }

    public boolean isVaildRequest() {
        if (StringUtils.isEmpty(url))
            return false;
        if (params == null)
            return false;
        return true;
    }
}
