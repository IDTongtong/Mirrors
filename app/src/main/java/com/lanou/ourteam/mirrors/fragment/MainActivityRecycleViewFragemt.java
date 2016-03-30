package com.lanou.ourteam.mirrors.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.MainActivityRecycleViewAdapter;
import com.lanou.ourteam.mirrors.base.BaseApplication;
import com.lanou.ourteam.mirrors.base.BaseFragment;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by dllo on 16/3/30.
 */
public class MainActivityRecycleViewFragemt extends BaseFragment {
    RecyclerView recyclerView;
    MainActivityRecycleViewAdapter activityRecycleViewAdapter;
    ArrayList<String> data;

    @Override
    public View onViewInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_recycleview_fragment, null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initiView();
    }

    private void initiView() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.mainactivity_recyclerview);
        data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("666");
        }
        activityRecycleViewAdapter = new MainActivityRecycleViewAdapter(data);
        GridLayoutManager gm = new GridLayoutManager(BaseApplication.getContext(), 1);
        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gm);
     recyclerView.setAdapter(activityRecycleViewAdapter);
    }


}
