package com.boby.livinghelper.app.stock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.qarobot.util.HttpData;
import com.boby.livinghelper.app.qarobot.util.HttpGetDataListener;
import com.boby.livinghelper.app.stock.entity.StockDapandataEntity;
import com.boby.livinghelper.app.stock.entity.StockDataEntity;
import com.boby.livinghelper.app.stock.entity.StockGopictureEntity;
import com.boby.livinghelper.app.stock.entity.StockResponseEntity;
import com.boby.livinghelper.app.stock.entity.StockResultEntity;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.CommonUtil;
import com.boby.livinghelper.util.LogUtil;
import com.boby.livinghelper.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 股票数据
 *
 * @author zbobin.com
 */
public class StockActivity extends BaseActivity implements HttpGetDataListener {

    private ImageButton btnBack;
    private TextView tvTitle;
    private EditText editTextGid;
    private ImageButton buttonSearch;
    private LinearLayout linearLayoutShow;
    private ImageView imageViewMinurl, imageViewDayurl, imageViewWeekurl, imageViewMonthurl;
    private TextView textViewNowPri, textViewIncrease, textViewIncrePer, textViewTodayStartPri,
            textViewTraNumber, textViewYestodEndPri, textViewTraAmount;


    private HttpData httpData;
    private StockResponseEntity response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        initView();
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.public_top_bar_title);
        tvTitle.setText(R.string.activity_stock);
        editTextGid = (EditText) findViewById(R.id.editTextGid);
        buttonSearch = (ImageButton) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);

        linearLayoutShow = (LinearLayout) findViewById(R.id.linearLayoutShow);
        linearLayoutShow.setVisibility(View.GONE);
        imageViewMinurl = (ImageView) findViewById(R.id.imageViewMinurl);
        imageViewDayurl = (ImageView) findViewById(R.id.imageViewDayurl);
        imageViewWeekurl = (ImageView) findViewById(R.id.imageViewWeekurl);
        imageViewMonthurl = (ImageView) findViewById(R.id.imageViewMonthurl);

        textViewNowPri = (TextView) findViewById(R.id.textViewNowPri);
        textViewIncrease = (TextView) findViewById(R.id.textViewIncrease);
        textViewIncrePer = (TextView) findViewById(R.id.textViewIncrePer);
        textViewTodayStartPri = (TextView) findViewById(R.id.textViewTodayStartPri);
        textViewTraNumber = (TextView) findViewById(R.id.textViewTraNumber);
        textViewYestodEndPri = (TextView) findViewById(R.id.textViewYestodEndPri);
        textViewTraAmount = (TextView) findViewById(R.id.textViewTraAmount);
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

            case R.id.buttonSearch:
                // 先隐藏键盘
                CommonUtil.handleSoftInputState(activity, false);

                String gid = editTextGid.getText().toString().trim();
                if (gid.isEmpty()) {
                    ToastUtil.showMessage(StockActivity.this, "请输入股票编号");
                    return;
                }
                getData(editTextGid.getText().toString().trim());
                break;
        }
    }

    /**
     * 获取股票数据
     *
     * @param gid   //股票编号，上海股市以sh开头，深圳股市以sz开头如：sh601009
     */
    public void getData(String gid) {
        httpData = (HttpData) new HttpData(
                ApiService.URL_MAIN_STOCK + "?key=" + StaticData.KEY_VALUE_STOCK + "&gid=" + gid,
                this).execute();
    }

    @Override
    public void getDataUrl(String data) {
        try {
            LogUtil.e("getDataUrl()", data + "");
            response = StockResponseEntity.getIntance(data);
            if (response == null)
                return;
            if (response.getError_code().equals("0")) {
                ToastUtil.showMessage(StockActivity.this, response.getReason());
                StockResultEntity resultEntity = response.getResult().get(0);
                StockDataEntity dataEntity = resultEntity.getData();
                StockDapandataEntity dapandata = resultEntity.getDapandata();
                StockGopictureEntity gopicture = resultEntity.getGopicture();

                if (dataEntity.getIncrease() != null) {
                    if (dataEntity.getIncrease().startsWith("+")) {
                        textViewNowPri.setTextColor(getResources().getColor(R.color.common_red_normal));
                        textViewIncrease.setTextColor(getResources().getColor(R.color.common_red_normal));
                        textViewIncrePer.setTextColor(getResources().getColor(R.color.common_red_normal));
                    } else {
                        textViewNowPri.setTextColor(getResources().getColor(R.color.stock_green));
                        textViewIncrease.setTextColor(getResources().getColor(R.color.stock_green));
                        textViewIncrePer.setTextColor(getResources().getColor(R.color.stock_green));
                    }
                }
                textViewNowPri.setText(dataEntity.getNowPri());
                textViewIncrease.setText(dataEntity.getIncrease());
                textViewIncrePer.setText(dataEntity.getIncrePer());
                textViewTodayStartPri.setText(dataEntity.getTodayStartPri());
                textViewTraNumber.setText(dataEntity.getTraNumber());
                textViewYestodEndPri.setText(dataEntity.getYestodEndPri());
                textViewTraAmount.setText(dataEntity.getTraAmount());

                ImageLoader.getInstance().displayImage(gopicture.getMinurl(), imageViewMinurl);
                ImageLoader.getInstance().displayImage(gopicture.getMinurl(), imageViewDayurl);
                ImageLoader.getInstance().displayImage(gopicture.getMinurl(), imageViewWeekurl);
                ImageLoader.getInstance().displayImage(gopicture.getMinurl(), imageViewMonthurl);

                linearLayoutShow.setVisibility(View.VISIBLE);
            }else {
                ToastUtil.showMessage(StockActivity.this, response.getReason());
                linearLayoutShow.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
