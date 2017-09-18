package com.boby.livinghelper.app.news;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.news.adapter.NewsPageAdapter;
import com.boby.livinghelper.app.news.entity.NewsResponseEntity;
import com.boby.livinghelper.app.news.fragment.NewsFragment;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.network.HttpUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

/**
 * 新闻头条
 * https://www.juhe.cn/myData/index/checked/3
 *
 * @zbobin.com
 */
public class NewsActivity extends BaseActivity
        implements View.OnClickListener
        , NewsFragment.UpdateOrderStatusCallback,
        ViewPager.OnPageChangeListener{

    private ImageButton btnBack;
    private TextView tvTitle;
    TabLayout tabNavigation;
    ViewPager vpOrder;

    public static final int CODE_NEWS_DETAIL = 10;
    private ArrayList<Fragment> fragments;
    //类别Id
    private int classType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }


    public void initView() {
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.public_top_bar_title);
        tvTitle.setText(R.string.activity_news);
        tabNavigation = (TabLayout) findViewById(R.id.tab_navigation);
        vpOrder = (ViewPager) findViewById(R.id.vp_order);
        /**
         * 类型:
         * top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),
         * tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
         */
        String[] titles = {
                getString(R.string.news_tab0), getString(R.string.news_tab1),
                getString(R.string.news_tab2), getString(R.string.news_tab3),
                getString(R.string.news_tab4), getString(R.string.news_tab5),
                getString(R.string.news_tab6), getString(R.string.news_tab7),
                getString(R.string.news_tab8), getString(R.string.news_tab9)
        };

        NewsPageAdapter fragmentAdapter = new
                NewsPageAdapter(getSupportFragmentManager(), getFragments(titles.length), titles);

        int page = getIntent().getIntExtra(StaticData.PAGE, 0);
        vpOrder.setOffscreenPageLimit(titles.length);
        vpOrder.setAdapter(fragmentAdapter);

        tabNavigation.setupWithViewPager(vpOrder);
        tabNavigation.setTabMode(TabLayout.MODE_FIXED);

        vpOrder.setCurrentItem(page);
        vpOrder.addOnPageChangeListener(this);

        NewsFragment fragment = (NewsFragment) fragments.get(page);
        fragment.layzzLoad(activity, classType);

    }

    private ArrayList<Fragment> getFragments(int length) {
        fragments = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            NewsFragment fragment =
                    NewsFragment.newInstance(i + 1, classType);
            fragment.setUpdateOrderCountCallback(this);
            fragments.add(fragment);
        }
        return fragments;
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put(StaticData.KEY, StaticData.KEY_VALUE_NEWS);
        params.put("type", "");
        HttpUtils.post(NewsActivity.this, ApiService.URL_MAIN_NEWS, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String arg0) {
                NewsResponseEntity response = NewsResponseEntity.getIntance(arg0);
                if (response == null)
                    return;
            }

        });
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        NewsFragment fragment =
                (NewsFragment) fragments.get(position);
        fragment.layzzLoad(activity, classType);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void updateOrder(int orderTabType) {

    }
}
