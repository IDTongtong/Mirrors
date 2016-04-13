package com.lanou.ourteam.mirrors.activity;

import android.util.Log;
import android.widget.Toast;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.AddListAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.AddressListBean;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.common.customhem.SwipeListView;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZHDelete on 16/4/11.
 */
public class AddressListActivity extends BaseActivity  {

    private NetHelper netHelper;
    private AddressListBean addressListBean;

    private SwipeListView mSwipeListView;
    private AddListAdapter mListAdapter;

    @Override
    protected int setContent() {
        return R.layout.activity_address_list_lay;
    }

    @Override
    protected void initView() {

        mSwipeListView = (SwipeListView) findViewById(R.id.activity_addresslist_swipelistview);
        mListAdapter = new AddListAdapter(this);
        mSwipeListView.setAdapter(mListAdapter);

        netHelper = NetHelper.getInstance();

        Map<String, String> params = new HashMap();
        params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
        params.put("device_type", "3");

        netHelper.volleyPostTogetNetData(Content.ADDRESS_LIST, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {

                AnalyzeJson analyzeJson = AnalyzeJson.getInstance();
                addressListBean = analyzeJson.analyzeAddressList(string);
                Log.d("AddressListActivity", "第一条地址:  " + addressListBean.getData().getList().get(0).getAddr_info());
                mListAdapter.addData(addressListBean.getData().getList());

            }

            @Override
            public void onFail(String failStr) {
                Log.d("PurchaseActivity", "   " + failStr);
            }
        });

    }

    @Override
    protected void initData() {

    }

//    @Override
//    public void onAddItemDelListener(String addr_id, int position) {
//        Log.d("AddressListActivity", "addr_id:" + addr_id);
//
//        Map<String, String> params = new HashMap();
//        params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
//        params.put("addr_id", addr_id);
//        netHelper.volleyPostTogetNetData(Content.DEL_ADDRESS, params, new VolleyNetListener() {
//            @Override
//            public void onSuccess(String string) {
//                Log.d("AddressListActivity", "删除地址返回数据:***" + string);
//                try {
//                    JSONObject jsonObject = new JSONObject(string);
//                    if (jsonObject.has("result")) {
//                        String result = jsonObject.getString("result");
//                        Log.d("AddressListActivity", "删除地址:result   " + result);
//                        if (result.equals("1")) {
//                            Toast.makeText(AddressListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
//
//
//
//                            //再次请求一下收货地址列表:::
//                            Map<String, String> params_1 = new HashMap();
//                            params_1.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
//                            params_1.put("device_type", "3");
//
//                            netHelper.volleyPostTogetNetData(Content.ADDRESS_LIST, params_1, new VolleyNetListener() {
//                                @Override
//                                public void onSuccess(String string) {
//                                    AnalyzeJson analyzeJson = AnalyzeJson.getInstance();
//                                    addressListBean = analyzeJson.analyzeAddressList(string);
//                                    Log.d("AddressListActivity", "第一条地址:  " + addressListBean.getData().getList().get(0).getAddr_info());
//                                    mAddRvAdapter.addData(addressListBean.getData().getList());
//                                }
//
//                                @Override
//                                public void onFail(String failStr) {
//
//                                }
//                            });
//
//
//                        } else if (result.equals("0")) {
//                            String msg = jsonObject.getString("msg");
//                            Log.d("AddressListActivity", "错误信息:" + msg);
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFail(String failStr) {
//
//            }
//        });
//
//
//    }
}
