package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
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



import com.lanou.ourteam.mirrors.bean.GoodsDetailsBean;
import com.lanou.ourteam.mirrors.listenerinterface.PoisitionListener;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by dllo on 16/4/7.
 */
public class GoodShopSecondActivityAdapter extends RecyclerView.Adapter {
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

    public void addData(GoodsDetailsBean  datas) {
        this.datas = datas;

    }

    /**
     * 该方法为接收activity传来的监听recycleview滑动距离的value
     */
    public void setScrollValue(int scrollValue) {
        this.layoutScrollValue = scrollValue;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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

             }
         });

        } else if (getItemViewType(position) == TYPE_TRANSPARENT) {
        } else if (getItemViewType(position) == TYPE_GOODS_TITLE) {

            int valueTitle = layoutScrollValue;//滑动总偏移量
            GoodsTitleViewHolder goodsTitleViewHolder = (GoodsTitleViewHolder) holder;
            goodsTitleViewHolder.goodsTitleBrandTv.setText(datas.getData().getBrand());

            ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
                    goodsTitleViewHolder.goodsTitleImg,
                    R.mipmap.ic_launcher,
                    R.mipmap.loading

            );
            NetHelper.getInstance().getImageLoader().get(datas.getData().getGoods_pic(), imageListener);
            goodsTitleViewHolder.goodsTitleLocationTv.setText(datas.getData().getGoods_data().get(0).getLocation());
            goodsTitleViewHolder.goodsTitleCountryTv.setText(datas.getData().getGoods_data().get(0).getCountry());
            goodsTitleViewHolder.goodsTitleEnglishTv.setText(datas.getData().getGoods_data().get(0).getEnglish());
            goodsTitleViewHolder.goodsTitleIntroContent.setText(datas.getData().getGoods_data().get(0).getIntroContent());
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) goodsTitleViewHolder.goodsTitleRelativeLayout.getLayoutParams();
            params.setMargins(0, (int) (100 + (valueTitle * 0.1)), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            goodsTitleViewHolder.goodsTitleRelativeLayout.setLayoutParams(params);
            // Log.d("dddGoodShopSecondActivityA", "(int) (valueTitle*0.1):" + (int) (valueTitle * 0.1));
        }
        //position大于3时候
        else if (getItemViewType(position) == TYPE_GOODS_DETAILS) {
            int valueTitle = layoutScrollValue;//滑动总偏移量

            GoodsDetailsViewHolder goodsDetailsViewHolder = (GoodsDetailsViewHolder) holder;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) goodsDetailsViewHolder.goodsDetailsRelativeLayout.getLayoutParams();
            int detailsHeight = goodsDetailsViewHolder.detailsRelativeLayoutAll.getHeight();


            params.setMargins(0, (int) (150 + (valueTitle * 0.25) + position * 250), 0, 0);

            goodsDetailsViewHolder.goodsDetailsRelativeLayout.setLayoutParams(params);
            // Log.d("GoodShopSecondActivityA", "datas.getData().getDesign_des().size()+2:" + (datas.getData().getDesign_des().size() + 2));


            if (datas.getData().getDesign_des().get(position-2).getType().equals("1") ) {
                goodsDetailsViewHolder.goodsDetailsDetailsName.setText(datas.getData().getGoods_data().get(position - 2).getName());
                goodsDetailsViewHolder.goodsDetailsIntroContent.setText(datas.getData().getGoods_data().get(position - 2).getIntroContent());
                goodsDetailsViewHolder.goodsDetailsRelativeLayout.setVisibility(View.VISIBLE);
            } else {
                //涉及到recycleview横布局的复用 必须让他强行显示
                goodsDetailsViewHolder.goodsDetailsRelativeLayout.setVisibility(View.GONE);
            }

            ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
                    goodsDetailsViewHolder.goodsDetailsImg,
                    R.mipmap.ic_launcher,
                    R.mipmap.loading

            );
            NetHelper.getInstance().getImageLoader().get(datas.getData().getDesign_des().get(position - 2).getImg(), imageListener);


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
      Log.d("sssGoodShopSecondActivityA", "datas.getData().getDesign_des().size() + 2:" + (datas.getData().getDesign_des().size() + 2));
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
private  ImageView imageViewShare;
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

        public GoodsDetailsViewHolder(View itemView) {
            super(itemView);
            goodsDetailsDetailsName = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_name);
            goodsDetailsIntroContent = (TextView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_introContent);
            goodsDetailsImg = (ImageView) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_img);
            goodsDetailsRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_relativelayout);
            detailsRelativeLayoutAll = (RelativeLayout) itemView.findViewById(R.id.item_goodsfragment_content_goods_details_relativelayout_all);

        }
    }
//    private void showShare() {
//        ShareSDK.initSDK(this);
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("分享");
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://3g.163.com/ntes/special/0034073A/wechat_article.html?docid=" + text);
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("我是分享文本");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://3g.163.com/ntes/special/0034073A/wechat_article.html?docid=" + text);
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://3g.163.com/ntes/special/0034073A/wechat_article.html?docid=" + text);
//
//// 启动分享GUI
//        oks.show(this);
//    }
}
