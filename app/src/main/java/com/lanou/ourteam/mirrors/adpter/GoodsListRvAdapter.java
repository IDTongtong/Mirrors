package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.activity.GoodShopSecondActivity;
import com.lanou.ourteam.mirrors.bean.GoodsListAllBean;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class GoodsListRvAdapter extends RecyclerView.Adapter<GoodsListRvAdapter.MyViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    List<GoodsListAllBean.DataEntity.ListEntity> listEntityList;
    private NetHelper netHelper;
    private ImageLoader imageLoader;
    private GoodsListAllBean goodsListAllBean;

    public GoodsListRvAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        netHelper = NetHelper.getInstance();
        imageLoader = netHelper.getImageLoader();
    }

    public void initData(GoodsListAllBean goodsListAllBean) {
        Log.d("GoodsListRvAdapter", "适配器传入数据 执行");
        this.goodsListAllBean = goodsListAllBean;
        listEntityList = goodsListAllBean.getData().getList();
        notifyDataSetChanged();
        Log.d("GoodsListRvAdapter", "List 大小" + listEntityList.size());

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.goods_list_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final GoodsListAllBean.DataEntity.ListEntity listEntity = listEntityList.get(position);

        holder.goodsNameTv.setText(listEntity.getGoods_name());
        holder.productAreaTv.setText(listEntity.getProduct_area());
        holder.brandTv.setText(listEntity.getBrand());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context, GoodShopSecondActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("background",listEntity.getGoods_img() );
                intent.putExtra("goodid",listEntity.getGoods_id());

                context.startActivity(intent);
            }
        });
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
                holder.picIv,
                R.mipmap.ic_launcher,
                R.mipmap.loading

        );
        String goods_img = listEntity.getGoods_img();
        Log.d("GoodsListRvAdapter", "图片网址:" + goods_img);
        imageLoader.get(goods_img, imageListener);


//        Picasso.with(context).load(goods_img).into(holder.picIv);

    }

    @Override
    public int getItemCount() {
        return listEntityList!= null && listEntityList.size() > 0 ? listEntityList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView picIv;
        private TextView goodsNameTv, productAreaTv, brandTv;
        private RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            picIv = (ImageView) itemView.findViewById(R.id.goods_list_item_pic);
            goodsNameTv = (TextView) itemView.findViewById(R.id.goods_list_item_goods_name_tv);
            productAreaTv = (TextView) itemView.findViewById(R.id.goods_list_item_produce_area_tv);
            brandTv = (TextView) itemView.findViewById(R.id.goods_list_item_brand_tv);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.goods_list_item_relativilayouty);
        }
    }
}
