package com.boby.livinghelper.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/9/1.
 */

public class MyPreference {

    private static MyPreference instance = null;
    private Context mContext = null;
    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;

    public static final String ID = "id";
    private static final String SAVE_NAME = "UserSaveName";

    public void clear() {
        editor.clear();
        editor.commit();
    }

    private MyPreference(Context context) {
        mContext = context;
    }

    public static MyPreference getInstance(Context context) {
        if (instance == null) {
            instance = new MyPreference(context);
            settings = context.getSharedPreferences(SAVE_NAME, 0);
            editor = settings.edit();

        }
        return instance;
    }

    /**
     * 用户id
     *
     * @return
     */
    public String getId() {
        return settings.getString(ID, "");
    }

    public void setId(String id) {
        editor.putString(ID, id);
        editor.commit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return settings.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return settings.getBoolean(key, false);
    }


    public void putInt(String Key, int value) {
        editor.putInt(Key, value);
        editor.commit();
    }

    public int getUpDateTime(String key) {
        return settings.getInt(key, 30 * 60);
    }

    public int getInt(String key) {
        return settings.getInt(key, 0);
    }



}
