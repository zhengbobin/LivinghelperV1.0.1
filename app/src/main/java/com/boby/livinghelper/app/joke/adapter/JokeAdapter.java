package com.boby.livinghelper.app.joke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.app.joke.entity.JokeDataEntity;
import java.util.ArrayList;

/**
 * 笑话大全列表适配器
 *
 * @author zbobin.com
 */
public class JokeAdapter extends BaseAdapter {
    private ArrayList<JokeDataEntity> arraylist;
    private LayoutInflater inflater;
    private Context context;

    public JokeAdapter(Context context, ArrayList<JokeDataEntity> arraylist) {
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
        final JokeDataEntity entity = arraylist.get(position);
        if (entity == null)
            return convertView;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_joke, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.textViewContent = getView(convertView, R.id.textViewContent);
            convertView.setTag(viewHolder);
        } else {
            Object O = convertView.getTag();
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewContent.setTag(entity);

        try {
            viewHolder.textViewContent.setText(entity.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder {
        private TextView textViewContent;//内容
    }

}
