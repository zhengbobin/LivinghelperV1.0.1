package com.boby.livinghelper.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;
import com.boby.livinghelper.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zbobin.com
 * @date 2014/10/31.
 */
public class ImageLoaderUtil {
    private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private ImageLoadingListener listener;
	private static ImageLoaderUtil loader;
    private DisplayImageOptions.Builder builder;

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

	public static ImageLoaderUtil getInstance() {
		if (loader == null) {
			loader = new ImageLoaderUtil();

		} else {
            loader.init(R.mipmap.icon_default);
        }

		return loader;
	}

	public static ImageLoaderUtil getInstance(int defaultIconResId) {
		if (loader == null) {
			loader = new ImageLoaderUtil(defaultIconResId);

		} else {
            loader.init(defaultIconResId);
        }

		return loader;
	}

	public ImageLoaderUtil(int defaultIconResId) {
        init(defaultIconResId);
	}

    public ImageLoaderUtil() {
        init(R.mipmap.icon_default);
    }

    private void init(int defaultIconResId) {
        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
            listener = new DisplayListener();
            builder = new DisplayImageOptions.Builder();
        }

        options = builder.showImageOnLoading(defaultIconResId)
                .showImageForEmptyUri(defaultIconResId)
                .showImageOnFail(defaultIconResId)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }

	public void loadImage(ImageView imageView, String url) {
		imageLoader.displayImage(url, imageView, options, listener);
	}

    public Bitmap getBitmap(String url) {
        return imageLoader.loadImageSync(url);
    }


	public static Bitmap toRoundCorner(Bitmap bitmap, int round) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);
		canvas.drawRoundRect(rectF, round, round, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

    private static class RoundDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // imageView.setImageBitmap(toRoundCorner(loadedImage, round));
                imageView.setImageBitmap(loadedImage);
            }
        }
    }

    private static class DisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(loadedImage);
            }
        }
    }
}
