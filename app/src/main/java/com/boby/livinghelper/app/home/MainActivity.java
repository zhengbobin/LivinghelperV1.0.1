package com.boby.livinghelper.app.home;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.boxoffice.BoxOfficeActivity;
import com.boby.livinghelper.app.calendar.CalendarActivity;
import com.boby.livinghelper.app.common.CommonWebViewActivity;
import com.boby.livinghelper.app.common.entity.WebIntentEntity;
import com.boby.livinghelper.app.home.adapter.MainGridViewAdapter;
import com.boby.livinghelper.app.home.entity.MainEntity;
import com.boby.livinghelper.app.home.entity.MainResponseEntity;
import com.boby.livinghelper.app.home.mvp.contract.MainContact;
import com.boby.livinghelper.app.home.mvp.presenter.MainPresenter;
import com.boby.livinghelper.app.horoscope.HoroscopeActivity;
import com.boby.livinghelper.app.joke.JokeActivity;
import com.boby.livinghelper.app.movie.MovieActivity;
import com.boby.livinghelper.app.movie.entity.MovieResponseEntity;
import com.boby.livinghelper.app.news.NewsActivity;
import com.boby.livinghelper.app.qarobot.QARobotActivity;
import com.boby.livinghelper.app.setting.SettingActivity;
import com.boby.livinghelper.app.stock.StockActivity;
import com.boby.livinghelper.app.wechathandpick.WechatHandpickActivity;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.config.MainActityModuleType;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.LogUtil;
import com.boby.livinghelper.util.ToastUtil;
import com.boby.livinghelper.util.jpush.JPushUtil;
import com.boby.livinghelper.util.jpush.LocalBroadcastManager;
import com.boby.livinghelper.util.network.HttpUtils;
import com.boby.livinghelper.widget.LoadDialog;
import com.boby.livinghelper.widget.MyGridView;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

/**
 * 首页
 *
 * @author zbobin.com
 */
public class MainActivity extends BaseActivity<MainPresenter> implements AdapterView.OnItemClickListener, MainContact.View{

    private MyGridView gridView;
    // ------------------

    private MainGridViewAdapter adapter;
    private LoadDialog loadDialog;
    private ArrayList<MainEntity> mainEntities;

    //JPUSH
    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 设置
        if (item.getItemId() == R.id.toolbar_setting) {
            startActivity(SettingActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        gridView = (MyGridView) findViewById(R.id.gv_main);
        gridView.setOnItemClickListener(this);
        loadDialog = new LoadDialog(this);
        //用于接收推送通知消息
        registerMessageReceiver();
    }

    private void initData() {
        if (mainEntities != null) {
            adapter = new MainGridViewAdapter(this, mainEntities);
            gridView.setAdapter(adapter);
        }
        mPresenter.getMainEntity();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent;
        MainEntity mainEntity = mainEntities.get(i);
        switch (mainEntity.getId()) {
            // 新闻头条
            case MainActityModuleType.TYPE_NEWS:
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                break;

            // 微信精选
            case MainActityModuleType.TYPE_WECHAT_HANDPICK:
                intent = new Intent(this, WechatHandpickActivity.class);
                startActivity(intent);
                break;

            // 笑话大全
            case MainActityModuleType.TYPE_JOKE:
                intent = new Intent(this, JokeActivity.class);
                startActivity(intent);
                break;

            // 万年历
            case MainActityModuleType.TYPE_CALENDAR:
                intent = new Intent(this, CalendarActivity.class);
                startActivity(intent);
                break;

            // 在线电影票
            case MainActityModuleType.TYPE_MOVIE:
                getMovieData();
                break;

            // 电影票房
            case MainActityModuleType.TYPE_BOX_OFFICE:
                intent = new Intent(this, BoxOfficeActivity.class);
                startActivity(intent);
                break;

            // 星座运势
            case MainActityModuleType.TYPE_HOROSCOPE:
                intent = new Intent(this, HoroscopeActivity.class);
                startActivity(intent);
                break;

            // 股票数据
            case MainActityModuleType.TYPE_STOCK:
                intent = new Intent(this, StockActivity.class);
                startActivity(intent);
                break;

            // 问答机器人
            case MainActityModuleType.TYPE_QAROBOT:
                intent = new Intent(this, QARobotActivity.class);
                startActivity(intent);
                break;

            default:
                intent = new Intent(this, CommonWebViewActivity.class);
                intent.putExtra(StaticData.IS_HIDE_PANEL, true);
                intent.putExtra(StaticData.WEB_INTENT_ENTITY, new WebIntentEntity(mainEntity.getMain_url(), mainEntity.getMain_name()));
                startActivityForResult(intent, NewsActivity.CODE_NEWS_DETAIL);
                break;
        }
    }

    @Override
    public void showDialog() {
        if (loadDialog == null)
            loadDialog = new LoadDialog(this);
        loadDialog.show();
    }

    @Override
    public void onSuccess(MainResponseEntity entity) {
        try {
            ToastUtil.showMessage(this, entity.getMsg());
            if (StaticData.STATUS_SUCCESS_200.equals(entity.getStatus())) {
                mainEntities = entity.getMain();
                if (adapter == null) {
                    adapter = new MainGridViewAdapter(this, mainEntities);
                    gridView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(String s) {
        if (s == null)
            ToastUtil.showMessage(this, "请求失败");
        else
            ToastUtil.showMessage(this, s + "");
    }

    @Override
    public void hideDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void getMovieData() {
        RequestParams params = new RequestParams();
        params.put(StaticData.KEY, StaticData.KEY_VALUE_MOVIE);
        HttpUtils.post(MainActivity.this, ApiService.URL_MAIN_MOVIE, params, new TextHttpResponseHandler() {
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
                            intent.setClass(MainActivity.this, CommonWebViewActivity.class);
                            intent.putExtra(StaticData.IS_HIDE_PANEL, true);
                            intent.putExtra(StaticData.WEB_INTENT_ENTITY, new WebIntentEntity(response.getResult().getH5url(), getString(R.string.activity_movie)));
                            startActivity(intent);
                        }
                    } else {
                        ToastUtil.showMessage(MainActivity.this, R.string.no_data);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * --------------- JPUSH -------------------
     */
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.estate.chargingpile.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MainActivity.MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!JPushUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        LogUtil.e("推送消息：", msg + "");
        ToastUtil.showMessage(MainActivity.this, "推送消息：" + ": " + msg + "");
    }

}
