package com.lanou.ourteam.mirrors.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.AddressListBean;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;
import com.lanou.ourteam.mirrors.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ZHDelete on 16/4/11.
 */
public class PurchaseActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.activity_purchase_address_reciever_tv)
    TextView activityPurchaseAddressRecieverTv;
    @InjectView(R.id.activity_purchase_address_add_tv)
    TextView activityPurchaseAddressAddTv;
    @InjectView(R.id.activity_purchase_address_phone_tv)
    TextView activityPurchaseAddressPhoneTv;
    private String goods_id, goods_pic, goods_name, info_des, goods_price;
    private ImageView goodsIv;
    private TextView goodsNameTv, infoDesTv, priceTv, addChangeTv;


    private NetHelper netHelper;
    private AddressListBean addressListBean;


    public static void startPurchaseActivity(Context context,
                                             String goods_id,
                                             String goods_pic,
                                             String goods_name,
                                             String info_des,
                                             String goods_price) {
        Intent intent = new Intent(context, PurchaseActivity.class);
        intent.putExtra("goods_id", goods_id);
        intent.putExtra("goods_pic", goods_pic);
        intent.putExtra("goods_name", goods_name);
        intent.putExtra("info_des", info_des);
        intent.putExtra("goods_price", goods_price);
        context.startActivity(intent);

    }


    @Override
    protected int setContent() {
        return R.layout.activity_purchase_lay;
    }

    @Override
    protected void initView() {
        goodsIv = (ImageView) findViewById(R.id.activity_purchase_goods_iv);
        goodsNameTv = (TextView) findViewById(R.id.activity_purchase_goods_name_tv);
        infoDesTv = (TextView) findViewById(R.id.activity_purchase_info_des_tv);
        priceTv = (TextView) findViewById(R.id.activity_purchase_goods_price_tv);

        addChangeTv = bindView(R.id.activity_purchase_address_changeoradd_tv);
        addChangeTv.setOnClickListener(this);

    }

    @Override
    protected void initData() {


        netHelper = NetHelper.getInstance();

        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        goods_pic = intent.getStringExtra("goods_pic");
        goods_name = intent.getStringExtra("goods_name");
        info_des = intent.getStringExtra("info_des");
        goods_price = intent.getStringExtra("goods_price");

        goodsNameTv.setText(goods_name);
        infoDesTv.setText(info_des);
        priceTv.setText(goods_price);

        netHelper.loadImageWithVolley(goodsIv, goods_pic, 280, 280);

        Map<String, String> params = new HashMap();

        params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
        params.put("device_type", "3");

        netHelper.volleyPostTogetNetData(Content.ADDRESS_LIST, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("PurchaseActivity", "STRING" + string);

                AnalyzeJson analyzeJson = AnalyzeJson.getInstance();
                addressListBean = analyzeJson.analyzeAddressList(string);
                for (int i = 0; i < addressListBean.getData().getList().size(); i++) {
                    if (addressListBean.getData().getList().get(i).getIf_moren().equals("1")){

                        activityPurchaseAddressRecieverTv.setText(addressListBean.getData().getList().get(i).getUsername());
                        activityPurchaseAddressAddTv.setText(addressListBean.getData().getList().get(i).getAddr_info());
                        activityPurchaseAddressPhoneTv.setText(addressListBean.getData().getList().get(i).getCellphone());
                    }
                }
            }

            @Override
            public void onFail(String failStr) {
                Log.d("PurchaseActivity", "   " + failStr);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_purchase_address_changeoradd_tv:
                Intent intent = new Intent(PurchaseActivity.this, AddressListActivity.class);
                startActivityForResult(intent, 10);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            activityPurchaseAddressRecieverTv.setText(data.getStringExtra("username").toString());
            activityPurchaseAddressAddTv.setText(data.getStringExtra("address").toString());
            activityPurchaseAddressPhoneTv.setText(data.getStringExtra("username").toString());
            Toast.makeText(this, "设置默认地址成功", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
