package com.lanou.ourteam.mirrors.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by zt on 16/3/29.
 */
public abstract class BaseFragment extends Fragment {


    protected Context context;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ButterKnife.inject(this, inflater.inflate(setContent(), container, false));
        return inflater.inflate(setContent(), container, false);

    }


    protected <T extends View> T bindViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    protected abstract int setContent();
    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.inject(getActivity());
    }

}
