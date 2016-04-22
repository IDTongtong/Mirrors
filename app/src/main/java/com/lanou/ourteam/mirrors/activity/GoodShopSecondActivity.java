package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.GoodShopSecondActivityAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.base.BaseApplication;
import com.lanou.ourteam.mirrors.bean.GoodsDetailsBean;
import com.lanou.ourteam.mirrors.listenerinterface.PoisitionListener;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.MySharedPreferencesUtils;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dllo on 16/4/7.
 * 二级列表的activity
 */
public class GoodShopSecondActivity extends BaseActivity {
    int value = 0;
    RecyclerView recyclerView;
    GoodShopSecondActivityAdapter adapter;
    ArrayList<String> datas;
    RelativeLayout relativeLayout;
    @InjectView(R.id.secondshop_wear_picyure)
    Button secondshopWearPicyure;

    private boolean isup = true, isDown = true;
    private boolean dyUp = true, dyDown = true;
    private boolean down = false;
    ImageView imageViewbackground;
    ////该步是第二种动画出现的步骤,暂未用上

    private ImageView imageViewReturn, purchaseIv;
    private String goodsid;
    private NetHelper netHelper;

    //购买相关 变量   暂不知道是 在确认下单 页 在此拉取,
    private String goods_pic, goods_name, info_des, goods_price;


    @Override
    protected int setContent() {
        return R.layout.activity_second_good_shop;

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        goodsid = intent.getStringExtra("goodid");
        Log.d("ddd", goodsid);
        String background = intent.getStringExtra("background");


        netHelper = NetHelper.getInstance();
        netHelper.loadImageWithVolley(imageViewbackground, background);

        Map<String, String> params = new HashMap();
        params.put("version", "1.0.1");
        params.put("device_type", "3");
        params.put("goods_id", goodsid);
        adapter = new GoodShopSecondActivityAdapter();
        NetHelper.getInstance().volleyPostTogetNetData(Content.GOODS_DETIALS_LIST, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {

                try {

                    adapter.setPositionListener(new PoisitionListener() {
                        @Override
                        public void getPoisition(int poition) {

                        }
                    });
                    //  adapter.setPositionListener(this);

                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(string);
                    GoodsDetailsBean goodsDetailsBean = gson.fromJson(jsonObject.toString(), GoodsDetailsBean.class);
                    //goods_id = goodsDetailsBean.getData().getGoods_id();
                    //Log.d("GoodShopSecondActivity", "*-*-*-" + goods_id);

                    GridLayoutManager gm = new GridLayoutManager(BaseApplication.getContext(), 1);
                    gm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(gm);

                    recyclerView.setAdapter(adapter);
                    adapter.addData(goodsDetailsBean);
                    //向 下订单页面 传递数据,在此 得到
                    goods_pic = goodsDetailsBean.getData().getGoods_pic();
                    goods_name = goodsDetailsBean.getData().getGoods_name();
                    info_des = goodsDetailsBean.getData().getInfo_des();
                    goods_price = goodsDetailsBean.getData().getGoods_price();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String failStr) {


            }
        });


    }

    @Override
    protected void initView() {

        imageViewReturn = (ImageView) findViewById(R.id.secondshop_returnbt);
        imageViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //跳转到 购买 activity 相关 from here
        purchaseIv = (ImageView) findViewById(R.id.secondshop_shop);

        purchaseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean hasLogin = (Boolean) MySharedPreferencesUtils.getData(GoodShopSecondActivity.this, "hasLogin", false);
                //如果首页登陆过,在此处 会得到 true
                if (hasLogin) {

                    PurchaseActivity.startPurchaseActivity(GoodShopSecondActivity.this,
                            goodsid,
                            goods_pic,
                            goods_name,
                            info_des,
                            goods_price);
                } else {
                    Log.d("GoodShopSecondActivity", "购买页面没登录时,跳到了LoginActivity");
                    jumpToActivity(GoodShopSecondActivity.this, LoginActivity.class, null);
                }


            }
        });
        //跳转到 购买 activity 相关 up here

        imageViewbackground = (ImageView) findViewById(R.id.secondshop_background);
        datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add("fff");
        }
        recyclerView = (RecyclerView) findViewById(R.id.secondshop_recyclerview);
        relativeLayout = (RelativeLayout) findViewById(R.id.secondshop_lineout);

        /**
         * 该方法为对recycleview进行滑动监听 获取滑动距离
         * */

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dyUp) {//判断滑动方向  dy>0为上滑

                    //该数值为滑动到recycleview第三个布局by滑动的数值范围
                    if (value <= -3000) {
                        if (isup) {
                            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.into);
                            relativeLayout.setAnimation(animation);
                            relativeLayout.setVisibility(View.VISIBLE);
                            isup = false;
                            isDown = true;
                            down = true;

                        }

                    }

                } else if (down) {

                    if (value >= -2950) {
                        if (isDown) {
                            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.exit);
                            relativeLayout.setAnimation(animation);
                            relativeLayout.setVisibility(View.INVISIBLE);
                            isup = true;
                            isDown = false;
                        }

                    }

                }
                /**
                 * 这里的 value 是获得recyclerview 所有的滑动距离,将每一次的滑动距离叠加形成的结果.
                 * */
                value -= dy;
                Log.d("变化值", "layoutScrollValue:" + value);

                adapter.setScrollValue(value, dy);


                //这是Recyclerview 的方法来获得当前的 value 值.
                // adapter.setScrollValue(value);
                dyUp = dy > 0;  //该步是第二种动画出现的步骤
                dyDown = dy < 0;


                /**
                 * 底部动画操作
                 *
                 * */

            }
        });

    }



    @OnClick(R.id.secondshop_wear_picyure)
    public void onClick() {
        WearPhotoActivity.startWearPhotoActivity(this, goodsid);

    }


}