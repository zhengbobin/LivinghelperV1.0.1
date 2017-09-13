package com.boby.livinghelper.app.home.mvp.presenter;

import android.widget.Toast;

import com.boby.livinghelper.R;
import com.boby.livinghelper.app.home.entity.MainResponseEntity;
import com.boby.livinghelper.app.home.mvp.contract.MainContact;
import com.boby.livinghelper.app.home.mvp.model.MainModel;
import com.boby.livinghelper.base.BaseApplication;
import com.boby.livinghelper.util.network.NetworkUtil;

import rx.Subscriber;
import rx.Subscription;

/**
 * Main Presenter层
 *
 * @author zbobin.com
 * @date 2017/9/13.
 */

public class MainPresenter extends MainContact.Precenter{

    public MainPresenter(MainContact.View view) {
        mView = view;
        mModel = new MainModel();
    }

    @Override
    public void getMainEntity() {

        //无网络提示
        if (!NetworkUtil.isConnected(BaseApplication.getContext())) {
            Toast.makeText(BaseApplication.getContext(), R.string.network_interrupted, Toast.LENGTH_SHORT).show();
            return;
        }

        Subscription subscription = mModel.getMainEntity()
                .subscribe(new Subscriber<MainResponseEntity>() {
                    @Override
                    public void onStart() {
                        mView.showDialog();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFail(e.getMessage());
                        onCompleted();
                    }

                    @Override
                    public void onNext(MainResponseEntity entity) {
                        mView.onSuccess(entity);
                    }
                });
        addSubscribe(subscription);
    }
}
