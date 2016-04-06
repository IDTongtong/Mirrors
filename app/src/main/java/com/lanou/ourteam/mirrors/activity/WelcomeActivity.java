package com.lanou.ourteam.mirrors.activity;

import android.os.Handler;
import android.widget.ImageView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dllo on 16/3/29.
 */
public class WelcomeActivity extends BaseActivity {
    private ImageView spashIv;

    //安卓os的handler
    Handler handler;

    @Override
    protected int setContent() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {

            //new 一个计时器
            Timer timer = new Timer();
            //tast是计时器的任务
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {

                    finish();
                }
            };
            // 设定计时器的任务以及时间
            timer.schedule(tast, 2000);

    }


    @Override
    protected void initView() {

    }
}
