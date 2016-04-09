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
import com.lanou.ourteam.mirrors.adpter.MrtjRvAdapter;
import com.lanou.ourteam.mirrors.base.BaseFragment;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
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
public class MrtjFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private NetHelper netHelper;
    private String url_body;
    private MrtjRvAdapter mAdapter;
    MrtjBean mrtjBean;
    LinearLayout linearLayoutTop;
    TextView textViewtoptv;
    String title;

    public static MrtjFragment setUrlBodyGetInstance(String url_body, String title) {
        MrtjFragment instance = new MrtjFragment();
        Bundle bundle = new Bundle();
        Log.d("GoodsListFragment", "1///" + url_body);
        bundle.putString("url_body", url_body);
        bundle.putString("title", title);

        instance.setArguments(bundle);

        return instance;
    }


    @Override
    protected int setContent() {
        return R.layout.fragment_common_lay;
    }

    @Nullable


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewtoptv = (TextView) view.findViewById(R.id.common_lay_toptv);
        mRecyclerView = bindViewById(view, R.id.common_frag_rc_view);

        //最上边字的点击事件
        linearLayoutTop = (LinearLayout) view.findViewById(R.id.common_lay_yoplayout);
        linearLayoutTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyPopWindow.getPopWindow(getContext()).showPopupWindow(v, title);



            }
        });
        netHelper = NetHelper.getInstance();

        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        Bundle bundle = getArguments();
        url_body = bundle.getString("url_body");
        title = bundle.getString("title");
        //设置上边的title
        textViewtoptv.setText(title);
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new MrtjRvAdapter(context);
        mRecyclerView.setAdapter(mAdapter);


        Map<String, String> params = new HashMap();

        params.put("last_time", "");
        params.put("page", "");
        params.put("token", "");
        params.put("device_type", "3");
        params.put("version", "1.0.0");
        netHelper.volleyPostTogetNetData(url_body, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("MrtjFragment", "----" + string);
                AnalyzeJson ananlyzeJson = AnalyzeJson.getInstance();
                mrtjBean = ananlyzeJson.analyzeMrtj(string);
                List<MrtjBean.DataEntity.ListEntity> listEntityList = mrtjBean.getData().getList();
                mAdapter.addData(listEntityList);
            }

            @Override
            public void onFail(String failStr) {

            }
        });

    }
}