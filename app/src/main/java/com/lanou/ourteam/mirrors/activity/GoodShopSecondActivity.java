package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.activity.WearPhotoActivity;
import com.lanou.ourteam.mirrors.adpter.GoodShopSecondActivityAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.base.BaseApplication;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dllo on 16/4/7.
 * 二级列表的activity
 */
public class GoodShopSecondActivity extends BaseActivity {

    RecyclerView recyclerView;
    GoodShopSecondActivityAdapter adapter;
    ArrayList<String> datas;
    RelativeLayout relativeLayout;
    @InjectView(R.id.secondshop_wear_picyure)
    Button secondshopWearPicyure;
    private int value;
    private boolean isup = true, isDown = true;
    private boolean dyUp = true, dyDown = true;
    private boolean down = false;
    ImageView imageViewbackground;
    ////该步是第二种动画出现的步骤,暂未用上

    ImageView imageViewReturn;

    private String goodsid;
    //private String goods_id;

    private NetHelper netHelper;

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

//        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
//                imageViewbackground,
//                R.mipmap.ic_launcher,
//                R.mipmap.loading
//
//        );

//        NetHelper.getInstance().getImageLoader().get(background, imageListener);

        netHelper = NetHelper.getInstance();
        netHelper.loadImageWithVolley(imageViewbackground,background);

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
                    adapter.addData(goodsDetailsBean);
                    GridLayoutManager gm = new GridLayoutManager(BaseApplication.getContext(), 1);
                    gm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(gm);

                    recyclerView.setAdapter(adapter);
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
                    if (value <= -2600) {
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

                    if (value >= -2550) {
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
                adapter.setScrollValue(value);


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick(R.id.secondshop_wear_picyure)
    public void onClick() {
        WearPhotoActivity.startWearPhotoActivity(this, goodsid);

    }



}