package com.lanou.ourteam.mirrors.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.alipay.PayResult;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.AddressListBean;
import com.lanou.ourteam.mirrors.bean.AliPayBean;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.bean.SubOrderBean;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHDelete on 16/4/11.
 */
public class PurchaseActivity extends BaseActivity implements View.OnClickListener {

    private Button subOrderBtn;
    @InjectView(R.id.activity_purchase_address_reciever_tv)
    TextView activityPurchaseAddressRecieverTv;
    @InjectView(R.id.activity_purchase_address_add_tv)
    TextView activityPurchaseAddressAddTv;
    @InjectView(R.id.activity_purchase_address_phone_tv)
    TextView activityPurchaseAddressPhoneTv;
    @InjectView(R.id.activity_purdetails_iv_close)
    ImageView activityPurdetailsIvClose;
    private String goods_id, goods_pic, goods_name, info_des, goods_price;
    private ImageView goodsIv;
    private TextView goodsNameTv, infoDesTv, priceTv, addChangeTv;


    private NetHelper netHelper;
    private AddressListBean addressListBean;
    SubOrderBean subOrderBean;

    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PurchaseActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PurchaseActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PurchaseActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


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
        netHelper = NetHelper.getInstance();

        goodsIv = (ImageView) findViewById(R.id.activity_purchase_goods_iv);
        goodsNameTv = (TextView) findViewById(R.id.activity_purchase_goods_name_tv);
        infoDesTv = (TextView) findViewById(R.id.activity_purchase_info_des_tv);
        priceTv = (TextView) findViewById(R.id.activity_purchase_goods_price_tv);

        addChangeTv = bindView(R.id.activity_purchase_address_changeoradd_tv);
        subOrderBtn = bindView(R.id.activity_purchase_sub_order_btn);

        addChangeTv.setOnClickListener(this);
        subOrderBtn.setOnClickListener(this);


    }

    @Override
    protected void initData() {


        netHelper = NetHelper.getInstance();

        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
//        goods_pic = intent.getStringExtra("goods_pic");
//        goods_name = intent.getStringExtra("goods_name");
//        info_des = intent.getStringExtra("info_des");
        goods_price = intent.getStringExtra("goods_price");

        Log.d("PurchaseActivity", "QQQ  " + goods_id + "QQQ  " + goods_price);

        subOrder(goods_id, "1", goods_price, "");


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

    public void subOrder(String goods_id, String goods_num, String goods_price, String discount) {

        Log.d("PurchaseActivity", "FFFF" + goods_id);
        Map<String, String> params = new HashMap();

        params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
        params.put("goods_id", goods_id);
//        params.put("goods_id", "96Psa1455524521");

        params.put("goods_num", goods_num);
//        params.put("goods_num", "1");

        params.put("price", goods_price);
//        params.put("price", "0.01");

        params.put("discount", "");
        params.put("device_type", "3");
        netHelper.volleyPostTogetNetData(Content.SUB_ORDER, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("PurchaseActivity", "DDDD" + string);
                AnalyzeJson analyzeJson = AnalyzeJson.getInstance();
                subOrderBean = analyzeJson.analyzerSubOrder(string);

                Log.d("PurchaseActivity", "subOrderBean   " + subOrderBean.getData().getGoods().getBook_copy());

                goodsNameTv.setText(subOrderBean.getData().getGoods().getGoods_name());
                infoDesTv.setText(subOrderBean.getData().getGoods().getBook_copy());
                priceTv.setText(subOrderBean.getData().getGoods().getPrice());

                String goodsPic = subOrderBean.getData().getGoods().getPic();

                netHelper.loadImageWithVolley(goodsIv, goodsPic, 280, 280);
            }

            @Override
            public void onFail(String failStr) {

                Log.d("PurchaseActivity", "   " + failStr);
            }
        });

    }

    public void aliPay(String order_no, String addr_id, String goods_name) {
        Map<String, String> params = new HashMap();

        params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
        params.put("order_no", order_no);
        params.put("addr_id", addr_id);
        params.put("goodsname", goods_name);
        netHelper.volleyPostTogetNetData(Content.ALI_PAY, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("PurchaseActivity", "支付加密string " + string);
                AnalyzeJson analyzeJson = AnalyzeJson.getInstance();
                AliPayBean aliPayBean = analyzeJson.analyzeAliPay(string);
                final String ali_pay_str_payInfo = aliPayBean.getData().getStr();
                Log.d("PurchaseActivity", "支付加密string解析成功:: " + ali_pay_str_payInfo);
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(PurchaseActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(ali_pay_str_payInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();

            }

            @Override
            public void onFail(String failStr) {

            }
        });

    }


    public void showPopupWindow(View v) {
        PopupWindow popupWindow = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.ali_weixin_popwin_layout, null);

        popupWindow.setContentView(view);
        //宽高
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //点击 外面 取消
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        LinearLayout weixinLl = (LinearLayout) view.findViewById(R.id.ali_weixin_weixinpay_linearl);
        LinearLayout aliLl = (LinearLayout) view.findViewById(R.id.ali_weixin_alipay_linearl);
        weixinLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aliLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PurchaseActivity", "即将支付宝付款");

                String order_no = subOrderBean.getData().getOrder_id();
                String addr_id = subOrderBean.getData().getAddress().getAddr_id();
                String goods_name = subOrderBean.getData().getGoods().getGoods_name();
                aliPay(order_no, addr_id, goods_name);

            }
        });
//         popupWindow.showAsDropDown(v);
        //位置
        popupWindow.showAtLocation(v, Gravity.CENTER, 300, -300);//0,0 距原点坐标

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_purchase_address_changeoradd_tv:
                Intent intent = new Intent(PurchaseActivity.this, AddressListActivity.class);
                startActivityForResult(intent, 10);
                break;
            case R.id.activity_purchase_sub_order_btn:
                showPopupWindow(v);
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

    @OnClick(R.id.activity_purdetails_iv_close)
    public void onClick() {
        finish();
    }
}
