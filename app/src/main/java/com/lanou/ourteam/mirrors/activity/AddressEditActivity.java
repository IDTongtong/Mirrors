package com.lanou.ourteam.mirrors.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHDelete on 16/4/12.
 */
public class AddressEditActivity extends BaseActivity {

    @InjectView(R.id.activity_address_edit_close_iv)
    ImageView activityAddressEditCloseIv;
    /**
     * private String username;
     * private String cellphone;
     * private String addr_info;
     */
    private EditText userNameEt, cellPhoneEt, addrInfoEt;
    private NetHelper netHelper;

    @Override
    protected int setContent() {
        return R.layout.activity_address_edit_lay;
    }


    @Override
    protected void initView() {
        userNameEt = bindView(R.id.activity_address_edit_user_name_et);
        cellPhoneEt = bindView(R.id.activity_address_edit_phone_num_et);
        addrInfoEt = bindView(R.id.activity_address_edit_address_et);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick(R.id.activity_address_edit_close_iv)
    public void onClick() {
    finish();
    }
}
