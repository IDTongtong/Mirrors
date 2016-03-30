package com.lanou.ourteam.mirrors.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class MainActivityRecycleViewAdapter extends RecyclerView.Adapter<MainActivityRecycleViewAdapter.MyViewHolder> {
    ArrayList<String> data;

    public MainActivityRecycleViewAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_recycleview_item,null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText("这是第"+data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.maintivity_text_tv);
        }
    }
}
