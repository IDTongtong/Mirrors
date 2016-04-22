package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lanou.ourteam.mirrors.R;


import com.lanou.ourteam.mirrors.base.BaseApplication;
import com.lanou.ourteam.mirrors.bean.GoodsDetailsBean;
import com.lanou.ourteam.mirrors.listenerinterface.PoisitionListener;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;
import com.lanou.ourteam.mirrors.utils.ShareUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by dllo on 16/4/7.
 */
public class GoodShopSecondActivityAdapter extends RecyclerView.Adapter {
    //******
    private NetHelper netHelper = NetHelper.getInstance();
    Context contex2t;

    //接口传position
    PoisitionListener poisitionListener;
    private int layoutScrollValue, valueDy;
    final int TYPE_HEAD = 0;
    final int TYPE_TRANSPARENT = 1;
    final int TYPE_GOODS_TITLE = 2;
    final int TYPE_GOODS_DETAILS = 3;
    GoodsDetailsBean datas;
    Context context;

    public void setPositionListener(PoisitionListener positionListener) {
        this.poisitionListener = positionListener;
    }

    public GoodShopSecondActivityAdapter() {

    }

    public void addData(GoodsDetailsBean datas) {
        this.datas = datas;

    }

    /**
     * 该方法为接收activity传来的监听recycleview滑动距离的value
     */
    public void setScrollValue(int scrollValue,int valueDy ) {
        this.layoutScrollValue = scrollValue;
        this.valueDy = valueDy;

        //刷新UI
        /**
         * 必须加上这句话,持续的刷新从Actvity 接收的滑动值.
         * */
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == TYPE_HEAD) {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_head, null);
            return new HeadViewHolder(view1);
        } else if (viewType == TYPE_TRANSPARENT) {
            View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_transparent, null);
            return new TransparentViewHolder(view2);
        } else if (viewType == TYPE_GOODS_TITLE) {
            View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_goods_title, null);
            return new GoodsTitleViewHolder(view3);
        } else {
            View view4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goodsfragment_content_goods_details, null);

            return new GoodsDetailsViewHolder(view4);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        poisitionListener.getPoisition(position);

        if (getItemViewType(position) == TYPE_HEAD) {
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            ((HeadViewHolder) holder).headGoodsNameTv.setText(datas.getData().getGoods_name());
            headViewHolder.headBrandTv.setText(datas.getData().getBrand());
            headViewHolder.headInfoDesTv.setText(datas.getData().getInfo_des());
            headViewHolder.headGoodsPriceTv.setText(datas.getData().getGoods_price());
            headViewHolder.imageViewShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShareUtils.showShare(datas.getData().getGoods_share()+datas.getData().getGoods_id());
                }
            });

            //设置透明度的变化
            headViewHolder.relativeLayoutHead.getBackground().setAlpha(255);

            Log.d("GoodShopSecondActivityA", "valueDy:" + valueDy);
        } else if (getItemViewType(position) == TYPE_TRANSPARENT) {
        } else if (getItemViewType(position) == TYPE_GOODS_TITLE) {

            int valueTitle = layoutScrollValue;//滑动总偏移量
            GoodsTitleViewHolder goodsTitleViewHolder = (GoodsTitleViewHolder) holder;
            goodsTitleViewHolder.goodsTitleBrandTv.setText(datas.getData().getBrand());


            netHelper.loadImageWithVolley(goodsTitleViewHolder.goodsTitleImg, datas.getData().getGoods_pic());

            goodsTitleViewHolder.goodsTitleLocationTv.setText(datas.getData().getGoods_data().get(0).getLocation());
            goodsTitleViewHolder.goodsTitleCountryTv.setText(datas.getData().getGoods_data().get(0).getCountry());
            goodsTitleViewHolder.goodsTitleEnglishTv.setText(datas.getData().getGoods_data().get(0).getEnglish());
            goodsTitleViewHolder.goodsTitleIntroContent.setText(datas.getData().getGoods_data().get(0).getIntroContent());
            goodsTitleViewHolder.goodsTitleRelativeLayout.getBackground().setAlpha(255);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) goodsTitleViewHolder.goodsTitleRelativeLayout.getLayoutParams();
            params.setMargins(0, (int) (200 + (valueTitle * 0.1)), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            goodsTitleViewHolder.goodsTitleRelativeLayout.setLayoutParams(params);
            // Log.d("dddGoodShopSecondActivityA", "(int) (valueTitle*0.1):" + (int) (valueTitle * 0.1));
        }
        //position大于3时候
        else if (getItemViewType(position) == TYPE_GOODS_DETAILS) {

            int valueTitle = layoutScrollValue;//滑动总偏移量

            GoodsDetailsViewHolder goodsDetailsViewHolder = (GoodsDetailsViewHolder) holder;
            goodsDetailsViewHolder.progressBar.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) goodsDetailsViewHolder.goodsDetailsRelativeLayout.getLayoutParams();


            params.setMargins(0, (int) (250 + (valueTitle * 0.25) + position * 250), 0, 0);

            goodsDetailsViewHolder.goodsDetailsRelativeLayout.setLayoutParams(params);
            goodsDetailsViewHolder.goodsDetailsRelativeLayout.getBackground().setAlpha(255);
            // Log.d("GoodShopSecondActivityA", "datas.getData().getDesign_des().size()+2:" + (datas.getData().getDesign_des().size() + 2));


            if (datas.getData().getDesign_des().get(position - 2).getType().equals("1")) {
                goodsDetailsViewHolder.goodsDetailsDetailsName.setText(datas.getData().getGoods_data().get(position - 2).getName());
                goodsDetailsViewHolder.goodsDetailsIntroContent.setText(datas.getData().getGoods_data().get(position - 2).getIntroContent());
                goodsDetailsViewHolder.goodsDetailsRelativeLayout.setVisibility(View.VISIBLE);
            } else {

                //涉及到recycleview横布局的复用 必须让他强行显示
                goodsDetailsViewHolder.goodsDetailsRelativeLayout.setVisibility(View.GONE);
            }


