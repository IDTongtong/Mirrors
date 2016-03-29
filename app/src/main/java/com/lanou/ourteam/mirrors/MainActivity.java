package com.lanou.ourteam.mirrors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lanou.ourteam.mirrors.activity.WelcomeActivity;
import com.lanou.ourteam.mirrors.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
      jumpToActivity(this, WelcomeActivity.class, null);

    }
}
