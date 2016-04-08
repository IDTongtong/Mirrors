package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    private UserBean data;
//    轻量级数据库
    private SharedPreferences sharedPreferences;
    private boolean isFirst = true;

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
                                AnalyzeJson gson = new AnalyzeJson();
                                data = gson.AnalyzeUser(string);
                                //TODO 对data 做后续操作
                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
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

    private void isFirst() {
        // 实例化SharedPreferences对象
        sharedPreferences = getSharedPreferences("isFirst", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("isFirst", true)) {
            // 第一次启动
            // 实例化SharedPreferences.Editor对象
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // 用putString的方法保存数据
            editor.putBoolean("isFirst", false);
            // 提交当前数据
            editor.commit();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
