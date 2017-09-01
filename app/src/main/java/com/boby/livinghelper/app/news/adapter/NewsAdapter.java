package com.boby.livinghelper.app.news.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boby.livinghelper.R;
import com.boby.livinghelper.app.news.entity.NewsEntity;
import com.boby.livinghelper.app.news.fragment.NewsFragment;
import com.boby.livinghelper.util.ImageLoaderUtil;
import com.boby.livinghelper.widget.DialogBuilder;

import java.util.ArrayList;


/**
 * 新闻头条
 *
 * @author zbobin.com
 */
public class NewsAdapter extends BaseAdapter implements View.OnClickListener {
    private static final int ORDER_ALL = 0;
    private ArrayList<NewsEntity> entities;
    private Activity context;
    // 订单的列表类型 全部 待付款...
    private int orderTabType;
    private DialogBuilder builder;

    private NewsFragment.UpdateOrderStatusCallback updateOrderStatusCallback;

    public void setUpdateOrderStatusCallback(NewsFragment.UpdateOrderStatusCallback updateOrderStatusCallback) {
        this.updateOrderStatusCallback = updateOrderStatusCallback;
    }

    public NewsAdapter(Activity context, int orderTabType, ArrayList<NewsEntity> entities) {
        if (entities == null)
            entities = new ArrayList<>();

        this.context = context;
        this.orderTabType = orderTabType;
        this.entities = entities;
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public NewsEntity getItem(int position) {

        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final NewsEntity entity = entities.get(position);
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


        //时间
        viewHolder.textViewTime.setText(entity.getDate());
        //图片
        ImageLoaderUtil.getInstance().loadImage(viewHolder.imageViewImage, entity.getThumbnail_pic_s());
        //描述
        viewHolder.textViewTitle.setText(entity.getTitle());

        return convertView;
    }

    public void onDataChanged(ArrayList<NewsEntity> list) {
        entities = list;
        notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(View parent, int resId) {
        return (V) parent.findViewById(resId);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {

            //投诉
            case R.id.linearLayoutNews:
                final NewsEntity newsEntity = (NewsEntity) view.getTag();

                break;
        }
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
