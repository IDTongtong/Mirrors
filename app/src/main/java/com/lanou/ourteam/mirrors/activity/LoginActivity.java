package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zt on 16/3/29.
 */
public class LoginActivity extends BaseActivity {
//    private EditText loginPhoneEt;

    @InjectView(R.id.login_close_iv)
    ImageView loginCloseIv;
    @InjectView(R.id.login_bar_iv)
    ImageView loginBarIv;
    @InjectView(R.id.login_phone)
    TextView loginPhone;
    @InjectView(R.id.login_phone_et)
    EditText loginPhoneEt;
    @InjectView(R.id.login_verification_et)
    EditText loginVerificationEt;
    @InjectView(R.id.login_forget_tv)
    TextView loginForgetTv;
    @InjectView(R.id.login_land)
    TextView loginLand;
    @InjectView(R.id.login_btn_create)
    TextView loginBtnCreate;

    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }


    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {
//        注册成功后 , 得到注册的号码
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String num = bundle.getString("phoneNum");
            Log.d("LoginActivity", num);
            loginPhoneEt.setText(num);
        }
    }

    @OnClick({R.id.login_close_iv, R.id.login_forget_tv, R.id.login_land, R.id.login_btn_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_close_iv:
                finish();
                break;
            case R.id.login_forget_tv:

                break;
            case R.id.login_land:
                finish();
                break;
            case R.id.login_btn_create:
                jumpToActivity(this, CreateAccountActivity.class, null);
                break;
        }
    }
}
