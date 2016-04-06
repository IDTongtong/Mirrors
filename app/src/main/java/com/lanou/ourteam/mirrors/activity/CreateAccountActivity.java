package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.listenerinterface.Url;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.NetHelper;
import com.lanou.ourteam.mirrors.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zt on 16/3/30.
 */
public class CreateAccountActivity extends BaseActivity implements Url {


    @InjectView(R.id.create_close_iv)
    ImageView createCloseIv;
    @InjectView(R.id.create_bar_iv)
    ImageView createBarIv;
    @InjectView(R.id.create_phone)
    TextView createPhone;
    @InjectView(R.id.create_phone_et)
    EditText createPhoneEt;
    @InjectView(R.id.create_verification_et)
    EditText createVerificationEt;
    @InjectView(R.id.create_sendVerification_tv)
    TextView createSendVerificationTv;
    @InjectView(R.id.create_password_et)
    EditText createPasswordEt;
    @InjectView(R.id.create_btn_success)
    TextView createBtnSuccess;

    private String phoneNum, verificationCode, password;
    private enum UserOperation{
        LOGIN, REGISTER, RESET_PASSWORD;
    }

    @Override
    protected int setContent() {
        return R.layout.activity_createaccount;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.create_close_iv, R.id.create_sendVerification_tv, R.id.create_btn_success})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_close_iv:
                finish();
                break;
            case R.id.create_sendVerification_tv:
                sendVerification();
                break;
            case R.id.create_btn_success:
                Bundle bundle = new Bundle();
                bundle.putString("phoneNum", createPhoneEt.getText().toString());
                //          Log.d("CreateAccountActivity", createPhoneEt.getText().toString());
                jumpToActivity(this, LoginActivity.class, bundle);
                if (!createPhoneEt.getText().toString().equals(createVerificationEt)) {
                    ToastUtils.showToast(this, "请输入验证码");
                } else if (createPhoneEt == null) {
                    ToastUtils.showToast(this, "请输入手机号");
                } else if (createPasswordEt != null) {

                }


                break;
        }
    }


    private void sendVerification() {
        NetHelper netHelper = NetHelper.getInstance();
        Map<String, String> params = new HashMap();
        params.put("phone number", String.valueOf(createPhoneEt.getText()));
        Log.d("CreateAccountActivity", "电话号码是:::::" + String.valueOf(createVerificationEt.getText()));
        netHelper.volleyPostTogetNetData(USER_SEND_CODE, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("CreateAccountActivity", "你好帅" + string);
            }

            @Override
            public void onFail(String failStr) {

            }
        });
    }

}
