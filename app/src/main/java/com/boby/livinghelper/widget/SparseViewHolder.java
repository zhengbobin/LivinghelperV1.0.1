package com.boby.livinghelper.widget;

import android.util.SparseArray;
import android.view.View;

/**
 * desc
 *
 * @author zbobin.com
 */
public class SparseViewHolder {

    public static <V extends View> V getView(View view, int resId) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(resId);
        if (childView == null) {
            childView = view.findViewById(resId);
            viewHolder.put(resId, childView);
        }
        return (V) childView;
    }

}
