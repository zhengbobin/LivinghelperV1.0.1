package com.boby.livinghelper.app.horoscope;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.horoscope.entity.HoroscopeResponseEntity;
import com.boby.livinghelper.app.qarobot.util.HttpData;
import com.boby.livinghelper.app.qarobot.util.HttpGetDataListener;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.LogUtil;
import com.boby.livinghelper.util.TimeUtil;
import com.boby.livinghelper.widget.MyPopupWindow;

/**
 * 星座运势
 *
 * https://www.juhe.cn/docs/api/id/58
 * @author zbobin.com
 */
public class HoroscopeActivity extends BaseActivity implements HttpGetDataListener, TextWatcher {

    private ImageButton btnBack;
    private TextView tvTitle;
    private TextView textViewName, textViewDatetime, textViewLunarAll, textViewColor, textViewHealth, textViewLove,
            textViewMoney, textViewNumber, textViewQFriend, textViewSummary, textViewWork;
    private LinearLayout linearLayoutName;

    private HttpData httpData;
    private HoroscopeResponseEntity entity;

    //星座数据
    private String[] xingzuo = {"白羊座",
            "巨蟹座", "天秤座", "摩羯座", "金牛座"
            ,
            "狮子座", "天蝎座", "水瓶座", "双子座 "
            ,
            "处女座", "射手座", "双鱼座"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope);
        initView();
        getData("双子座");
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.public_top_bar_title);
        tvTitle.setText(R.string.activity_horoscope);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewName.addTextChangedListener(this);
        textViewDatetime = (TextView) findViewById(R.id.textViewDatetime);
        textViewLunarAll = (TextView) findViewById(R.id.textViewLunarAll);
        textViewColor = (TextView) findViewById(R.id.textViewColor);
        textViewHealth = (TextView) findViewById(R.id.textViewHealth);
        textViewLove = (TextView) findViewById(R.id.textViewLove);
        textViewMoney = (TextView) findViewById(R.id.textViewMoney);
        textViewNumber = (TextView) findViewById(R.id.textViewNumber);
        textViewQFriend = (TextView) findViewById(R.id.textViewQFriend);
        textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        textViewWork = (TextView) findViewById(R.id.textViewWork);
        linearLayoutName = (LinearLayout) findViewById(R.id.linearLayoutName);
        linearLayoutName.setOnClickListener(this);

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
            case R.id.linearLayoutName:
                new MyPopupWindow(this, textViewName).setHomeAppliances(xingzuo, 0).updateNumberPickerControl();
                break;
        }
    }

    public void getData(String name) {
        httpData = (HttpData) new HttpData(
                ApiService.URL_MAIN_HOROSCOPE + "?key=" + StaticData.KEY_VALUE_HOROSCOPE + "&consName=" + name
                        + "&type=today",
                this).execute();
    }

    @Override
    public void getDataUrl(String data) {
        try {
            LogUtil.e("getDataUrl()", data + "");
            entity = HoroscopeResponseEntity.getInstance(data);
            if (entity == null)
                return;

            if (entity.getError_code().equals("0")) {
                LogUtil.e("entity.getName()", entity.getName());
                textViewDatetime.setText(entity.getDatetime());
                textViewLunarAll.setText(entity.getAll());
                textViewColor.setText(entity.getColor());
                textViewHealth.setText(entity.getHealth());
                textViewLove.setText(entity.getLove());
                textViewMoney.setText(entity.getMoney());
                textViewNumber.setText(entity.getNumber());
                textViewQFriend.setText(entity.getQFriend());
                textViewSummary.setText(entity.getSummary());
                textViewWork.setText(entity.getWork());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        getData(textViewName.getText().toString().trim());
    }
}
