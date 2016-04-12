package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.bean.MenuBean;
import com.lanou.ourteam.mirrors.imagedao.MenuItemEntity;

import java.util.List;

/**
 * Created by dllo on 16/3/31.
 */
public class MainActivityPupwindowListviewAdapter extends BaseAdapter {
    Context context;
    String title;
    int position;
    List<MenuItemEntity> menuItemEntityList;

    public MainActivityPupwindowListviewAdapter(List<MenuItemEntity> menuItemEntityList, Context context, String title) {
        // Log.d("aasMainActivityPupwindowLi", datas.get(1));
        this.context = context;
        this.menuItemEntityList = menuItemEntityList;
        this.title = title;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return menuItemEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItemEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {


        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_pupwindow_listview_item, parent, false);
            myViewHolder = new MyViewHolder(convertView);

            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }


        myViewHolder.textViewTab.setText(menuItemEntityList.get(position).getTitle());
        return convertView;
    }

    public class MyViewHolder {

        TextView textViewTab;
        ImageView imageViewline;

        public MyViewHolder(View v) {
            textViewTab = (TextView) v.findViewById(R.id.acticty_main_popwondow_listview_tv);
            imageViewline = (ImageView) v.findViewById(R.id.popwindow_all_line_iv);
         //传来tilte 如果相等 就让他显示那条线
            if (title.equals(menuItemEntityList.get(position).getTitle())) {
                Log.d("MainActivityPupwindowLi", "position:" + position);
                textViewTab.setTextColor(Color.parseColor(menuItemEntityList.get(position).getButtomColor()));
                imageViewline.setVisibility(View.VISIBLE);
            }
        }

    }
}
