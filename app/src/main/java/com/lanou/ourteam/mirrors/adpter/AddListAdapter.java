package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.activity.AddressListActivity;
import com.lanou.ourteam.mirrors.bean.AddressListBean;
import com.lanou.ourteam.mirrors.common.customhem.SwipeListView;
import com.lanou.ourteam.mirrors.listenerinterface.AddDelListener;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHDelete on 16/4/12.
 */
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

    }

    public void addData(List<AddressListBean.DataEntity.ListEntity> listEntityList) {
        this.listEntityList = listEntityList;
    }

    @Override
    public int getCount() {
        return listEntityList != null && listEntityList.size()>0 ? listEntityList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listEntityList != null && listEntityList.size()>0 ? listEntityList.get(position) : null;
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


            }
        });
        return convertView;
    }

    class ViewHolder{
        private TextView nameTv,phoneTv,addressTv,deleteTv;
        private ImageView editIv;
    }

}
