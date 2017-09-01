package com.boby.livinghelper.app.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boby.livinghelper.R;
import com.boby.livinghelper.widget.SparseViewHolder;

/**
 * desc
 *
 * @author zbobin.com
 * @date 2017/8/31.
 */

public class MainGridViewAdapter extends BaseAdapter {

    private Context context;
    private String[] strName;
    private LayoutInflater inflater;
    private int[] drawableId;

    public MainGridViewAdapter(Context context,String[] strName,int[] drawableId) {
        this.context = context;
        this.strName=strName;
        this.drawableId=drawableId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return strName.length;
    }

    @Override
    public Object getItem(int i) {
        return strName[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.item_main_gridview, viewGroup, false);
        ImageView imageViewIcon= SparseViewHolder.getView(view, R.id.imageView_icon);
        TextView textViewName=SparseViewHolder.getView(view, R.id.textView_name);
        imageViewIcon.setImageDrawable(context.getResources().getDrawable(drawableId[i]));
        textViewName.setText(strName[i]);
        return view;
    }

}
