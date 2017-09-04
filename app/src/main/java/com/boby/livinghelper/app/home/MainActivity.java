package com.boby.livinghelper.app.home;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.app.home.adapter.MainGridViewAdapter;
import com.boby.livinghelper.app.news.NewsActivity;
import com.boby.livinghelper.app.setting.SettingActivity;
import com.boby.livinghelper.app.wechatHandpick.WechatHandpickActivity;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.util.ToastUtil;
import com.boby.livinghelper.widget.MyGridView;

/**
 * 首页
 *
 * @author zbobin.com
 */
public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private MyGridView gridView;
    private String[] strName = new String[]{
            "新闻头条", "微信精选", "笑话大全",
            "万年历","在线电影票", "电影票房",
            "星座运势", "股票数据", "问答机器人"};
    private int[] drawableId = new int[]{
            R.mipmap.icon_1, R.mipmap.icon_2, R.mipmap.icon_3,
            R.mipmap.icon_4,R.mipmap.icon_5, R.mipmap.icon_6,
            R.mipmap.icon_7, R.mipmap.icon_8, R.mipmap.icon_9
    };
    private MainGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
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
    }

    private void initData() {
        adapter = new MainGridViewAdapter(this, strName, drawableId);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent;
        switch (strName[i]) {
            case "新闻头条":
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                break;
            case "微信精选":
                intent = new Intent(this, WechatHandpickActivity.class);
                startActivity(intent);
                break;
            case "笑话大全":
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;
            case "万年历":
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;
            case "在线电影票":
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;
            case "电影票房":
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;
            case "星座运势":
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;
            case "股票数据":
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;
            case "问答机器人":
                ToastUtil.showMessage(this, "正在开发中，敬请期待~");
                break;
        }
    }

}
