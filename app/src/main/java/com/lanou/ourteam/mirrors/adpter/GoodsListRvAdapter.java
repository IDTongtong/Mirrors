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
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.bean.GoodsListAllBean;
import com.lanou.ourteam.mirrors.imagedao.DaoEntityHelper;
import com.lanou.ourteam.mirrors.imagedao.GoodsItemEntity;
import com.lanou.ourteam.mirrors.utils.CommonUtils;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import com.lanou.ourteam.mirrors.adpter.GoodShopSecondActivity;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class GoodsListRvAdapter extends RecyclerView.Adapter<GoodsListRvAdapter.MyViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    List<GoodsListAllBean.DataEntity.ListEntity> listEntityList;
    private NetHelper netHelper;
    //private ImageLoader imageLoader;
    private GoodsListAllBean goodsListAllBean;


    private DaoEntityHelper daoEntityHelper;
    private String type;
    private List<GoodsItemEntity> goodsItemEntityList;

    public GoodsListRvAdapter(Context context,String category_id) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        netHelper = NetHelper.getInstance();
        //imageLoader = netHelper.getImageLoader();

        daoEntityHelper = DaoEntityHelper.getInstance();
        type = category_id;
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

//        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
//                holder.picIv,
//                R.mipmap.ic_launcher,
//                R.mipmap.loading);
        if (CommonUtils.isNetworkAvailable()) {

            final GoodsListAllBean.DataEntity.ListEntity listEntity = listEntityList.get(position);

            holder.goodsNameTv.setText(listEntity.getGoods_name());
            holder.goodsPriceTv.setText(listEntity.getGoods_price());
            holder.productAreaTv.setText(listEntity.getProduct_area());
            holder.brandTv.setText(listEntity.getBrand());
            Log.d("GoodsListRvAdapter", "产地::" + listEntity.getProduct_area());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GoodShopSecondActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("background", listEntity.getGoods_img());
                    intent.putExtra("goodid", listEntity.getGoods_id());
                    context.startActivity(intent);

                }
            });



            String goods_img = listEntity.getGoods_img();
            Log.d("GoodsListRvAdapter", "图片网址:" + goods_img);

//            imageLoader.get(goods_img, imageListener,400,200);
            netHelper.loadImageWithVolley(holder.picIv, goods_img);





            //给GoodsItem数据库添加数据:
            String goods_name = listEntity.getGoods_name();
            String product_area = listEntity.getProduct_area();
            String brand = listEntity.getBrand();
            String goods_price = listEntity.getGoods_price();

            GoodsItemEntity goodsItemEntity = new GoodsItemEntity();

            goodsItemEntity.setType(type);
            goodsItemEntity.setGoods_img(goods_img);
            goodsItemEntity.setGoods_name(goods_name);
            goodsItemEntity.setProduce_area(product_area);
            goodsItemEntity.setGoods_price(goods_price);
            goodsItemEntity.setBrand(brand);

            if (daoEntityHelper.queryGoodsItemByUrl(goodsItemEntity)) {
                Log.d("GoodsListRvAdapter", "该 goods_url 已在数据库中存在");
            } else {
                daoEntityHelper.insert(goodsItemEntity);
                Log.d("GoodsListRvAdapter", "goods_list fragment 插入数据库执行");
            }

        } else {
            GoodsItemEntity goodsItemEntity = goodsItemEntityList.get(position);
            holder.goodsNameTv.setText(goodsItemEntity.getGoods_name());
            holder.goodsPriceTv.setText(goodsItemEntity.getGoods_price());
            holder.productAreaTv.setText(goodsItemEntity.getProduce_area());
            holder.brandTv.setText(goodsItemEntity.getBrand());

           // imageLoader.get(goodsItemEntity.getGoods_img(), imageListener);
            netHelper.loadImageWithVolley(holder.picIv, goodsItemEntity.getGoods_img());

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "没网点不进去的", Toast.LENGTH_SHORT).show();

                }
            });

        }






    }

    @Override
    public int getItemCount() {
        if (CommonUtils.isNetworkAvailable()) {
            return listEntityList != null && listEntityList.size() > 0 ? listEntityList.size() : 0;

        } else {
            goodsItemEntityList = daoEntityHelper.queryGoodsByTpye(type);
            return goodsItemEntityList.size();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView picIv;
        private TextView goodsNameTv,goodsPriceTv, productAreaTv, brandTv;
        private RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            picIv = (ImageView) itemView.findViewById(R.id.goods_list_item_pic);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.goods_list_item_goods_price_tv);
            goodsNameTv = (TextView) itemView.findViewById(R.id.goods_list_item_goods_name_tv);
            productAreaTv = (TextView) itemView.findViewById(R.id.goods_list_item_produce_area_tv);
            brandTv = (TextView) itemView.findViewById(R.id.goods_list_item_brand_tv);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.goods_list_item_relativilayouty);

        }
    }
}
