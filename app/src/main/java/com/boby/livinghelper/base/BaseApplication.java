package com.boby.livinghelper.base;

import android.app.Application;
import android.graphics.Bitmap;
import com.boby.livinghelper.R;
import com.boby.livinghelper.config.StaticData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.bugly.Bugly;

/**
 * 自定义 Application
 *
 * @author zbobin.com
 * @date 2017/8/28.
 */

public class BaseApplication extends Application {

    public static BaseApplication mContext;
    protected static ImageLoader imgLoader;//异步加载图片实体
    public DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initImagLoader();
        // Bugly SDK初始化
        Bugly.init(getApplicationContext(), StaticData.BUGLY_APP_ID, false);//true:启动Debug，false：关闭Debug
    }

    public static BaseApplication getContext() {
        return mContext;
    }

    /**
     * 初始化ImageLoader
     */
    private void initImagLoader() {
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.icon_default)
                .showImageForEmptyUri(R.mipmap.icon_default)
                .showImageOnFail(R.mipmap.icon_default)
                .cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

}
