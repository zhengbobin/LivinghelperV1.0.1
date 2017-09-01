package com.boby.livinghelper.base;

import android.app.Application;
import android.graphics.Bitmap;

import com.boby.livinghelper.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

}
