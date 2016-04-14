package com.lanou.ourteam.mirrors.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.ourteam.mirrors.R;

import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.AddressListBean;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.common.SwipeListView;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHDelete on 16/4/11.
 */
public class AddressListActivity extends BaseActivity {
    private static final int LOGINCODE = 20;
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
                startActivityForResult(intent, 2);
            }
        });
        mSwipeListView = (SwipeListView) findViewById(R.id.activity_addresslist_swipelistview);


        netHelper = NetHelper.getInstance();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ggdddAddressListActivity", "resultCode:" + resultCode);

        if (resultCode == REGISTER || resultCode == 20) {
            Log.d("sssAddressListActivity", "jinlaile");
            Map<String, String> params = new HashMap();
            params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
            params.put("device_type", "3");

            netHelper.volleyPostTogetNetData(Content.ADDRESS_LIST, params, new VolleyNetListener() {
                @Override
                public void onSuccess(String string) {

                    AnalyzeJson analyzeJson = AnalyzeJson.getInstance();
                    addressListBean = analyzeJson.analyzeAddressList(string);

                    Log.d("AddressListActivity", "第一条地址:  " + addressListBean.getData().getList().get(0).getAddr_info());
                    mListAdapter = new AddListAdapter(AddressListActivity.this, addressListBean.getData().getList(), mSwipeListView);
                    mSwipeListView.setAdapter(mListAdapter);

                    //mListAdapter.addData(addressListBean.getData().getList());

                }

                @Override
                public void onFail(String failStr) {
                    Log.d("PurchaseActivity", "   " + failStr);
                }
            });
        }

    }

    @Override
    protected void initData() {
        Map<String, String> params = new HashMap();
        params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
        params.put("device_type", "3");
        final AnalyzeJson analyzeJson = AnalyzeJson.getInstance();

        netHelper.volleyPostTogetNetData(Content.ADDRESS_LIST, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {

                addressListBean = analyzeJson.analyzeAddressList(string);

                mListAdapter = new AddListAdapter(AddressListActivity.this, addressListBean.getData().getList(), mSwipeListView);
                mSwipeListView.setAdapter(mListAdapter);


            }

            @Override
            public void onFail(String failStr) {
                Log.d("PurchaseActivity", "   " + failStr);
            }
        });
//编辑默认地址
        mSwipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Map<String, String> params = new HashMap();
                params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
                params.put("device_type", "3");

                params.put("addr_id", addressListBean.getData().getList().get(position).getAddr_id());
                NetHelper.getInstance().volleyPostTogetNetData("index.php/user/mr_address", params, new VolleyNetListener() {
                    @Override
                    public void onSuccess(String string) {
                        Intent intent = new Intent();
                        intent.putExtra("phone", addressListBean.getData().getList().get(position).getCellphone());
                        intent.putExtra("address", addressListBean.getData().getList().get(position).getAddr_info());
                        intent.putExtra("username", addressListBean.getData().getList().get(position).getUsername());
                        setResult(100, intent);
                        finish(); // 不打的话没有跳转
                    }

                    @Override
                    public void onFail(String failStr) {

                    }
                });
            }
        });
    }


//*******内部适配器

    public class AddListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<AddressListBean.DataEntity.ListEntity> listEntityList;
        private SwipeListView mListView;
        private NetHelper netHelper;


        public AddListAdapter(Context context,
                              List<AddressListBean.DataEntity.ListEntity> listEntityList,
                              SwipeListView mListView) {
            this.context = context;
            this.listEntityList = listEntityList;
            this.mListView = mListView;

            inflater = LayoutInflater.from(context);
            netHelper = NetHelper.getInstance();
            notifyDataSetChanged();
        }

        public void addData(List<AddressListBean.DataEntity.ListEntity> listEntityList) {
            this.listEntityList = listEntityList;
        }

        @Override
        public int getCount() {
            return listEntityList != null && listEntityList.size() > 0 ? listEntityList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return listEntityList != null && listEntityList.size() > 0 ? listEntityList.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void deleteAddOverService(String addr_id) {
            Map<String, String> params = new HashMap();
            params.put("token", "0065d70d336ea6d38a5c11412d7b19a4");
            params.put("addr_id", addr_id);
            netHelper.volleyPostTogetNetData(Content.DEL_ADDRESS, params, new VolleyNetListener() {
                @Override
                public void onSuccess(String string) {
                    Log.d("AddressListActivity", "删除地址返回数据:***" + string);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(string);
                        if (jsonObject.has("result")) {
                            String result = jsonObject.getString("result");

                            Log.d("AddressListActivity", "删除地址:result   " + result);

                            if (result.equals("1")) {
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();

                            } else if (result.equals("0")) {
                                String msg = jsonObject.getString("msg");
                                Log.d("AddressListActivity", "错误信息:" + msg);
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.address_list_item_view, parent, false);

                viewHolder.nameTv = (TextView) convertView.findViewById(R.id.add_item_reciever_tv);
                viewHolder.phoneTv = (TextView) convertView.findViewById(R.id.add_item_phone_tv);
                viewHolder.addressTv = (TextView) convertView.findViewById(R.id.add_item_add_tv);
                viewHolder.editIv = (ImageView) convertView.findViewById(R.id.add_item_edit_iv);
                viewHolder.deleteTv = (TextView) convertView.findViewById(R.id.add_item_delete_tv);
                viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.add_item_reciever_linlayout);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.nameTv.setText(listEntityList.get(position).getUsername());
            viewHolder.phoneTv.setText(listEntityList.get(position).getCellphone());
            viewHolder.addressTv.setText(listEntityList.get(position).getAddr_info());

            viewHolder.deleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String addr_id = listEntityList.get(position).getAddr_id();
                    Log.d("AddListAdapter", "addr_id***" + addr_id);
                    listEntityList.remove(position);
                    notifyDataSetChanged();
                    deleteAddOverService(addr_id);
                    mListView.turnToNormal();
              //删除时候 还需要传个默认地址
                    Intent intent = new Intent();
                    intent.putExtra("phone", addressListBean.getData().getList().get(addressListBean.getData().getList().size()-1).getCellphone());
                    intent.putExtra("address", addressListBean.getData().getList().get(addressListBean.getData().getList().size()-1).getAddr_info());
                    intent.putExtra("username", addressListBean.getData().getList().get(addressListBean.getData().getList().size()-1).getUsername());
                    setResult(100, intent);
                }
            });
            viewHolder.editIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddressEditActivity.class);
                    intent.putExtra("addr_id", listEntityList.get(position).getAddr_id());
                    intent.putExtra("username", listEntityList.get(position).getUsername());
                    intent.putExtra("cellphone", listEntityList.get(position).getCellphone());
                    intent.putExtra("addrinfo", listEntityList.get(position).getAddr_info());

                    startActivityForResult(intent, 1);


                }
            });

            return convertView;
        }

        class ViewHolder {
            private TextView nameTv, phoneTv, addressTv, deleteTv;
            private ImageView editIv;
            private LinearLayout linearLayout;
        }

    }


}