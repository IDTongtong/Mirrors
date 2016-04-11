package com.lanou.ourteam.mirrors.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.AddListRvAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.AddressListBean;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZHDelete on 16/4/11.
 */
public class AddressListActivity extends BaseActivity {

    private NetHelper netHelper;
    private AddressListBean addressListBean;
    private RecyclerView mRecyclerView;
    private AddListRvAdapter mAddRvAdapter;

    @Override
    protected int setContent() {
        return R.layout.activity_address_list_lay;
    }
    @Override
    protected void initView() {
        mAddRvAdapter = new AddListRvAdapter(this);
        mRecyclerView = bindView(R.id.activity_address_list_recyclerview);
        mRecyclerView.setAdapter(mAddRvAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);

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
                mAddRvAdapter.addData(addressListBean.getData().getList());

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
