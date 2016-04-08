package com.lanou.ourteam.mirrors.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.WearTheAtlasRvAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.WearBean;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zt on 16/4/7.
 */
public class WearTheAtlasActivity extends BaseActivity {

    @InjectView(R.id.weartheatlas_recyclerView)
    RecyclerView weartheatlasRecyclerView;
    private RecyclerView recyclerView;
    private ArrayList<WearBean> data;
    private WearTheAtlasRvAdapter atlasRvAdapter;


    @Override
    protected int setContent() {
        return R.layout.activity_weartheatlas;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        atlasRvAdapter = new WearTheAtlasRvAdapter();
        weartheatlasRecyclerView.setAdapter(atlasRvAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        lm.setOrientation(LinearLayoutManager.VERTICAL);

        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new WearBean(R.mipmap.ic_launcher));

        }

        atlasRvAdapter.addData(data, this);
    }



}
