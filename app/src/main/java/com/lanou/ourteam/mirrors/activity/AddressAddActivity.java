package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dllo on 16/4/13.
 */
public class AddressAddActivity extends BaseActivity {

    @InjectView(R.id.activity_addaddress_edit_close_iv)
    ImageView activityAddaddressEditCloseIv;
    @InjectView(R.id.activity_addaddress_edit_bar_iv)
    ImageView activityAddaddressEditBarIv;
    @InjectView(R.id.activity_addaddress_edit_user_name_et)
    EditText activityAddaddressEditUserNameEt;
    @InjectView(R.id.activity_addaddress_edit_phone_num_et)
    EditText activityAddaddressEditPhoneNumEt;
    @InjectView(R.id.activity_addaddress_edit_address_et)
    EditText activityAddaddressEditAddressEt;
    @InjectView(R.id.activity_addaddress_edit_commit_btn)
    Button activityAddaddressEditCommitBtn;

    @Override
    protected int setContent() {
        return R.layout.activity_address_addaddress;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick({R.id.activity_addaddress_edit_close_iv, R.id.activity_addaddress_edit_bar_iv, R.id.activity_addaddress_edit_user_name_et, R.id.activity_addaddress_edit_phone_num_et, R.id.activity_addaddress_edit_address_et, R.id.activity_addaddress_edit_commit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_addaddress_edit_close_iv:
                finish();
                break;

            case R.id.activity_addaddress_edit_commit_btn:
                if (activityAddaddressEditUserNameEt.getText()!=null&&activityAddaddressEditPhoneNumEt.getText()!=null&&activityAddaddressEditAddressEt.getText()!=null){

                    Map<String, String> params = new HashMap();
                    params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
                    params.put("device_type", "3");
                    params.put("username",activityAddaddressEditUserNameEt.getText().toString());
                    params.put("cellphone",activityAddaddressEditPhoneNumEt.getText().toString());
                    params.put("addr_info",activityAddaddressEditAddressEt.getText().toString());
                    NetHelper.getInstance().volleyPostTogetNetData(Content.ADD_ADDRESS, params, new VolleyNetListener() {
                        @Override
                        public void onSuccess(String string) {
                            Intent intent = new Intent();
                            setResult(10, intent);
                            finish(); // 不打的话没有跳转
                        }

                        @Override
                        public void onFail(String failStr) {

                        }
                    });

                }
                break;
        }
    }
}
