package com.lanou.ourteam.mirrors.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.VerticalPagerAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.common.customhem.VerticalViewPager;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    boolean flag = true;
    VerticalViewPager verticalViewPager;
    @InjectView(R.id.mian_login_iv)
    TextView mianLoginIv;
    private VerticalPagerAdapter mAdapter;
    NetHelper netHelper;
    List<String> info_dataList = new ArrayList<>();
    private ImageView mirrorIv;


    public static final String MRTJ = "METJ";
    public static final String STORY_LIST = "专题分析";
    public static final String SHOPPING_CAR = "购物车";

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        if (flag) {
            flag = false;
            jumpToActivity(this, WelcomeActivity.class, null);

        }
        mirrorIv = (ImageView) findViewById(R.id.main_mirror_iv);

        verticalViewPager = (VerticalViewPager) findViewById(R.id.main_vertical_viewpger);
//        datas = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            datas.add(new MainActivityRecycleViewFragemt());
//        }

    }

    @Override
    protected void initData() {


        info_dataList.add(MRTJ);
        info_dataList.add("269");//浏览平光镜
        info_dataList.add("268");//浏览太阳镜
        info_dataList.add(STORY_LIST);
        info_dataList.add(SHOPPING_CAR);


        mAdapter = new VerticalPagerAdapter(getSupportFragmentManager(), this, info_dataList);
        verticalViewPager.setAdapter(mAdapter);


        mirrorIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playHeartbeatAnimation();
            }
        });
    }


    // 按钮模拟心脏跳动
    private void playHeartbeatAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        // Animation.RELATIVE_TO_SELF 变化中心角
        animationSet.addAnimation(new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f));


        animationSet.setDuration(200);
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
        scroller.setScrollDuration(50);
        scroller.initViewPagerScroll(verticalViewPager);
        //这个是设置切换过渡时间为0毫秒
        verticalViewPager.setCurrentItem(position);

    }



    @OnClick(R.id.mian_login_iv)
    public void onClick() {

        jumpToActivity(this, LoginActivity.class, null);

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

}

