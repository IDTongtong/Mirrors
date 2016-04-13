package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
    private static final int LOGINCODE = 0;
    private static final int REGISTER = 10;
    private NetHelper netHelper;
    private AddressListBean addressListBean;
    private SwipeListView mSwipeListView;
    private AddListAdapter mListAdapter;
private TextView addAdresstv;
    @Override
    protected int setContent() {
        return R.layout.activity_address_list_lay;
    }

    @Override
    protected void initView() {
        addAdresstv = (TextView) findViewById(R.id.activity_addresslist_addadresstv);
        addAdresstv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, AddressAddActivity.class);
                startActivityForResult(intent, LOGINCODE);
            }
        });
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


}
