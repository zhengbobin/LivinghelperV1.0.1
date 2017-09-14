package com.boby.livinghelper.app.home;

import android.os.Bundle;
import android.view.View;
import com.boby.livinghelper.R;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.rx.RxSchedulers;
import java.util.concurrent.TimeUnit;
import rx.Observable;

/**
 * APP启动欢迎页
 *
 * @author zbobin.com
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.<Long>switchThread())
                .subscribe(permission -> {
                    startActivity(MainActivity.class);
                    finish();
                });
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }
}
