package com.boby.livinghelper.app.joke;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;

/**
 * 笑话大全
 *
 * @author zbobin.com
 */
public class JokeActivity extends BaseActivity {

    private ImageButton btnBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        initView();
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.public_top_bar_title);
        tvTitle.setText(R.string.activity_joke);
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
        }
    }
}