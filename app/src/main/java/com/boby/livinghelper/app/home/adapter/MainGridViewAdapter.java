package com.boby.livinghelper.app.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.app.home.entity.MainEntity;
import com.boby.livinghelper.util.ImageLoaderUtil;
import com.boby.livinghelper.widget.SparseViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/8/31.
 */

public class MainGridViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MainEntity> mainEntities;

    public MainGridViewAdapter(Context context,ArrayList<MainEntity> mainEntities) {
        this.context = context;
        this.mainEntities=mainEntities;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mainEntities==null?0:mainEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return mainEntities;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            view=inflater.inflate(R.layout.item_main_gridview, viewGroup, false);
            ImageView imageViewIcon= SparseViewHolder.getView(view, R.id.imageView_icon);
            TextView textViewName=SparseViewHolder.getView(view, R.id.textView_name);
            ImageLoaderUtil.getInstance().loadImage(imageViewIcon, mainEntities.get(i).getMain_img());
            textViewName.setText(mainEntities.get(i).getMain_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
