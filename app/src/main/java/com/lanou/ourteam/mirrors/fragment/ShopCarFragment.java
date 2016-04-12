package com.lanou.ourteam.mirrors.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.activity.MainActivity;
import com.lanou.ourteam.mirrors.base.BaseFragment;


/**
 * Created by dllo on 16/4/5.
 */
public class ShopCarFragment extends BaseFragment {
    private TextView shopCarTv;
   private MainActivity mainActivity;
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
      //  final MyPopWindow popWindow = new MyPopWindow(getContext());
        mainActivity = (MainActivity) context;
        shopCarTv = (TextView) getActivity().findViewById(R.id.mycar_tv);
        Bundle bundle = getArguments();

        shopCarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                String title = bundle.getString("stopcartitle");
               // MyPopWindow.getPopWindow(getContext()).showPopupWindow(v, tilte);
                mainActivity.showMenu(title);

            }
        });

    }
}
