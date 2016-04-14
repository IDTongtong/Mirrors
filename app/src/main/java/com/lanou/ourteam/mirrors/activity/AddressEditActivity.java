package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZHDelete on 16/4/12.
 */
public class AddressEditActivity extends BaseActivity {

    /**
     * private String username;
     * private String cellphone;
     * private String addr_info;
     */
    private EditText userNameEt, cellPhoneEt, addrInfoEt;
    Button button;
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


        button = (Button) findViewById(R.id.activity_address_edit_commit_btn);
        //   finish();
    }

    @Override
    protected void initData() {
        Intent intent;
        intent = getIntent();
        String name = intent.getStringExtra("username");
        String phone = intent.getStringExtra("cellphone");
        String address = intent.getStringExtra("addrinfo");
        userNameEt.setText(name);
        cellPhoneEt.setText(phone);
        addrInfoEt.setText(address);
     final String addrid = intent.getStringExtra("addr_id");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (userNameEt.getText()!=null||cellPhoneEt.getText()!=null||addrInfoEt.getText()!=null) {
                    Map<String, String> params = new HashMap();
                    params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
                    params.put("device_type", "3");
                    params.put("username", userNameEt.getText().toString());
                    params.put("cellphone", cellPhoneEt.getText().toString());
                    params.put("addr_info", addrInfoEt.getText().toString());
                    params.put("addr_id", addrid);
                    NetHelper.getInstance().volleyPostTogetNetData(Content.EDIT_ADDRESS, params, new VolleyNetListener() {
                        @Override
                        public void onSuccess(String string) {
                            Toast.makeText(AddressEditActivity.this, "编辑成功", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.putExtra("666", "666");
                            setResult(20, intent1);
                            finish();
                        }

                        @Override
                        public void onFail(String failStr) {

                        }
                    });

                }
                else {
                    Toast.makeText(AddressEditActivity.this, "打字！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
