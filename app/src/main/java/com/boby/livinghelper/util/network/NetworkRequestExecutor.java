package com.boby.livinghelper.util.network;

import android.app.Activity;
import com.boby.livinghelper.widget.LoadDialog;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * 功能描述  网络请求执行者
 * 使用方式：
 * 1. new 本类对象
 * 2.适合时机执行execute 方法
 * 3.Activity.onDestroy 方法时调用本类的 onDestroyed 方法
 *
 * @author zbobin.com
 */
public class NetworkRequestExecutor {
    private Activity activity;
    private OnNetworkDataEnverListener onNetworkDataEnverListener;
    private LoadDialog loadDialog;

    /**
     * @param onNetworkDataEnverListener 调用者确保不能为 null
     */
    public NetworkRequestExecutor(Activity activity, OnNetworkDataEnverListener onNetworkDataEnverListener) {
        this.activity = activity;
        this.onNetworkDataEnverListener = onNetworkDataEnverListener;
    }


    /**
     * 开始请求网络
     *
     * @param networkRquestEntity
     */
    public void execute(final NetworkRquestEntity networkRquestEntity) {
        if (!networkRquestEntity.isVaildRequest())
            return;
        HttpUtils.post(activity, networkRquestEntity.url, networkRquestEntity.params, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                fireStartListener(networkRquestEntity);
                if (networkRquestEntity.isShowDilaog)
                    showDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                fireFailedListener(networkRquestEntity, statusCode, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                fireSuccessfullyListener(networkRquestEntity, statusCode, responseString);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                fireFinishedListener(networkRquestEntity);
                if (networkRquestEntity.isShowDilaog)
                    dismissCustomDialog();

            }
        });

    }


    private void showDialog() {
        if (activity != null && !activity.isFinishing()) {
            if (loadDialog == null)
                loadDialog = new LoadDialog(activity);

            if (!loadDialog.isShowing())
                loadDialog.show();
        }
    }


    private void dismissCustomDialog() {
        if (loadDialog != null && loadDialog.isShowing())
            loadDialog.dismiss();
    }


    /**
     * 请求开始的回调
     *
     * @param entity
     */
    private void fireStartListener(NetworkRquestEntity entity) {
        if (onNetworkDataEnverListener != null && onNetworkDataEnverListener
                instanceof OnNetworkDataEnverListener.OnNetworkDataEventExListener) {
            ((OnNetworkDataEnverListener.OnNetworkDataEventExListener)
                    onNetworkDataEnverListener).onRequestStart(entity);
        }
    }

    /**
     * 请求结束的回调
     *
     * @param entity
     */
    private void fireFinishedListener(NetworkRquestEntity entity) {
        if (onNetworkDataEnverListener != null && onNetworkDataEnverListener
                instanceof OnNetworkDataEnverListener.OnNetworkDataEventExListener) {
            ((OnNetworkDataEnverListener.OnNetworkDataEventExListener) onNetworkDataEnverListener)
                    .onRequestFinshed(entity);
        }
    }


    /**
     * 请求失败的回调
     *
     * @param netWrokRuquestEntity
     * @param statusCode
     * @param responseString
     */
    private void fireFailedListener(NetworkRquestEntity netWrokRuquestEntity,
                                    int statusCode, String responseString) {
        if (onNetworkDataEnverListener != null)
            onNetworkDataEnverListener.onRequestFailed(netWrokRuquestEntity, statusCode, responseString);
    }


    /**
     * 请求成功的回调
     *
     * @param netWorkReuqestEntity
     * @param statusCode
     * @param responseString
     */
    private void fireSuccessfullyListener(NetworkRquestEntity netWorkReuqestEntity,
                                          int statusCode, String responseString) {
        if (onNetworkDataEnverListener != null)
            onNetworkDataEnverListener.onRequestSuceeessfully(netWorkReuqestEntity, statusCode, responseString);
    }


    /**
     * 在Activity.onDestroy 中调用
     */
    public void onActivityDestroyed() {
        dismissCustomDialog();
        loadDialog = null;
        activity = null;
        onNetworkDataEnverListener = null;
    }


}
