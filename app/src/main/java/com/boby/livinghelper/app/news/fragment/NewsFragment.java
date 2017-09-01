package com.boby.livinghelper.app.news.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.api.ApiService;
import com.boby.livinghelper.app.common.CommonWebViewActivity;
import com.boby.livinghelper.app.common.entity.WebIntentEntity;
import com.boby.livinghelper.app.news.NewsActivity;
import com.boby.livinghelper.app.news.adapter.NewsAdapter;
import com.boby.livinghelper.app.news.entity.NewsEntity;
import com.boby.livinghelper.app.news.entity.NewsResponseEntity;
import com.boby.livinghelper.config.MyPreference;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.LogUtil;
import com.boby.livinghelper.util.network.NetworkRequestExecutor;
import com.boby.livinghelper.util.network.NetworkRquestEntity;
import com.boby.livinghelper.util.network.OnNetworkDataEnverListener;
import com.boby.livinghelper.widget.LoadDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/**
 * 新闻头条
 *
 * @author zbobin.com
 * @date 2016/7/4.
 */

@SuppressLint("ValidFragment")
public class NewsFragment extends Fragment
        implements PullToRefreshBase.OnRefreshListener,
        OnNetworkDataEnverListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    private Activity context;
    protected ListView listView;
    PullToRefreshListView pullToRefreshListView;
    private NewsAdapter newsAdapter;
    // 刷新数据时的弹出框
    private LoadDialog loadDialog;
    // 初始化数据时的弹出框
    private View viewLoading;
    private View footView;
    // 数据加载中
    private boolean isLoading = false;
    private int pageIndex = 0;
    private int type;
    private int classType;
    private String status = "";
    private int listPos;
    private int offsetY;
    private ArrayList<NewsEntity> dataList;

    private boolean isLoad;
    private LinearLayout rlEmptyView;
    private NetworkRquestEntity networkRquestEntity;
    private final int REFRESH_IDENTIFYING = 1;  // 下拉刷新标识
    private final int UP_LOAD_DATA_IDENTIFYING = 2; // 上拉加载标识
    private final int NORMAL_LOAD_DATA = 0;  // 正常获取数据
    private MyPreference pref;
    private Activity activity;


    public static NewsFragment newInstance(int type, int classType) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(StaticData.TYPE, type);
        bundle.putInt(StaticData.TYPES, classType);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(StaticData.TYPE);
            classType = bundle.getInt(StaticData.TYPES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        context = getActivity();
        pref = MyPreference.getInstance(context);
        loadDialog = new LoadDialog(getActivity());
        dataList = new ArrayList<>();
        viewLoading = view.findViewById(R.id.view_loading);
        rlEmptyView = getView(view, R.id.rl_no_data);
        pullToRefreshListView = getView(view, R.id.refreshView_serviceOrder);
        pullToRefreshListView.setOnRefreshListener(onRefreshListener);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setOnItemClickListener(this);
        newsAdapter = new NewsAdapter(context, type, dataList);
        newsAdapter.setUpdateOrderStatusCallback(updateOrderStatusCallback);
        listView.setAdapter(newsAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int state) {
                if (SCROLL_STATE_IDLE == state) {
                    ViewGroup item = (ViewGroup) listView.getChildAt(0);// 此处必须为0
                    if (item != null) {
                        offsetY = item.getTop();
                    }
                    if (absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {
                        // 到底了
                        if (!isLoading) {
                            getData(UP_LOAD_DATA_IDENTIFYING, false, type);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                listPos = firstVisibleItem;
            }
        });

        footView = LayoutInflater.from(context).inflate(R.layout.footer_loading_layout, null);
        ProgressBar progressBar = (ProgressBar) footView.findViewById(R.id.footer_load_pb);
        progressBar.setVisibility(View.VISIBLE);
        footView.setVisibility(View.GONE);

        listView.addFooterView(footView);
    }


    public void layzzLoad(Activity activity, int classType) {
        this.classType = classType;
        if (isLoad) {
            //已经加载过了
            return;
        }
        clearData();
        if (this.activity == null)
            this.activity = activity;
        getData(NORMAL_LOAD_DATA, false, type);
    }


    public void clearData() {
        if (dataList != null) {
            pageIndex = 0;
            dataList.clear();
        }
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(View parent, int resId) {
        return (V) parent.findViewById(resId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0 || position == dataList.size() + 1)
            return;

        NewsEntity entity = (NewsEntity) parent.getItemAtPosition(position);
        if (entity == null)
            return;

        Intent intent = new Intent(context, CommonWebViewActivity.class);//NewsDetailActivity
        intent.putExtra(StaticData.IS_HIDE_PANEL, true);
        intent.putExtra(StaticData.WEB_INTENT_ENTITY, new WebIntentEntity(entity.getUrl(), "新闻头条"));
        startActivityForResult(intent, NewsActivity.CODE_NEWS_DETAIL);

    }

    PullToRefreshBase.OnRefreshListener<ListView> onRefreshListener = new PullToRefreshBase.OnRefreshListener<ListView>() {
        @Override
        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            clearData();
            getData(REFRESH_IDENTIFYING, false, type);
            // 刷新其他三个Fragment
            refreshOthers(type);
        }
    };


    public void refresh() {
        isLoad = false;
    }

    /**
     * 刷新其他三个Fragment
     */
    public void refreshOthers(int type) {

    }

    /**
     * ShoppingOrderActivity回调并刷新四个Fragment
     */
    public void notifyRefresh() {

    }

    /**
     * 0：全部 1：待接单 2：已接单 3：待付款 4:待评价
     */
    public void getData(final int identtifier, final boolean isRefresh, final int type) {
        if (isLoading) {
            return;
        }
        RequestParams params = new RequestParams();
        //KEY
        params.put(StaticData.KEY, StaticData.KEY_VALUE_NEWS);
        //状态(0，1，2，3，... 全部则不传) status
        /**
         * 类型:
         * top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),
         * tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
         */
        if (type == 0) {
            status = "top";
        } else if (type == 1) {
            status = "shehui";
        } else if (type == 2) {
            status = "guonei";
        } else if (type == 3) {
            status = "guoji";
        } else if (type == 4) {
            status = "yule";
        } else if (type == 5) {
            status = "tiyu";
        } else if (type == 6) {
            status = "junshi";
        } else if (type == 7) {
            status = "keji";
        } else if (type == 8) {
            status = "caijing";
        } else if (type == 9) {
            status = "shishang";
        } else {
            status = "";
        }
        params.put(StaticData.TYPE, status);

        if (networkRquestEntity == null) {
            networkRquestEntity = new NetworkRquestEntity(ApiService.URL_MAIN_NEWS, params, isRefresh);
        } else {
            networkRquestEntity.setParams(params);
            networkRquestEntity.setIsShowDilaog(isRefresh);//isShowDialog
        }
        networkRquestEntity.setRequestIdentifier(identtifier);
        new NetworkRequestExecutor(this.activity, this).execute(networkRquestEntity);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NewsActivity.CODE_NEWS_DETAIL:
//                if (data == null)
//                    break;
//
//                boolean isRefresh = data.getBooleanExtra(StaticData.IS_REFRESH, false);
//                if (isRefresh) {
//                    //if (pageIndex > 0) pageIndex = pageIndex - 1;//一定要减1，否则不能正常刷新
//                    pageIndex = 0;
//                    getData(isRefresh, type, classType);
//                }
                break;
        }
    }

    private UpdateOrderStatusCallback updateOrderStatusCallback;

    public void setUpdateOrderCountCallback(UpdateOrderStatusCallback updateOrderStatusCallback) {
        this.updateOrderStatusCallback = updateOrderStatusCallback;
    }

    public interface UpdateOrderStatusCallback {
        /**
         * 更新其他几个tab订单状态
         *
         * @param orderTabType 当前fragment
         */
//        void updateOrder(int orderTabType,boolean isLoadCurr);
        void updateOrder(int orderTabType);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onRequestFailed(NetworkRquestEntity networkRquestEntity, int statuesCode, String responseString) {

    }

    @Override
    public void onRequestSuceeessfully(NetworkRquestEntity networkRquestEntity, int statusCode, String responseString) {
        int Identifier = networkRquestEntity.getRequestIdentifier(); // 标识符
        disposeServiceData(responseString, Identifier);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {

    }


    /**
     * 处理服务端返回的数据
     *
     * @param s
     */
    private void disposeServiceData(String s, int Identifier) {
        try {
            LogUtil.e("--json data--:", s);
            NewsResponseEntity response = NewsResponseEntity.getIntance(s);
            if (response == null)
                return;
            if (response.getResult() == null)
                return;

            if (pageIndex == 0) {
                dataList.clear();
            }
            if ("0".equals(response.getError_code())) {
                ArrayList<NewsEntity> mEntitys = response.getResult().getData();

                dataList.addAll(mEntitys);

                if (newsAdapter == null) {
                    newsAdapter = new NewsAdapter(context, type, dataList);
                    listView.setAdapter(newsAdapter);
                } else {
                    newsAdapter.notifyDataSetChanged();
                }
                isLoad = true;
                pullToRefreshListView.onRefreshComplete();
            }

        } catch (Exception e) {}
    }

}
