package com.boby.livinghelper.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.view.View;

/**
 * Activity 基础类
 *
 * @author zbobin.com
 * @date 2017/8/28.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    protected Activity activity = this;
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (onCreatePresenter() != null)
            mPresenter = onCreatePresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.unSubsribe();
    }

    protected abstract P onCreatePresenter();

    /**
     * 跳转页面,无extra简易型
     *
     * @param tarActivity
     */
    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(int resId) {
        return (V) findViewById(resId);
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(View parent, int resId) {
        return (V) parent.findViewById(resId);
    }

}
