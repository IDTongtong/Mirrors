package com.lanou.ourteam.mirrors.activity;

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
import com.lanou.ourteam.mirrors.utils.Toastor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private UserBean data;

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
                createAccountSuccess();

                break;
        }
    }

    /**
     * 创建账号是否成功
     */
    private void createAccountSuccess() {
        NetHelper netHelper = NetHelper.getInstance();
        Map<String, String> params = new HashMap<>();
        params.put("phone_number", createPhoneEt.getText().toString());
        params.put("number", createVerificationEt.getText().toString());
        params.put("password", createPasswordEt.getText().toString());
        netHelper.volleyPostTogetNetData(USER_REG, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("CreateAccountActivity", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.has("result")) {
                        String result = jsonObject.getString("result");
                        switch (result) {
                            case "":
                                String msg = jsonObject.getString("msg");
                                Toastor.showToast(CreateAccountActivity.this, msg);
                                break;
                            case "1":
                                AnalyzeJson analyzeJson = AnalyzeJson.getInstance();
                                data = analyzeJson.analyzeUser(string);
                                Bundle bundle = new Bundle();
                                bundle.putString("phoneNum", createPhoneEt.getText().toString());
                                jumpToActivity(CreateAccountActivity.this, LoginActivity.class, bundle);
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

    //         发送手机验证码
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
