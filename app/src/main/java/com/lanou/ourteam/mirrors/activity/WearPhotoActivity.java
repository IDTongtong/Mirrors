package com.lanou.ourteam.mirrors.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.WearPhoRvAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.bean.GoodsItemBean;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.MySharedPreferencesUtils;
import com.lanou.ourteam.mirrors.utils.NetHelper;
import java.util.HashMap;
import java.util.Map;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zt on 16/4/8.
 */
public class WearPhotoActivity extends BaseActivity {
    @InjectView(R.id.activity_wear_back_btn)
    ImageView activityWearBackBtn, purchaseIv;
    private RecyclerView mRecyclerView;
    private String goods_id;
    private String goods_pic, goods_name, info_des, goods_price;
    private NetHelper netHelper;
    private WearPhoRvAdapter mAdapter;

    public static void startWearPhotoActivity(Context context, String goods_id) {
        Intent intent = new Intent(context, WearPhotoActivity.class);
        intent.putExtra("goods_id", goods_id);
        context.startActivity(intent);
    }

    @Override
    protected int setContent() {
        return R.layout.activity_wear_lay;
    }

    @Override
    protected void initData() {
        netHelper = NetHelper.getInstance();
        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        Map<String, String> params = new HashMap();
        params.put("token", "");
        params.put("goods_id", goods_id);
        params.put("device_type", "3");
        params.put("version", "1.0.1");
        netHelper.volleyPostTogetNetData(Content.GOODS_INFO, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                AnalyzeJson analyzeJson = AnalyzeJson.getInstance();
                GoodsItemBean goodsItemBean = analyzeJson.analyzeGoodsItem(string);
                Log.d("WearPhotoActivity", "&&&&&" + goodsItemBean.getData().getWear_video().get(0).getData());
                mAdapter = new WearPhoRvAdapter(WearPhotoActivity.this);
                mAdapter.addData(goodsItemBean.getData().getWear_video());
                mRecyclerView.setLayoutManager(lm);
                mRecyclerView.setAdapter(mAdapter);

                goods_pic = goodsItemBean.getData().getGoods_pic();
                goods_name = goodsItemBean.getData().getGoods_name();
                goods_price = goodsItemBean.getData().getGoods_price();


            }

            @Override
            public void onFail(String failStr) {
            }
        });

    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_wear_recyclerview);
        purchaseIv = (ImageView) findViewById(R.id.activity_wear_purchase);
        purchaseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean hasLogin = (Boolean) MySharedPreferencesUtils.getData(WearPhotoActivity.this, "hasLogin", false);
                //如果首页登陆过,在此处 会得到 true
                if (hasLogin) {

                    PurchaseActivity.startPurchaseActivity(WearPhotoActivity.this,
                            goods_id,
                            goods_pic,
                            goods_name,
                            info_des,
                            goods_price);
                } else {
                    Log.d("GoodShopSecondActivity", "购买页面没登录时,跳到了LoginActivity");
                    jumpToActivity(WearPhotoActivity.this, LoginActivity.class, null);
                }

            }
        });


    }

    @OnClick(R.id.activity_wear_back_btn)
    public void onClick() {
        finish();
    }
}
