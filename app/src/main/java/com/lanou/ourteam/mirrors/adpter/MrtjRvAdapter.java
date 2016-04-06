package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseRecyclerAdapter;
import com.lanou.ourteam.mirrors.bean.MrtjBean;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class MrtjRvAdapter extends BaseRecyclerAdapter<MrtjBean.DataEntity.ListEntity> {
    public MrtjRvAdapter(Context context) {
        super(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.goods_list_item_view, parent, false);
        return new MrtjViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MrtjBean.DataEntity.ListEntity listEntity = list.get(position);
        MrtjViewHolder mrtjViewHolder = (MrtjViewHolder) holder;
        mrtjViewHolder.goodsNameTv.setText(listEntity.getData_info().getGoods_name());
        mrtjViewHolder.productAreaTv.setText(listEntity.getData_info().getProduct_area());
        mrtjViewHolder.brandTv.setText(listEntity.getData_info().getBrand());

        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
                mrtjViewHolder.picIv,
                R.mipmap.ic_launcher,
                R.mipmap.loading

        );
        String goods_img = listEntity.getData_info().getGoods_img();
        Log.d("MrtjRvAdapter", "图片网址:" + goods_img);
        imageLoader.get(goods_img, imageListener);
    }

    @Override
    public int getItemCount() {
        return isLength()?list.size():0;
    }


    class MrtjViewHolder extends RecyclerView.ViewHolder {
        private ImageView picIv;
        private TextView goodsNameTv, productAreaTv, brandTv;

        public MrtjViewHolder(View itemView) {
            super(itemView);
            picIv = (ImageView) itemView.findViewById(R.id.goods_list_item_pic);
            goodsNameTv = (TextView) itemView.findViewById(R.id.goods_list_item_goods_name_tv);
            productAreaTv = (TextView) itemView.findViewById(R.id.goods_list_item_produce_area_tv);
            brandTv = (TextView) itemView.findViewById(R.id.goods_list_item_brand_tv);
        }
    }
}
