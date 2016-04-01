package com.lanou.ourteam.mirrors.activity;


import android.graphics.drawable.BitmapDrawable;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.VerticalPagerAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.fragment.MrtjFragment;
import com.lanou.ourteam.mirrors.selfview.VerticalPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private VerticalPager mVerticalPager;
    private VerticalPagerAdapter mAdapter;
    private List<Fragment> fragmentList;




    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mVerticalPager = (VerticalPager) findViewById(R.id.main_vertical_viewpger);

    }
    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            fragmentList.add(new MrtjFragment());
            Log.d("MainActivity", "new 了几个");
        }
        //fragmentList.add(new GoodsListFragment());

        mAdapter = new VerticalPagerAdapter(getSupportFragmentManager(), this, fragmentList);
        mVerticalPager.setAdapter(mAdapter);
    }


    //左边的pupwindow
    private void showPopupWindow(View view) {
        //5个textview 分别是 所有分类，游览平光眼镜，游览太阳眼镜，专题分享，我的购物车，返回首页，退出。
        TextView textViewAll,textViewShine,textViewSun,textViewShare,textViewShopMyCar,textViewReturnHome,textViewExit;

        // 一个自定义的布局，作为显示的内容 加载popwindow的布局
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.activity_main_pupwindow_view, null);
        // 设置按钮的点击事件

        textViewAll = (TextView) contentView.findViewById(R.id.popwindow_all_tv);
        textViewShine = (TextView) contentView.findViewById(R.id.popwindow_shine_tv);

        textViewSun = (TextView) contentView.findViewById(R.id.popwindow_sun_tv);

        textViewShare = (TextView) contentView.findViewById(R.id.popwindow_share_tv);

        textViewShopMyCar = (TextView) contentView.findViewById(R.id.popwindow_shop_tv);
        textViewReturnHome = (TextView) contentView.findViewById(R.id.popwindow_returnhome_tv);
        textViewExit = (TextView) contentView.findViewById(R.id.popupwindow_exit_tv);
        textViewAll.setOnClickListener(this);
        textViewShine.setOnClickListener(this);
        textViewSun.setOnClickListener(this);
        textViewShare.setOnClickListener(this);
        textViewShopMyCar.setOnClickListener(this);
        textViewReturnHome.setOnClickListener(this);
        textViewExit.setOnClickListener(this);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");
                popupWindow.dismiss();
                return true;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());


        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //给pupwindow设置动画
        popupWindow.setAnimationStyle(R.style.AnimationPreview);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug

        // 设置好参数之后再show

        popupWindow.showAsDropDown(view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popwindow_all_tv:

                break;
            case R.id.popwindow_shine_tv:

                break;
            case R.id.popwindow_sun_tv:

                break;
            case R.id.popwindow_share_tv:

                break;
            case R.id.popwindow_shop_tv:

                break;
            case R.id.popwindow_returnhome_tv:

                break;
            case R.id.popupwindow_exit_tv:

                break;

        }

      jumpToActivity(this, WelcomeActivity.class, null);


    }
}
