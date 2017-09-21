package com.boby.livinghelper.util.jpush;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.app.common.CommonWebViewActivity;
import com.boby.livinghelper.app.common.entity.WebIntentEntity;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.LogUtil;
import cn.jpush.android.api.JPushInterface;

/**
 * 推送消息页面
 *
 * @author zbobin.ocm
 */
public class JPushMessageActivity extends BaseActivity {

    private ImageButton btnBack;
    private TextView tvTitle;
    private AppCompatTextView textViewTitle, textViewContent, textViewShowDetail;

    private String messageContent,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush_message);
        initView();
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.public_top_bar_title);
        tvTitle.setText(R.string.activity_my_message);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewContent = findViewById(R.id.textViewContent);
        textViewShowDetail = findViewById(R.id.textViewShowDetail);
        textViewShowDetail.setOnClickListener(this);
    }

    private void initData() throws Exception {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = "";
            String content = "";
            if(bundle!=null){
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
            }
            if (!title.isEmpty())
                textViewTitle.setText(title + "：");
            if (content != null) {
                messageContent = content;
                if (content.contains("@")) {
                    messageContent = content.substring(0, content.indexOf("@"));
                    url = content.substring(content.indexOf("@") + 1, content.length());
                    LogUtil.e("content", messageContent + "");
                    LogUtil.e("url", url + "");
                    if (!url.isEmpty())
                        textViewShowDetail.setVisibility(View.VISIBLE);
                }
                textViewContent.setText(messageContent + "");
            }
        }
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.public_top_bar_left_btn:
                finish();
                break;

            case R.id.textViewShowDetail:
                intent = new Intent();
                intent.setClass(JPushMessageActivity.this, CommonWebViewActivity.class);
                intent.putExtra(StaticData.IS_HIDE_PANEL, true);
                intent.putExtra(StaticData.WEB_INTENT_ENTITY, new WebIntentEntity(url, getString(R.string.activity_my_message)));
                startActivity(intent);
                break;
        }
    }
}
