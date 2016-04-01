package com.lanou.ourteam.mirrors.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.StoryRvAdapter;
import com.lanou.ourteam.mirrors.base.BaseFragment;
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.bean.StoryBean;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class StoryFragment extends BaseFragment{


    private RecyclerView mRecyclerView;
    private NetHelper netHelper;
    private String url_body;
    private StoryRvAdapter mAdapter;
    StoryBean storyBean;


    public static StoryFragment setUrlBodyGetInstance(String url_body) {
        StoryFragment instance = new StoryFragment();
        Bundle bundle = new Bundle();
        Log.d("StoryFragment", "1///" + url_body);
        bundle.putString("url_body", url_body);
        instance.setArguments(bundle);
        return instance;
    }



    @Override
    protected int setContent() {
        return R.layout.fragment_common_lay;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = bindViewById(view, R.id.common_frag_rc_view);
        netHelper = NetHelper.getInstance();
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);


        Bundle bundle = getArguments();
        url_body = bundle.getString("url_body");
        Log.d("MrtjFragment", "***   " + url_body);


        mRecyclerView.setLayoutManager(lm);
        mAdapter = new StoryRvAdapter(context);
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
                Log.d("MrtjFragment", "----"+string);
                AnalyzeJson ananlyzeJson = new AnalyzeJson();
                storyBean = ananlyzeJson.AnalyzeStory(string);
                List<StoryBean.DataEntity.ListEntity> listEntityList = storyBean.getData().getList();
                mAdapter.addData(listEntityList);
            }

            @Override
            public void onFail(String failStr) {

            }
        });

    }
}
