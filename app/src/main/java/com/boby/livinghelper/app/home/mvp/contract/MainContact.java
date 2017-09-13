package com.boby.livinghelper.app.home.mvp.contract;

import com.boby.livinghelper.app.home.entity.MainResponseEntity;
import com.boby.livinghelper.base.BaseModel;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.base.BaseView;
import rx.Observable;

/**
 * Main 契约类
 *
 * @author zbobin.com
 * @date 2017/9/13.
 */

public interface MainContact {

    interface Model extends BaseModel {
        Observable<MainResponseEntity> getMainEntity();
    }

    interface View extends BaseView {
        void showDialog();
        void onSuccess(MainResponseEntity entity);
        void onFail(String s);
        void hideDialog();
    }

    abstract class Precenter extends BasePresenter<Model, View> {
        public abstract void getMainEntity();
    }

}
