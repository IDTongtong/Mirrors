package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.bean.UserBean;
import com.lanou.ourteam.mirrors.listenerinterface.Url;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zt on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements Url {
//    private EditText loginPhoneEt;

    @InjectView(R.id.login_close_iv)
    ImageView loginCloseIv;
    @InjectView(R.id.login_bar_iv)
    ImageView loginBarIv;
    @InjectView(R.id.login_phone)
    TextView loginPhone;
    @InjectView(R.id.login_phone_et)
    EditText loginPhoneEt;
    @InjectView(R.id.login_password_et)
    EditText loginPasswordEt;
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
        //    监听电话号码的EditText

        loginPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loginPhoneEt.length() != 0 && loginPasswordEt.length() != 0) {
//    当手机号和密码输入时 loginLand可点击状态
                    loginLand.setBackgroundResource(R.drawable.selector_create_account);

                } else {
//                    否则  不可点击
                    loginLand.setBackgroundResource(R.mipmap.button_use_false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // 密码EditText的监听
        loginPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loginPhoneEt.length() != 0 && loginPasswordEt.length() != 0) {
                    // 电话号码和密码都输入时 可点击状态
                    loginLand.setBackgroundResource(R.drawable.selector_create_account);
                } else if (loginPhoneEt.length() == 0 || loginPasswordEt.length() == 0) {
                    // 电话号码和密码只要有一个为空 就是不可点击的状态
                    loginLand.setBackgroundResource(R.mipmap.button_use_false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        注册成功后 , 得到注册的号码
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String num = bundle.getString("phoneNum");
            Log.d("LoginActivity", num);
            loginPhoneEt.setText(num);
        }
    }


    @OnClick({R.id.login_close_iv, R.id.login_land, R.id.login_btn_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_close_iv:
                finish();
                break;
            case R.id.login_land:
                if (loginPhoneEt.getText().toString().equals("") && loginPasswordEt.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入手机号或密码", Toast.LENGTH_SHORT).show();
                }
                toLogin();
                break;
            case R.id.login_btn_create:
                jumpToActivity(this, CreateAccountActivity.class, null);
                break;
        }
    }

    private void toLogin() {
        NetHelper netHelper = NetHelper.getInstance();
        Map<String, String> params = new HashMap<>();
        params.put("phone_number", loginPhoneEt.getText().toString());
        params.put("password", loginPasswordEt.getText().toString());
        netHelper.volleyPostTogetNetData(USER_LOGIN, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {

                try {
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.has("result")) {
                        String result = jsonObject.getString("result");
                        switch (result) {
                            case "":
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                break;
                            case "1":
                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLogin", true);
                                editor.commit();
                                finish();
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String failStr) {

            }
        });

    }


}
