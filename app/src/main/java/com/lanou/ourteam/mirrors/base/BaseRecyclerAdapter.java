package com.lanou.ourteam.mirrors.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //来自 8500的兄弟
    protected LayoutInflater inflater;
    protected Context context;
    protected List<T> list;

    protected NetHelper netHelper;
    protected ImageLoader imageLoader;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        netHelper = NetHelper.getInstance();
        imageLoader = netHelper.getImageLoader();
    }
    public void addData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();

    }
    protected boolean isLength() {
        //判断集合是否为空,集合大小是否大于0
        return list != null && list.size() > 0;
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public  abstract  void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public abstract int getItemCount();
}
