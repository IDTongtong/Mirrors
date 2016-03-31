package com.lanou.ourteam.mirrors.activity;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Scroller;
import android.widget.TextView;


import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.activity.WelcomeActivity;
import com.lanou.ourteam.mirrors.adpter.MainActicityViewpagerAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.common.customhem.VerticalViewPager;
import com.lanou.ourteam.mirrors.fragment.MainActivityRecycleViewFragemt;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends BaseActivity  {

    VerticalViewPager verticalViewPager;
    MainActicityViewpagerAdapter adapter;
    ArrayList<Fragment> datas;
    ImageView imageViewMirror;

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
imageViewMirror.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        playHeartbeatAnimation();
    }
});
    }

    @Override
    protected void initView() {
        jumpToActivity(this, WelcomeActivity.class, null);
        imageViewMirror = (ImageView) findViewById(R.id.mainactivity_mirror);
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new MainActivityRecycleViewFragemt());
        }
verticalViewPager = (VerticalViewPager) findViewById(R.id.mainactivity_viewpager);
        adapter = new MainActicityViewpagerAdapter(getSupportFragmentManager(),datas);
        verticalViewPager.setAdapter(adapter);




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
                imageViewMirror.startAnimation(animationSet);
            }
        });

        // 实现心跳的View
        imageViewMirror.startAnimation(animationSet);
        
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


    /**
     *
     * 
     *这个类是给ViewPager滚动速度的设置
     *这个类封装了滚动操作。滚动的持续时间可以通过构造函数传递，并且可以指定滚动动作的持续的最长时间。
     * 经过这段时间，滚动会自动定位到最终位置，
     * 并且通过computeScrollOffset()会得到的返回值为false，表明滚动动作已经结束。
     */
    public class ViewPagerScroller extends Scroller {
        private int mScrollDuration = 2000;             // 滑动速度

        /**http://mengsina.iteye.com/blog/1123339 这个对这个类解释很详细
         * 设置速度速度
         * @param duration
         */
        public void setScrollDuration(int duration){
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
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}

