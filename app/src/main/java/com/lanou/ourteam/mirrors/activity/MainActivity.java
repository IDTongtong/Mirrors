package com.lanou.ourteam.mirrors.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.VerticalPagerAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.MenuBean;

import com.lanou.ourteam.mirrors.common.VerticalViewPager;
import com.lanou.ourteam.mirrors.fragment.MenuFragment;
import com.lanou.ourteam.mirrors.imagedao.DaoEntityHelper;
import com.lanou.ourteam.mirrors.imagedao.MenuItemEntity;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.CommonUtils;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    boolean flag = true;

    FrameLayout menuFrameLayout;
    VerticalViewPager verticalViewPager;
    @InjectView(R.id.main_login_iv)
    TextView mainLoginIv;
    @InjectView(R.id.main_shopping_iv)
    TextView mainShoppingIv;

    private VerticalPagerAdapter mAdapter;
    NetHelper netHelper;
    List<String> info_dataList = new ArrayList<>();
    private ImageView mirrorIv;

    private TextView loginTv;
    MenuBean menuBean;
    private DaoEntityHelper daoEntityHelper;
    public static final String MRTJ = "METJ";
    public static final String STORY_LIST = "专题分析";
    public static final String SHOPPING_CAR = "购物车";
    List<MenuBean.DataEntity.ListEntity> listEntityList;
    List<MenuItemEntity> menuItemEntityList;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        menuFrameLayout = (FrameLayout) findViewById(R.id.main_activity_menulayout);
        if (flag) {
            flag = false;
            jumpToActivity(this, WelcomeActivity.class, null);

        }
        mirrorIv = (ImageView) findViewById(R.id.main_mirror_iv);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.main_vertical_viewpger);


    }

    /**
     * 显示购物车
     */
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin) {
            mainLoginIv.setVisibility(View.GONE);
            mainShoppingIv.setVisibility(View.VISIBLE);
        } else {
            mainLoginIv.setVisibility(View.VISIBLE);
            mainShoppingIv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {

        info_dataList.add(MRTJ);
        info_dataList.add("269");//浏览平光镜
        info_dataList.add("268");//浏览太阳镜
        info_dataList.add(STORY_LIST);
        info_dataList.add(SHOPPING_CAR);
        Map<String, String> params = new HashMap();
        params.put("token", "");

        NetHelper.getInstance().volleyPostTogetNetData(Content.MENU_LIST, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Gson gson = new Gson();
                Log.d("MainActivity", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    menuBean = gson.fromJson(jsonObject.toString(), MenuBean.class);

                    //listEntityList = menuBean.getData().getList();
                    //Log.d("MainActivity", "listEntityList大小:" + listEntityList.size());
                    Log.d("MainActivity", "解析菜单bean 成功");
                    listEntityList = menuBean.getData().getList();
                    Log.d("MainActivity", "listEntityList  size" + listEntityList.size());


                    if (CommonUtils.isNetworkAvailable()) {
                        menuItemEntityList = new ArrayList<>();

                        for (int i = 0; i < listEntityList.size(); i++) {
                            MenuItemEntity menuItemEntity = new MenuItemEntity();
                            MenuBean.DataEntity.ListEntity listEntity = listEntityList.get(i);

                            menuItemEntity.setStore(listEntity.getStore());
                            menuItemEntity.setInfo_data(listEntity.getInfo_data());
                            menuItemEntity.setType(listEntity.getType());
                            menuItemEntity.setButtomColor(listEntity.getButtomColor());
                            menuItemEntity.setTopColor(listEntity.getTopColor());
                            menuItemEntity.setTitle(listEntity.getTitle());
                            menuItemEntity.setButtomColor(menuBean.getData().getColor_data().getSelect_font_color());
                            menuItemEntityList.add(menuItemEntity);
                        }

                        daoEntityHelper.deleteMenuAll();
                        daoEntityHelper.insertMenuList(menuItemEntityList);
                        Log.d("MainActivity", "MenuList 加入数据库成功");

                        mAdapter = new VerticalPagerAdapter(getSupportFragmentManager(),
                                MainActivity.this, menuItemEntityList);

                        verticalViewPager.setAdapter(mAdapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFail(String failStr) {
                Log.d("MainActivity", "***" + failStr);
            }
        });


        mirrorIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playHeartbeatAnimation();
            }
        });


        //有网的时候 清空数据库相关,但是如果在MainActivity 到Fragment期间,断网,有待考虑
        daoEntityHelper = DaoEntityHelper.getInstance();
        if (CommonUtils.isNetworkAvailable()) {
            daoEntityHelper.deleteMrtjAll();
            daoEntityHelper.deleteGoodsAll();
            daoEntityHelper.deleteStoryAll();
            // daoEntityHelper.deleteMenuAll();

            Log.d("MainActivity", "MainActivity里 数据控清空执行");

        }


        if (!CommonUtils.isNetworkAvailable()) {
            Log.d("MainActivity", "没网是 viewpager 适配器 构造方法");
            menuItemEntityList = daoEntityHelper.queryMenu();

            mAdapter = new VerticalPagerAdapter(getSupportFragmentManager(),
                    MainActivity.this, menuItemEntityList);

            verticalViewPager.setAdapter(mAdapter);
        }


    }


    // 按钮模拟心脏跳动
    private void playHeartbeatAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        // Animation.RELATIVE_TO_SELF 变化中心角
        animationSet.addAnimation(new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f));


        animationSet.setDuration(100);
        animationSet.setInterpolator(new AccelerateInterpolator());
        //结尾停在最后一针
        animationSet.setFillAfter(true);
        //对动画进行监听
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            //开始时候怎么样
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            //结束时候怎么样
            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(1.2f, 1.0f, 1.2f,
                        1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.setDuration(200);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(false);
                // 实现心跳的View
                mirrorIv.startAnimation(animationSet);
            }
        });

        // 实现心跳的View
        mirrorIv.startAnimation(animationSet);

    }

    //暴露方法 得到position
    public void getDatafromFragment(int position) {
        Log.d("MainActivity", "从fragment历来" + position);

        //这个是设置viewPager切换过度时间的类
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(100);
        scroller.initViewPagerScroll(verticalViewPager);
        //这个是设置切换过渡时间为0毫秒
        verticalViewPager.setCurrentItem(position);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick({R.id.main_login_iv, R.id.main_shopping_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_login_iv:
                jumpToActivity(this, LoginActivity.class, null);
                break;
            case R.id.main_shopping_iv:
                verticalViewPager.setCurrentItem(menuBean.getData().getList().size() - 1);
                dissMenu();
                break;
        }
    }


    /**
     * 这个类是给ViewPager滚动速度的设置
     * 这个类封装了滚动操作。滚动的持续时间可以通过构造函数传递，并且可以指定滚动动作的持续的最长时间。
     * 经过这段时间，滚动会自动定位到最终位置，
     * 并且通过computeScrollOffset()会得到的返回值为false，表明滚动动作已经结束。
     */
    public class ViewPagerScroller extends Scroller {
        private int mScrollDuration = 2000;             // 滑动速度

        /**
         * http://mengsina.iteye.com/blog/1123339 这个对这个类解释很详细
         * 设置速度速度
         *
         * @param duration
         */
        public void setScrollDuration(int duration) {
            this.mScrollDuration = duration;
        }

        public ViewPagerScroller(Context context) {
            super(context);
        }


        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }


        public void initViewPagerScroll(ViewPager viewPager) {
            try {
                //就是存储一个类的属性值
                //通过这个方法找到private的方法
                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                //试图设置accessible标志。其设置为true防止IllegalAccessExceptions。
                mScroller.setAccessible(true);
                mScroller.set(viewPager, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showMenu(String title) {
        MenuFragment menuFragment = new MenuFragment();


        Bundle bundle = new Bundle();
        bundle.putString("menutitle", title);

        menuFragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.main_activity_menulayout, menuFragment).commit();
        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.mypopwindow_anim);

        menuFrameLayout.setVisibility(View.VISIBLE);
        menuFrameLayout.setAnimation(animation);

    }

    public void dissMenu() {
        fm.isDestroyed();
        menuFrameLayout.setVisibility(View.GONE);
        Log.d("dddMainActivity", "hhh");
    }
}

