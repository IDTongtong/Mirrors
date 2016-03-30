package com.lanou.ourteam.mirrors.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanou.ourteam.mirrors.R;

import java.util.zip.Inflater;

/**
 * Created by dllo on 16/3/30.
 */
public class MainActivityRecycleViewFragemt extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_recycleview_fragment,null);
    }
}
