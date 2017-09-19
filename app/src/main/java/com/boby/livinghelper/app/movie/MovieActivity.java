package com.boby.livinghelper.app.movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.common.CommonWebViewActivity;
import com.boby.livinghelper.app.common.entity.WebIntentEntity;
import com.boby.livinghelper.app.movie.entity.MovieResponseEntity;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.LogUtil;
import com.boby.livinghelper.util.ToastUtil;
import com.boby.livinghelper.util.network.HttpUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

/**
 * 在线电影票
 *
 * @author zbobin.com
 */
public class MovieActivity extends BaseActivity {

    private ImageButton btnBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        initView();
        getData();
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.public_top_bar_title);
        tvTitle.setText(R.string.activity_movie);
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

    public void getData() {
        RequestParams params = new RequestParams();
        params.put(StaticData.KEY, StaticData.KEY_VALUE_MOVIE);
        HttpUtils.post(MovieActivity.this, ApiService.URL_MAIN_MOVIE, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToastUtil.showMessage(getApplication(), "请求数据失败，请重试!");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String arg0) {
                try {
                    LogUtil.e("MovieActivity", arg0);
                    MovieResponseEntity response = MovieResponseEntity.getIntance(arg0);
                    if (response == null)
                        return;

                    if (response.getError_code().equals("0")) {
                        if (response.getResult() != null) {
                            Intent intent = new Intent();
                            intent.setClass(MovieActivity.this, CommonWebViewActivity.class);
                            intent.putExtra(StaticData.IS_HIDE_PANEL, true);
                            intent.putExtra(StaticData.WEB_INTENT_ENTITY, new WebIntentEntity(response.getResult().getH5url(), getString(R.string.activity_movie)));
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        ToastUtil.showMessage(MovieActivity.this, R.string.no_data);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
