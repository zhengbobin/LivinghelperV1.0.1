package com.boby.livinghelper.app.home;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.app.common.CommonWebViewActivity;
import com.boby.livinghelper.app.common.entity.WebIntentEntity;
import com.boby.livinghelper.app.home.adapter.MainGridViewAdapter;
import com.boby.livinghelper.app.home.entity.MainEntity;
import com.boby.livinghelper.app.home.entity.MainResponseEntity;
import com.boby.livinghelper.app.home.mvp.contract.MainContact;
import com.boby.livinghelper.app.home.mvp.presenter.MainPresenter;
import com.boby.livinghelper.app.news.NewsActivity;
import com.boby.livinghelper.app.qarobot.QARobotActivity;
import com.boby.livinghelper.app.setting.SettingActivity;
import com.boby.livinghelper.app.wechathandpick.WechatHandpickActivity;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.config.MainActityModuleType;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.ToastUtil;
import com.boby.livinghelper.widget.LoadDialog;
import com.boby.livinghelper.widget.MyGridView;
import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
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
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;

            // 万年历
            case MainActityModuleType.TYPE_CALENDAR:
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;

            // 在线电影票
            case MainActityModuleType.TYPE_MOVIE:
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;

            // 电影票房
            case MainActityModuleType.TYPE_BOX_OFFICE:
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;

            // 星座运势
            case MainActityModuleType.TYPE_HOROSCOPE:
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;

            // 股票数据
            case MainActityModuleType.TYPE_STOCK:
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;

            // 问答机器人
            case MainActityModuleType.TYPE_QAROBOT:
                intent = new Intent(this, QARobotActivity.class);
                startActivity(intent);
                break;

            default:
                intent = new Intent(this, CommonWebViewActivity.class);
                intent.putExtra(StaticData.IS_HIDE_PANEL, true);
                intent.putExtra(StaticData.WEB_INTENT_ENTITY, new WebIntentEntity(mainEntity.getMain_url(), mainEntity.getMain_url()));
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
}