//            NetHelper.getInstance().getImageLoader().get(datas.getData().getDesign_des().get(position - 2).getImg(), imageListener);
            //  Uri imageUri = Uri.parse(datas.getData().getDesign_des().get(position - 2).getImg());
            // goodsDetailsViewHolder.goodsDetailsImg.setImageURI(imageUri);

            netHelper.loadImageWithVolley(goodsDetailsViewHolder.goodsDetailsImg, datas.getData().getDesign_des().get(position - 2).getImg());
         goodsDetailsViewHolder.progressBar.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEAD;

            case 1:
                return TYPE_TRANSPARENT;
            case 2:
                return TYPE_GOODS_TITLE;
            default:
                return TYPE_GOODS_DETAILS;
        }

    }

    @Override
    public int getItemCount() {
        return datas.getData().getDesign_des().size() + 2;

    }

    /**
     * 新建缓存类
     */
    //商品详情二级页面头布局(半透明)缓存类
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView headGoodsNameTv, headBrandTv, headInfoDesTv, headGoodsPriceTv;
        private RelativeLayout relativeLayoutHead;
        private ImageView imageViewShare;

        public HeadViewHolder(View itemView) {
            super(itemView);
            headGoodsNameTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_name);
            headBrandTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_brand);
            headInfoDesTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_info_des);
            headGoodsPriceTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_head_goods_price);
            imageViewShare = (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_head_share);
            relativeLayoutHead = (RelativeLayout) itemView.findViewById(R.id.
                    item_goodsfragment_content_head_relativelayout);

            // relativeLayoutHead.
        }
    }

    //商品二级页面第二布局(全透明)加载布局时别忘喽!
    public class TransparentViewHolder extends RecyclerView.ViewHolder {
        public TransparentViewHolder(View itemView) {
            super(itemView);
        }
    }

    //商品二级页面商品详情带标题布局缓存类
    public class GoodsTitleViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView goodsTitleBrandTv, goodsTitleCountryTv, goodsTitleLocationTv, goodsTitleEnglishTv, goodsTitleIntroContent;
        private ImageView goodsTitleImg;
        private RelativeLayout goodsTitleRelativeLayout;

        public GoodsTitleViewHolder(View itemView) {
            super(itemView);
            goodsTitleBrandTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_brand);
            goodsTitleCountryTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_country);
            goodsTitleLocationTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_location);
            goodsTitleEnglishTv = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_english);
            goodsTitleIntroContent = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_introContent);
            goodsTitleImg = (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_img);
            goodsTitleRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_title_relativelayout);


        }
    }

    //商品二级页面商品详情不带标题布局缓存类
    public class GoodsDetailsViewHolder extends RecyclerView.ViewHolder {
        //需要网络解析的数据
        private TextView goodsDetailsDetailsName, goodsDetailsIntroContent;
        private ImageView goodsDetailsImg;
        private RelativeLayout goodsDetailsRelativeLayout, detailsRelativeLayoutAll;
    private ProgressBar progressBar;
        public GoodsDetailsViewHolder(View itemView) {
            super(itemView);
            goodsDetailsDetailsName = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_name);
            goodsDetailsIntroContent = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_introContent);
            goodsDetailsImg = (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_img);
            goodsDetailsRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_relativelayout);
            detailsRelativeLayoutAll = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_relativelayout_all);
           progressBar = (ProgressBar) itemView.findViewById(R.id.item_goodsfragment_content_goods_progressbar);
        }
    }


}
