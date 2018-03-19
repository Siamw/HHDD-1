package com.hj.hd.hhdd;

import android.content.Context;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hwangil on 2018-03-16.
 */

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<listItem> listData = null;
    private int listCount = 0;

    public ListAdapter(ArrayList<listItem> _listData)
    {
        listData = _listData;
        listCount = listData.size();
    }

    @Override
    public int getCount ()
    {
        return listCount;
    }

    @Override
    public Object getItem (int position)
    {
        return null;
    }

    @Override
    public long getItemId (int position)
    {
        return 0;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView textDate = (TextView)convertView.findViewById(R.id.listview_item_date);
        TextView textContent = (TextView)convertView.findViewById(R.id.listview_item_content);

        textDate.setText(listData.get(position).strDate);
        textContent.setText(listData.get(position).strContent);

        return convertView;
    }
}
