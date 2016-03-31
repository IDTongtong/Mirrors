package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/31.
 */
public class MainActivityPupwindowListviewAdapter extends BaseAdapter {
    ArrayList<String> datas;
    Context context;

    public MainActivityPupwindowListviewAdapter(ArrayList<String> datas, Context context) {
        Log.d("aasMainActivityPupwindowLi", datas.get(1));
this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {


        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_pupwindow_listview_item, parent, false);
            myViewHolder = new MyViewHolder();
            myViewHolder.textView = (TextView) convertView.findViewById(R.id.acticty_main_popwondow_listview_tv);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.textView.setText(datas.get(position));

        return convertView;
    }

    public class MyViewHolder {
        TextView textView;
    }
}
