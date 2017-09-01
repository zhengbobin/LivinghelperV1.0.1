package com.boby.livinghelper.app.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.app.common.entity.WebIntentEntity;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import com.boby.livinghelper.config.StaticData;
import com.boby.livinghelper.util.LogUtil;
import com.boby.livinghelper.util.ToastUtil;
import com.boby.livinghelper.widget.LoadDialog;

/**
 * 自定义的内部浏览器
 *
 * @zbobin.com
 */
public class CommonWebViewActivity extends BaseActivity implements View.OnClickListener{

    private ImageButton buttonLeft;
    private TextView textViewTitle;
    private Button closeBtn;
    private WebView webView;
    private Button buttonBack;
    private Button buttonForward;
    private Button buttonRefresh;

    protected boolean backFlag;
    private LoadDialog loadDialog;
    protected Activity activity = this;
    private WebIntentEntity entity;
    private String link = "";
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web_view);

        initViews();
        initWebView();
        dealIntentData();
    }

    @Override
    protected void onDestroy() {

        if (loadDialog != null && loadDialog.isShowing())
        {
            loadDialog.dismiss();
        }

        /**
         * [FATAL:jni_android.cc(295)] Check failed: false. Please include Java exception stack in crash report
         */
        if (webView != null) {
            ViewGroup viewGroup = (ViewGroup) webView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }

        super.onDestroy();
    }

    private void dealIntentData() {
        if(getIntent().hasExtra(StaticData.WEBVIEW_URL))
            link = getIntent().getStringExtra(StaticData.WEBVIEW_URL);
        WebIntentEntity entity = (WebIntentEntity) getIntent().getSerializableExtra(StaticData.WEB_INTENT_ENTITY);
        if (entity == null || entity.getUrl() == null) {
            ToastUtil.showMessage(this, R.string.error_web_parser);
            return;
        }

        // 控制标题
        String title = entity.getTitle();
        if (title == null || "".equals(title)) {
            getView(R.id.view_titleBar).setVisibility(View.GONE);
        } else {
            textViewTitle.setText(entity.getTitle());
        }

        // 控制底部的操作面板
        boolean isHidePanel = getIntent().getBooleanExtra(StaticData.IS_HIDE_PANEL, false);
        if (isHidePanel) {
            getView(R.id.linearLayout_panel2).setVisibility(View.GONE);
        }

        String url = entity.getUrl();
        if (url.contains("http://") || url.contains("https://")) {
            webView.loadUrl(url);
        }

        this.entity = entity;
    }

    private void initViews() {
        textViewTitle = (TextView) getView(R.id.public_top_bar_title);

        buttonLeft = (ImageButton) getView(R.id.public_top_bar_left_btn);
        buttonLeft.setOnClickListener(this);
        buttonLeft.setVisibility(View.VISIBLE);

        buttonBack = (Button) getView(R.id.button_back);
        buttonBack.setOnClickListener(this);

        buttonForward = (Button) getView(R.id.button_forward);
        buttonForward.setOnClickListener(this);

        buttonRefresh = (Button) getView(R.id.button_refresh);
        buttonRefresh.setOnClickListener(this);

        closeBtn = (Button) getView(R.id.button_close);
        closeBtn.setOnClickListener(this);

        webView = (WebView) getView(R.id.webView_web);
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setGeolocationEnabled(true);
        webView.setHapticFeedbackEnabled(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setBackgroundColor(Color.parseColor("#ffeeeeee"));
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setLoadWithOverviewMode(true);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //settings.setPluginsEnabled(true);//support flash
        settings.setUseWideViewPort(true);// 这个很关键
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);

        /*------*/
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        settings.setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        LogUtil.e("-CommonWebViewActivity2-", "cacheDirPath="+cacheDirPath);
        //设置数据库缓存路径
        settings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        settings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        settings.setAppCacheEnabled(true);
        /*-----*/
        //启用地理定位
        settings.setGeolocationEnabled(true);
        //设置定位的数据库路径
        settings.setGeolocationDatabasePath(cacheDirPath);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            settings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                if (url.startsWith("tel:")){
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                } else if(url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                backFlag = true;
                if(!activity.isFinishing()){
                    if (loadDialog == null)
                        loadDialog = new LoadDialog(activity);
                    loadDialog.show();
                }
                buttonRefresh.setBackgroundResource(R.drawable.refresh_stop_bg);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadDialog.dismiss();

                buttonRefresh.setBackgroundResource(R.drawable.selector_web_refresh);
                backFlag = false;
                buttonBack.setEnabled(webView.canGoBack());
                buttonForward.setEnabled(webView.canGoForward());
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //textViewTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    //errorLayout.hideEmptyLayout();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.public_top_bar_left_btn:
                onBackPressed();
                break;

            case R.id.button_back:
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                break;

            case R.id.button_forward:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;

            case R.id.button_refresh:
                if (backFlag) {
                    webView.stopLoading();
                    backFlag = false;
                    buttonRefresh.setBackgroundResource(R.drawable.selector_web_refresh);
                } else {
                    webView.reload();
                }
                break;

            case R.id.button_close:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();

        webView.clearCache(true);
        webView.clearHistory();
    }

}