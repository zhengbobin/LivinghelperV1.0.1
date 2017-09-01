package com.boby.livinghelper.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *
 */
public class OwlImageView extends ImageView {

    public OwlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((AnimationDrawable)this.getDrawable()).start();
    }
}
