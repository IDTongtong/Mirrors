package com.lanou.ourteam.mirrors.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.GoodsListRvAdapter;
import com.lanou.ourteam.mirrors.base.BaseFragment;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.bean.GoodsListAllBean;
import com.lanou.ourteam.mirrors.bean.MrtjBean;
import com.lanou.ourteam.mirrors.common.customhem.MyPopWindow;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class GoodsListFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private NetHelper netHelper;
    private GoodsListRvAdapter mAdapter;
    //Only the original thread that created a view hierarchy can touch its views.
String title;
    LinearLayout linearLayoutTop;
    TextView textViewToptv;
    GoodsListAllBean goodsListAllBean;
    MrtjBean mrtjBean;
    private String url_body;
    private String category_id;

    public static GoodsListFragment setUrlBodyGetInstance(String url_body, String category_id,String title) {
        GoodsListFragment instance = new GoodsListFragment();
        Bundle bundle = new Bundle();
        Log.d("GoodsListFragment", "1///" + url_body);
        bundle.putString("url_body", url_body);
        bundle.putString("category_id", category_id);
        bundle.putString("goodtitle",title);
        instance.setArguments(bundle);

        return instance;
    }


    @Override
    protected int setContent() {
        return R.layout.fragment_common_lay;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.common_frag_rc_view);
      //  final MyPopWindow myPopWindow = new MyPopWindow(getContext());
        //最上边字的点击事件
        linearLayoutTop = (LinearLayout) view.findViewById(R.id.common_lay_yoplayout);
        linearLayoutTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyPopWindow.getPopWindow(getContext()).showPopupWindow(v,title);

            //    myPopWindow.showPopupWindow(v,title);


            }
        });
        textViewToptv = (TextView) view.findViewById(R.id.common_lay_toptv);
        intiview();

    }

    public void intiview() {

        netHelper = NetHelper.getInstance();

        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(lm);
        mAdapter = new GoodsListRvAdapter(context);
        mRecyclerView.setAdapter(mAdapter);

        Bundle bundle = getArguments();
        url_body = bundle.getString("url_body");
        Log.d("GoodsListFragment", "***" + url_body);
        category_id = bundle.getString("category_id");
title = bundle.getString("goodtitle");

        Map<String, String> params = new HashMap();

        params.put("last_time", "");
        params.put("page", "");
        params.put("token", "");
        params.put("category_id", category_id);
        params.put("device_type", "3");
        params.put("version", "1.0.0");

        netHelper.volleyPostTogetNetData(url_body, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("GoodsListFragment", "voley得到数据:" + string);
                AnalyzeJson ananlyzeJson = new AnalyzeJson();
                goodsListAllBean = ananlyzeJson.analyzeGoodsList(string);
                Log.d("GoodsListFragment", "***" + goodsListAllBean.getData().getList().get(0).getGoods_img());
                mAdapter.initData(goodsListAllBean);
                textViewToptv.setText(title);
            }

            @Override
            public void onFail(String failStr) {

            }
        });

    }
}
