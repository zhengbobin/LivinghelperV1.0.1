package com.boby.livinghelper.app.setting;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;

/**
 * 关于我们
 *
 * @author zbobin.com
 */
public class AboutUsActivity extends BaseActivity
        implements View.OnClickListener{

    private TextView textViewTitle;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    private void initView() {
        textViewTitle = (TextView) getView(R.id.public_top_bar_title);
        textViewTitle.setText("关于");
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        TextView textViewVersion = (TextView) getView(R.id.textView_version);
        String versionStr = getResources().getString(R.string.version_num);
        textViewVersion.setText(versionStr + getVersionName());
    }

    /**
     * 获取当前应用的版本信息
     *
     * @return
     */
    private String getVersionName() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.public_top_bar_left_btn:
                finish();
        }
    }
}
