package com.lanou.ourteam.mirrors.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zt on 16/3/29.
 */
public abstract class BaseFragment extends Fragment {

    public View rootView;//根视图

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //根据根视图创建视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == rootView) {
            return onViewInit(inflater, container, savedInstanceState);
        } else {
            return rootView;
        }
    }


    public abstract View onViewInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    //根据根视图销毁视图
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != rootView) {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (null != viewGroup) {
                viewGroup.removeView(rootView);
            }
        }
    }
}
