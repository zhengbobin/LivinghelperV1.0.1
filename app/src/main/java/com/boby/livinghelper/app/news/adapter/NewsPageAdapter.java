package com.boby.livinghelper.app.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Viewpager的适配器
 *
 * @author zbobin.com
 */
public class NewsPageAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    private String[] pageTitles ;
    public NewsPageAdapter(FragmentManager fm
            , ArrayList<Fragment> fragments, String[] pageTitles) {
        super(fm);
        this.fragments = fragments;
        this.pageTitles = pageTitles;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
