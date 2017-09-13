package com.boby.livinghelper.app.home.mvp.model;

import com.boby.livinghelper.api.ApiEngine;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.home.entity.MainResponseEntity;
import com.boby.livinghelper.app.home.mvp.contract.MainContact;
import com.boby.livinghelper.base.BaseModel;
import com.boby.livinghelper.rx.RxSchedulers;
import rx.Observable;

/**
 * Main Model层
 *
 * @author zbobin.com
 * @date 2017/9/13.
 */

public class MainModel implements MainContact.Model{
    @Override
    public Observable<MainResponseEntity> getMainEntity() {
        return ApiEngine.getInstance().getApiService()
                .getMainEntity("api_livinghelper_main.php")
                .compose(RxSchedulers.switchThread());
    }
}
