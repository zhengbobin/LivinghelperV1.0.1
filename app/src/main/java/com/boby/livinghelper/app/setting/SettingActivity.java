package com.boby.livinghelper.app.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.tencent.bugly.beta.Beta;

/**
 * 设置页面
 *
 * @author zbobin.com
 */
public class SettingActivity extends BaseActivity
        implements View.OnClickListener{

    private TextView textViewTitle;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setViews();
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    private void setViews() {
        textViewTitle = (TextView) getView(R.id.public_top_bar_title);
        textViewTitle.setText("设置");
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        getView(R.id.view_messageTip).setOnClickListener(this);
        getView(R.id.view_about).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            // 返回
            case R.id.public_top_bar_left_btn:
                finish();
                break;

            // 应用更新
            case R.id.view_messageTip:
                Beta.checkUpgrade();
                break;

            // 关于我们
            case R.id.view_about:
                intent = new Intent(activity, AboutUsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
