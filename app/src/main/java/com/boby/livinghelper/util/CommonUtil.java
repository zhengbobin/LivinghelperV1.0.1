package com.boby.livinghelper.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 常用工具类
 *
 * @author zbobin.com
 * @date 2017/9/19.
 */

public class CommonUtil {

    /**
     * 是否隐藏软键盘
     */
    public static void handleSoftInputState(Activity activity, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            IBinder binder = focusView.getWindowToken();
            if (binder != null) {
                if (isShow) {
                    imm.hideSoftInputFromWindow(binder, InputMethodManager.SHOW_FORCED);
                } else {
                    imm.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

}
