package com.boby.livinghelper.util.network;

import android.content.Context;
import android.os.Handler;

import com.boby.livinghelper.R;
import com.boby.livinghelper.util.ToastUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 网络访问工具类
 *
 * @author zbobin.com
 */
public class HttpUtils {

    private static AsyncHttpClient client = new AsyncHttpClient();
    static {
        client.setTimeout(15000); // 设置链接超时，如果不设置，默认为15s
    }

    /**
     * 不带参数的get请求,获取一个String对象
     * @param context
     * @param url
     * @param asyncHttpResponseHandler
     */
    public static void get(Context context,String url,AsyncHttpResponseHandler asyncHttpResponseHandler){
        if (!CheckNetworkConnection.isNetworkAvailable(context)) {
            ToastUtil.showMessage(context, R.string.network_is_disabled);
            return;
        }
        client.get(url, asyncHttpResponseHandler);
    }
    /**
     * 带参数的get请求，获取一个String对象
     * @param context
     * @param url
     * @param params
     * @param asyncHttpResponseHandler
     */
    public static void get(Context context,String url,RequestParams params,AsyncHttpResponseHandler asyncHttpResponseHandler){
        if (!CheckNetworkConnection.isNetworkAvailable(context)) {
            ToastUtil.showMessage(context, R.string.network_is_disabled);
            return;
        }
        client.get(url, params, asyncHttpResponseHandler);
    }

    /**
     * 不带参数的get请求,获取一个json对象
     * @param context
     * @param url
     * @param jsonHttpResponseHandler
     */
    public static void get(Context context,String url,JsonHttpResponseHandler jsonHttpResponseHandler){
        if (!CheckNetworkConnection.isNetworkAvailable(context)) {
            ToastUtil.showMessage(context, R.string.network_is_disabled);
            return;
        }
        client.get(url, jsonHttpResponseHandler);
    }
    /**
     * 带参数的get请求，获取一个json对象
     * @param context
     * @param url
     * @param params
     * @param jsonHttpResponseHandler
     */
    public static void get(Context context,String url,RequestParams params,JsonHttpResponseHandler jsonHttpResponseHandler){
        if (!CheckNetworkConnection.isNetworkAvailable(context)) {
            ToastUtil.showMessage(context, R.string.network_is_disabled);
            return;
        }
        client.get(url, params, jsonHttpResponseHandler);
    }

    /**
     * 不带参数的post请求，获取一个String对象
     * @param context
     * @param params
     * @param url
     * @param asyncHttpResponseHandler
     */
    public static void post(Context context, RequestParams params, String url, AsyncHttpResponseHandler asyncHttpResponseHandler){
        if (!CheckNetworkConnection.isNetworkAvailable(context)) {
            ToastUtil.showMessage(context, R.string.network_is_disabled);
            return;
        }
        client.post(url, asyncHttpResponseHandler);
    }
    /**
     * 带参数的post请求，获取一个String对象
     * @param context
     * @param url
     * @param params
     * @param asyncHttpResponseHandler
     */
    public static void post(Context context, String url, RequestParams params,
                            AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!CheckNetworkConnection.isNetworkAvailable(context)) {
            ToastUtil.showMessage(context, R.string.network_is_disabled);
            return;
        }
        client.post(url, params, asyncHttpResponseHandler);
    }
    /**
     * 有handler回调，带参数的post请求，获取一个String对象
     * @param handler
     * @param context
     * @param url
     * @param params
     * @param asyncHttpResponseHandler
     */
    public static void post(Handler handler,Context context, String url, RequestParams params,
                            AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!CheckNetworkConnection.isNetworkAvailable(context)) {
            handler.sendEmptyMessage(-1);//网络不可用 返回-1
            ToastUtil.showMessage(context, R.string.network_is_disabled);
            return;
        }
        client.post(url, params, asyncHttpResponseHandler);
    }

    /**
     * 不带参数的post请求，获取一个json对象
     * @param context
     * @param url
     * @param jsonHttpResponseHandler
     */
    public static void post(Context context,String url,JsonHttpResponseHandler jsonHttpResponseHandler){
        if (!CheckNetworkConnection.isNetworkAvailable(context)) {
            ToastUtil.showMessage(context, R.string.network_is_disabled);
            return;
        }
        client.post(url, jsonHttpResponseHandler);
    }
    /**
     * 带参数的post请求，获取一个json对象
     * @param context
     * @param url
     * @param params
     * @param jsonHttpResponseHandler
     */
    public static void post(Context context, String url, RequestParams params,
                            JsonHttpResponseHandler jsonHttpResponseHandler) {
        if (!CheckNetworkConnection.isNetworkAvailable(context)) {
            ToastUtil.showMessage(context, R.string.network_is_disabled);
            return;
        }
        client.post(url, params, jsonHttpResponseHandler);
    }
}
