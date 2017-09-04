package com.boby.livinghelper.app.wechatHandpick.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.app.wechatHandpick.entity.WechatHandpickEntity;
import com.boby.livinghelper.util.ImageLoaderUtil;
import java.util.ArrayList;

/**
 * 微信精选列表适配器
 *
 * @author zbobin.com
 */
public class WechatHandpickAdapter extends BaseAdapter {
    private ArrayList<WechatHandpickEntity> arraylist;
    private LayoutInflater inflater;
    private Context context;

    public WechatHandpickAdapter(Context context, ArrayList<WechatHandpickEntity> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arraylist.size() == 0 ? 1 : arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(View parent, int resId) {
        return (V) parent.findViewById(resId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final WechatHandpickEntity entity = arraylist.get(position);
        if (entity == null)
            return convertView;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
            viewHolder = new ViewHolder();

            //时间
            viewHolder.textViewTime = getView(convertView, R.id.textViewTime);
            //图片
            viewHolder.imageViewImage = getView(convertView, R.id.imageViewImage);
            //描述
            viewHolder.textViewTitle = getView(convertView, R.id.textViewTitle);
            viewHolder.linearLayoutNews = getView(convertView, R.id.linearLayoutNews);
            convertView.setTag(viewHolder);
        } else {
            Object O = convertView.getTag();
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.linearLayoutNews.setTag(entity);

        try {
            //时间
            viewHolder.textViewTime.setText(entity.getSource());
            //图片
            ImageLoaderUtil.getInstance().loadImage(viewHolder.imageViewImage, entity.getFirstImg());
            //描述
            viewHolder.textViewTitle.setText(entity.getTitle());
        } catch (Exception e) {}

        return convertView;
    }

    class ViewHolder {
        //时间
        private TextView textViewTime;
        //图片
        private ImageView imageViewImage;
        //描述
        private TextView textViewTitle;
        private LinearLayout linearLayoutNews;
    }

}
