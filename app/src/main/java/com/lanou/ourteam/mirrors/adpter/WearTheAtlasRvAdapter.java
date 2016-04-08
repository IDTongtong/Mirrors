package com.lanou.ourteam.mirrors.adpter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.bean.WearBean;
import com.lanou.ourteam.mirrors.utils.NetHelper;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by zt on 16/4/7.
 */
public class WearTheAtlasRvAdapter extends RecyclerView.Adapter<WearTheAtlasRvAdapter.ViewHolder> {


    private ArrayList<WearBean> data;
    private Context context;
    protected NetHelper netHelper;
    protected ImageLoader imageLoader;

    public void addData(ArrayList<WearBean> data,Context context) {
        this.context = context;
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.weartheatlas_item_view, parent, false));
        return holder;

//        View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.weartheatlas_item_view, parent, false);
//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (data != null && data.size() > 0) {
            WearBean wearBean = data.get(position);
            holder.weartheatlasItemIv.setImageResource(wearBean.getImg());
            holder.position = position;
        }


    }

    @Override
    public int getItemCount() {
        return data != null && data.size() > 0 ? data.size() : 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.weartheatlas_item_iv)
        ImageView weartheatlasItemIv;
        @InjectView(R.id.weartheatlas_item_ll)
        AutoLinearLayout weartheatlasItemLl;
        private int position;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
