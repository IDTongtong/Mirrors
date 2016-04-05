package com.lanou.ourteam.mirrors.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseFragment;
import com.lanou.ourteam.mirrors.common.customhem.MyPopWindow;

/**
 * Created by dllo on 16/4/5.
 */
public class ShopCarFragment extends BaseFragment {
    TextView shopCarTv;
    @Override
    protected int setContent() {
        return R.layout.myshopcar_fragment;
    }

    public static ShopCarFragment setUrlBodyGetInstance(String title) {
        ShopCarFragment instance = new ShopCarFragment();
        Bundle bundle = new Bundle();

        bundle.putString("stopcartitle", title);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final MyPopWindow popWindow = new MyPopWindow(getContext());
        shopCarTv = (TextView) getActivity().findViewById(R.id.mycar_tv);
        Bundle bundle = getArguments();
        final String tilte = bundle.getString("stopcartitle");
        shopCarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
popWindow.showPopupWindow(v,tilte);
            }
        });

    }
}
