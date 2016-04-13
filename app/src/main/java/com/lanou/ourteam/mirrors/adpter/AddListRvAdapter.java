package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.activity.AddressEditActivity;
import com.lanou.ourteam.mirrors.base.BaseRecyclerAdapter;
import com.lanou.ourteam.mirrors.bean.AddressListBean;
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
 * Created by ZHDelete on 16/4/11.
 */
public class AddListRvAdapter extends BaseRecyclerAdapter<AddressListBean.DataEntity.ListEntity> {

    private NetHelper netHelper;
    private AddDelListener addDelListener;





    public AddListRvAdapter(Context context) {
        super(context);
    }

    public void setOnAddDelListener(AddDelListener addDelListener) {
        this.addDelListener = addDelListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemVIew = inflater.inflate(R.layout.address_list_item_view, parent, false);
        return new AddViewHolder(itemVIew);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AddViewHolder addViewHolder = (AddViewHolder) holder;
        AddressListBean.DataEntity.ListEntity listEntity = list.get(position);
        addViewHolder.receiverTv.setText(listEntity.getUsername());
        addViewHolder.phoneNumTv.setText(listEntity.getCellphone());
        addViewHolder.addTv.setText(listEntity.getAddr_info());

        final String addr_id = listEntity.getAddr_id();



        addViewHolder.holder_position = position;
        addViewHolder.addr_id = listEntity.getAddr_id();

        Log.d("AddListRvAdapter", "地址id:   " + addr_id);

        addViewHolder.editIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转至 收货地址编辑 activity
                Log.d("AddListRvAdapter", "跳转至 收货地址编辑 activity");
                Intent intent = new Intent(context, AddressEditActivity.class);

                intent.putExtra("addr_id", addr_id);
                context.startActivity(intent);


            }
        });

//        addViewHolder.deleteTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO 跳转至 删除本条收货地址
//                addDelListener.onAddItemDelListener(addr_id, position);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return isLength() ? list.size() : 0;
    }

    class AddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int holder_position;
        private String addr_id;

        private RelativeLayout delRl;
        private TextView receiverTv, addTv, phoneNumTv, deleteTv;
        private ImageView editIv;

        public AddViewHolder(View itemView) {
            super(itemView);

            receiverTv = (TextView) itemView.findViewById(R.id.add_item_reciever_tv);
            addTv = (TextView) itemView.findViewById(R.id.add_item_add_tv);
            phoneNumTv = (TextView) itemView.findViewById(R.id.add_item_phone_tv);
            editIv = (ImageView) itemView.findViewById(R.id.add_item_edit_iv);
            deleteTv = (TextView) itemView.findViewById(R.id.add_item_delete_tv);

//            delRl = (RelativeLayout) itemView.findViewById(R.id.add_item_del_rl);

        }

        @Override
        public void onClick(View v) {
            //TODO 跳转至 删除本条收货地址
            Log.d("AddViewHolder", "addr_id holder" + addr_id);
            //addDelListener.onAddItemDelListener(addr_id, holder_position);
            list.remove(holder_position);
            notifyDataSetChanged();
        }
    }
}
