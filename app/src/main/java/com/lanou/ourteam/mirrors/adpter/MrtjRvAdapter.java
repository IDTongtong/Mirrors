package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseRecyclerAdapter;
import com.lanou.ourteam.mirrors.bean.MrtjBean;
import com.lanou.ourteam.mirrors.imagedao.DaoEntityHelper;
import com.lanou.ourteam.mirrors.imagedao.MrtjItemEntity;
import com.lanou.ourteam.mirrors.utils.CommonUtils;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class MrtjRvAdapter<ListEntity> extends BaseRecyclerAdapter {
    private DaoEntityHelper daoEntityHelper;

    private List<MrtjItemEntity> mrtjItemEntityList;


    public MrtjRvAdapter(Context context) {
        super(context);
        daoEntityHelper = DaoEntityHelper.getInstance();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.goods_list_item_view, parent, false);
        return new MrtjViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        MrtjBean.DataEntity.ListEntity listEntity = (MrtjBean.DataEntity.ListEntity) list.get(position);
//
//        MrtjViewHolder mrtjViewHolder = (MrtjViewHolder) holder;
//
//        mrtjViewHolder.goodsPriceTv.setText(listEntity.getData_info().getGoods_price());
//        mrtjViewHolder.goodsNameTv.setText(listEntity.getData_info().getGoods_name());
//        mrtjViewHolder.productAreaTv.setText(listEntity.getData_info().getProduct_area());
//        mrtjViewHolder.brandTv.setText(listEntity.getData_info().getBrand());
//
//        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
//                mrtjViewHolder.picIv,
//                R.mipmap.ic_launcher,
//                R.mipmap.loading
//
//        );
//        String goods_img = listEntity.getData_info().getGoods_img();
//        Log.d("MrtjRvAdapter", "图片网址:" + goods_img);
//        imageLoader.get(goods_img, imageListener);


        MrtjViewHolder mrtjViewHolder = (MrtjViewHolder) holder;
//        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
//                mrtjViewHolder.picIv,
//                R.mipmap.ic_launcher,
//                R.mipmap.loading);

        if (CommonUtils.isNetworkAvailable()) {
            //bindview 相关 from here
            final MrtjBean.DataEntity.ListEntity listEntity = (MrtjBean.DataEntity.ListEntity) list.get(position);

            mrtjViewHolder.goodsPriceTv.setText(listEntity.getData_info().getGoods_price());
            mrtjViewHolder.goodsNameTv.setText(listEntity.getData_info().getGoods_name());
            mrtjViewHolder.productAreaTv.setText(listEntity.getData_info().getProduct_area());
            mrtjViewHolder.brandTv.setText(listEntity.getData_info().getBrand());
            mrtjViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, com.lanou.ourteam.mirrors.adpter.GoodShopSecondActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("background", listEntity.getData_info().getGoods_img());
                    intent.putExtra("goodid", listEntity.getData_info().getGoods_id());
                    Log.d("sssaMrtjRvAdapter", listEntity.getData_info().getGoods_id());
                    context.startActivity(intent);
                }
            });

            String goods_img = listEntity.getData_info().getGoods_img();
            Log.d("MrtjRvAdapter", "图片网址:" + goods_img);
//            imageLoader.get(goods_img, imageListener,400,200);
            netHelper.loadImageWithVolley(mrtjViewHolder.picIv, goods_img);

            //bindview 相关 up here


            //有网时存入数据库相关
            //给Mrtj 表 数据库添加数据:
            //daoEntityHelper.deleteGoodsAll();

            String goods_name = listEntity.getData_info().getGoods_name();
            String product_area = listEntity.getData_info().getProduct_area();
            String brand = listEntity.getData_info().getBrand();
            String goods_price = listEntity.getData_info().getGoods_price();

            MrtjItemEntity mrtjItemEntity = new MrtjItemEntity();
            mrtjItemEntity.setGoods_img(goods_img);
            mrtjItemEntity.setGoods_name(goods_name);
            mrtjItemEntity.setProduce_area(product_area);
            mrtjItemEntity.setGoods_price(goods_price);
            mrtjItemEntity.setBrand(brand);


            if (daoEntityHelper.queryMrtjItemByUrl(mrtjItemEntity)) {
                Log.d("MrtjRvAdapter", "该 goods_url 已在数据库中存在");
            } else {

                daoEntityHelper.insert(mrtjItemEntity);
                Log.d("MrtjRvAdapter", "goods_list fragment 插入数据库执行");

            }

        } else {

            MrtjItemEntity mrtjItemEntity = mrtjItemEntityList.get(position);
            if (position < mrtjItemEntityList.size()) {
                mrtjViewHolder.goodsNameTv.setText(mrtjItemEntity.getGoods_name());
                mrtjViewHolder.goodsPriceTv.setText(mrtjItemEntity.getGoods_price());
                mrtjViewHolder.productAreaTv.setText(mrtjItemEntity.getProduce_area());
                mrtjViewHolder.brandTv.setText(mrtjItemEntity.getBrand());

//                imageLoader.get(mrtjItemEntity.getGoods_img(), imageListener);
                netHelper.loadImageWithVolley(mrtjViewHolder.picIv, mrtjItemEntity.getGoods_img());

                mrtjViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "没有网点不进去的", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }



    }

    @Override
    public int getItemCount() {
        if (CommonUtils.isNetworkAvailable()) {

            Log.d("MrtjRvAdapter", "有网时 这里应该走::");
            return isLength() ? list.size() : 0;
        } else {
            Log.d("MrtjRvAdapter", "没网时 这里应该走::");
            mrtjItemEntityList = daoEntityHelper.queryMrtj();
            return mrtjItemEntityList.size();
        }
    }


    class MrtjViewHolder extends RecyclerView.ViewHolder {
        private ImageView picIv;
        private TextView goodsNameTv, goodsPriceTv,productAreaTv, brandTv;
        private RelativeLayout relativeLayout;
        public MrtjViewHolder(View itemView) {
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
