package com.boby.livinghelper.app.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.calendar.entity.CalendarDataEntity;
import com.boby.livinghelper.app.calendar.entity.CalendarResponseEntity;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.LogUtil;
import com.boby.livinghelper.util.TimeUtil;
import com.boby.livinghelper.util.ToastUtil;
import com.boby.livinghelper.util.network.HttpUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

/**
 * 万年历
 *
 * @zbobin.com
 */
public class CalendarActivity extends BaseActivity {

    private ImageButton btnBack;
    private TextView tvTitle;
    private TextView textViewDate, textViewLunar, textViewLunarYear, textViewWeekday, textViewAnimalsYear, textViewSuit, textViewAvoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initView();
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.public_top_bar_title);
        tvTitle.setText(R.string.activity_calendar);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewLunar = (TextView) findViewById(R.id.textViewLunar);
        textViewLunarYear = (TextView) findViewById(R.id.textViewLunarYear);
        textViewWeekday = (TextView) findViewById(R.id.textViewWeekday);
        textViewAnimalsYear = (TextView) findViewById(R.id.textViewAnimalsYear);
        textViewSuit = (TextView) findViewById(R.id.textViewSuit);
        textViewAvoid = (TextView) findViewById(R.id.textViewAvoid);
        getData();
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
        params.put(StaticData.KEY, StaticData.KEY_VALUE_CALENDAR);
        params.put("date", TimeUtil.getYearMonthDay());//指定日期,格式为YYYY-MM-DD,如月份和日期小于10,则取个位,如:2017-1-1
        HttpUtils.post(CalendarActivity.this, ApiService.URL_MAIN_CALENDAR, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToastUtil.showMessage(getApplication(), "请求数据失败，请重试!");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String arg0) {
                try {
                    LogUtil.e("CalendarActivity", arg0);
                    CalendarResponseEntity response = CalendarResponseEntity.getInstance(arg0);
                    if (response == null)
                        return;

                    if (response.getError_code().equals("0")) {
                        if (response.getResult().getData() != null) {
                            CalendarDataEntity resultEntity = response.getResult().getData();
                            textViewDate.setText(resultEntity.getDate());
                            textViewLunar.setText(resultEntity.getLunar());
                            textViewLunarYear.setText(resultEntity.getLunarYear());
                            textViewWeekday.setText(resultEntity.getWeekday());
                            textViewAnimalsYear.setText(resultEntity.getAnimalsYear());
                            textViewSuit.setText(resultEntity.getSuit());
                            textViewAvoid.setText(resultEntity.getAvoid());
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
