package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.bean.MenuBean;

/**
 * Created by dllo on 16/3/31.
 */
public class MainActivityPupwindowListviewAdapter extends BaseAdapter {
    MenuBean menuBean;
    Context context;

    public MainActivityPupwindowListviewAdapter(MenuBean menuBean, Context context) {
      // Log.d("aasMainActivityPupwindowLi", datas.get(1));
this.context = context;
        this.menuBean = menuBean;
    }

    @Override
    public int getCount() {
        return menuBean.getData().getList().size();
    }

    @Override
    public Object getItem(int position) {
        return menuBean.getData().getList().get(position);
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
            myViewHolder.textViewTab = (TextView) convertView.findViewById(R.id.acticty_main_popwondow_listview_tv);
            myViewHolder.imageViewline = (ImageView) convertView.findViewById(R.id.popwindow_all_line_iv);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.textViewTab.setText(menuBean.getData().getList().get(position).getTitle());

        return convertView;
    }

    public class MyViewHolder {
        TextView textViewTab;
        ImageView imageViewline;
    }
}
