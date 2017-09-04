package com.boby.livinghelper.app.wechatHandpick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.common.CommonWebViewActivity;
import com.boby.livinghelper.app.common.entity.WebIntentEntity;
import com.boby.livinghelper.app.wechatHandpick.adapter.WechatHandpickAdapter;
import com.boby.livinghelper.app.wechatHandpick.entity.WechatHandpickEntity;
import com.boby.livinghelper.app.wechatHandpick.entity.WechatHandpickListEntity;
import com.boby.livinghelper.app.wechatHandpick.entity.WechatHandpickListResponseEntity;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.LogUtil;
import com.boby.livinghelper.util.ToastUtil;
import com.boby.livinghelper.util.network.HttpUtils;
import com.boby.livinghelper.util.network.NetworkUtil;
import com.boby.livinghelper.widget.LoadDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

/**
 * 微信精选实体类
 *
 * @author zbobin.com
 */
public class WechatHandpickActivity extends BaseActivity implements View.OnClickListener,
        PullToRefreshBase.OnRefreshListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener{

    private TextView publicTopBarTitle;
    private WechatHandpickAdapter adapter;
    private int page = 1;
    private PullToRefreshListView mPulltorefresh;
    private ListView listview;
    private ImageButton left_btn;
    private LoadDialog dialog;
    private ArrayList<WechatHandpickEntity> arraylist;
    private int offsetY;
    private int offsetx;
    private WechatHandpickListResponseEntity entity;
    private boolean refresh;

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_handpick);
        initUI();
        initData();
        getContentDate();
    }

    private void initUI() {
        publicTopBarTitle = (TextView) getView(R.id.public_top_bar_title);
        left_btn = (ImageButton) getView(R.id.public_top_bar_left_btn);
        mPulltorefresh = (PullToRefreshListView) getView(R.id.listView_history);
        listview = mPulltorefresh.getRefreshableView();
        mPulltorefresh.setOnRefreshListener(this);
        publicTopBarTitle.setText(getString(R.string.activity_wechat_handpick));
        publicTopBarTitle.setTextColor(getResources().getColor(R.color.white));
        left_btn.setOnClickListener(this);
        listview.setOnScrollListener(this);
        listview.setOnItemClickListener(this);

    }

    private void initData() {
        refresh = true;
    }

    private void getContentDate() {
        dialog = new LoadDialog(this);
        RequestParams params = new RequestParams();
        params.put(StaticData.KEY, StaticData.KEY_VALUE_WECHAT_HANDPICK);
        params.put("pno", page + "");//当前页数，默认1
        params.put("ps", "10");//每页返回条数，最大50，默认20
        params.put("dtype", "json");//返回数据的格式,xml或json，默认json
        params.put(StaticData.PAGE, page);

        HttpUtils.post(this, ApiService.URL_MAIN_WECHAT_HANDPICK, params, new TextHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        if (dialog != null)
                            if (refresh)
                                dialog.show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        dialog.dismiss();
                        ToastUtil.showMessage(getApplication(), "请求数据失败，请重试!");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                        try {

                            entity = WechatHandpickListResponseEntity.getIntance(responseString);
                            if (entity.getError_code().equals("0")) {
                                if (entity.getResult().getList().isEmpty()) {
                                    if (mPulltorefresh.isRefreshing()) {
                                        mPulltorefresh.onRefreshComplete();
                                    }
                                    ToastUtil.showMessage(getApplication(), "无更多数据");
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
                                    ToastUtil.showMessage(getApplication(), "暂无数据");
                                } else {
                                    ToastUtil.showMessage(getApplication(), "暂无数据");
                                }
                            }
                        } catch (Exception e) {}

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dialog.dismiss();
                        if (mPulltorefresh.isRefreshing()) {
                            mPulltorefresh.onRefreshComplete();
                        }
                    }
                }

        );
    }

    private void dispathResposeDate(WechatHandpickListEntity recordHistory) {
        page += 1;
        refresh = false;
        LogUtil.e("page", page + "");
        // arraylist.clear();
        arraylist.addAll(recordHistory.getList());
        if (adapter == null) {
            adapter = new WechatHandpickAdapter(this, arraylist);
        }
        //listview.setSelectionFromTop(offsetx, offsetY);
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
        getContentDate();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.public_top_bar_left_btn:
                finish();
                break;
        }
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

                getContentDate();
            }
        }

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        offsetx = i;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClass(this, CommonWebViewActivity.class);
        intent.putExtra(StaticData.IS_HIDE_PANEL, true);
        intent.putExtra(StaticData.WEB_INTENT_ENTITY, new WebIntentEntity(arraylist.get(i - 1).getUrl(), "微信精选"));
        startActivity(intent);
    }

}
