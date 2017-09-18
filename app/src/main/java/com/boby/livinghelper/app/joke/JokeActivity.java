package com.boby.livinghelper.app.joke;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.joke.adapter.JokeAdapter;
import com.boby.livinghelper.app.joke.entity.JokeDataEntity;
import com.boby.livinghelper.app.joke.entity.JokeResponseEntity;
import com.boby.livinghelper.app.joke.entity.JokeResultEntity;
import com.boby.livinghelper.app.qarobot.util.HttpData;
import com.boby.livinghelper.app.qarobot.util.HttpGetDataListener;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.LogUtil;
import com.boby.livinghelper.util.TimeUtil;
import com.boby.livinghelper.util.ToastUtil;
import com.boby.livinghelper.util.network.NetworkUtil;
import com.boby.livinghelper.widget.LoadDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import java.util.ArrayList;

/**
 * 笑话大全
 *
 * @author zbobin.com
 */
public class JokeActivity extends BaseActivity implements HttpGetDataListener,
        PullToRefreshBase.OnRefreshListener, AbsListView.OnScrollListener{

    private ImageButton btnBack;
    private TextView tvTitle;
    private PullToRefreshListView mPulltorefresh;
    private ListView listview;

    private HttpData httpData;
    private int page = 1;
    private JokeAdapter adapter;
    private LoadDialog dialog;
    private ArrayList<JokeDataEntity> arraylist;
    private int offsetY;
    private int offsetx;
    private JokeResponseEntity entity;
    private boolean refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        initView();
        initData();
        getData();
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.public_top_bar_title);
        tvTitle.setText(R.string.activity_joke);
        mPulltorefresh = (PullToRefreshListView) getView(R.id.listView_history);
        listview = mPulltorefresh.getRefreshableView();
        mPulltorefresh.setOnRefreshListener(this);
        listview.setOnScrollListener(this);
    }

    private void initData() {
        refresh = true;
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

    private void showDialog() {
        if (dialog == null)
            dialog = new LoadDialog(this);
        if (dialog != null)
            if (refresh)
                dialog.show();
    }

    private void hideDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    public void getData() {
        showDialog();
        httpData = (HttpData) new HttpData(
                ApiService.URL_MAIN_JOKE + "?key=" + StaticData.KEY_VALUE_JOKE + "&sort=asc"
                        + "&page=" + page + "&pagesize=10" + "&time=" + TimeUtil.getCurrentTimestamp(),
                this).execute();
    }

    @Override
    public void getDataUrl(String data) {
        try {
            LogUtil.e("getDataUrl()", data + "");
            entity = JokeResponseEntity.getInstance(data);
            if (entity == null) {
                ToastUtil.showMessage(getApplication(), R.string.no_data);
                return;
            }
            if (entity.getResult() == null) {
                ToastUtil.showMessage(getApplication(), R.string.no_data);
                return;
            }
            if (entity.getResult().getData() == null) {
                ToastUtil.showMessage(getApplication(), R.string.no_data);
                return;
            }
            if (entity.getError_code().equals("0")) {
                if (entity.getResult().getData().isEmpty()) {
                    if (mPulltorefresh.isRefreshing()) {
                        mPulltorefresh.onRefreshComplete();
                    }
                    ToastUtil.showMessage(getApplication(), R.string.no_more_data);
                } else {
                    if (page == 1) {
                        if (arraylist == null)
                            arraylist = new ArrayList<>();
                        arraylist.clear();

                    }
                    dispathResposeDate(entity.getResult());
                }
            } else {
                if (page == 1) {
                    if (!arraylist.isEmpty()) {
                        arraylist.clear();
                        adapter.notifyDataSetChanged();
                    }
                    ToastUtil.showMessage(getApplication(), R.string.no_data);
                } else {
                    ToastUtil.showMessage(getApplication(), R.string.no_data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            hideDialog();
        }
    }

    private void dispathResposeDate(JokeResultEntity recordHistory) {
        page += 1;
        refresh = false;
        LogUtil.e("page", page + "");
        arraylist.addAll(recordHistory.getData());
        if (adapter == null) {
            adapter = new JokeAdapter(this, arraylist);
        }
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        if (!NetworkUtil.isConnected(activity)) { //如果没有网络
            refreshView.post(new Runnable() {
                @Override
                public void run() {
                    mPulltorefresh.onRefreshComplete();
                    ToastUtil.showMessage(activity, R.string.network_is_disabled);
                    return;
                }
            });
        }
        page = 1;
        getData();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            ViewGroup item = (ViewGroup) listview.getChildAt(0);// 此处必须为0
            if (item != null) {
                offsetY = item.getTop();
            }
            // 加载更多处理
            if (absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {
                getData();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        offsetx = i;
    }
}