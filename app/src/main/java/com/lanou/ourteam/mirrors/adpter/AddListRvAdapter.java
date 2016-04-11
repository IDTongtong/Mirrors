package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseRecyclerAdapter;
import com.lanou.ourteam.mirrors.bean.AddressListBean;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/11.
 */
public class AddListRvAdapter extends BaseRecyclerAdapter<AddressListBean.DataEntity.ListEntity> {


    public AddListRvAdapter(Context context) {
        super(context);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemVIew = inflater.inflate(R.layout.address_list_item_view, parent, false);
        return new AddViewHolder(itemVIew);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddViewHolder addViewHolder = (AddViewHolder) holder;
        AddressListBean.DataEntity.ListEntity listEntity = list.get(position);
        addViewHolder.receiverTv.setText(listEntity.getUsername());
        addViewHolder.phoneNumTv.setText(listEntity.getCellphone());
        addViewHolder.addTv.setText(listEntity.getAddr_info());
        addViewHolder.editIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转至 收货地址编辑 activity
                Log.d("AddListRvAdapter", "跳转至 收货地址编辑 activity");
            }
        });

    }

    @Override
    public int getItemCount() {
        return isLength()?list.size():0;
    }

    class AddViewHolder extends RecyclerView.ViewHolder {
        private TextView receiverTv,addTv, phoneNumTv;
        private ImageView editIv;

        public AddViewHolder(View itemView) {
            super(itemView);

            receiverTv = (TextView) itemView.findViewById(R.id.add_item_reciever_tv);
            addTv = (TextView) itemView.findViewById(R.id.add_item_add_tv);
            phoneNumTv = (TextView) itemView.findViewById(R.id.add_item_phone_tv);
            editIv = (ImageView) itemView.findViewById(R.id.add_item_edit_iv);
        }
    }
}
