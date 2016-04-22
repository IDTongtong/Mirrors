package com.lanou.ourteam.mirrors.fragment;


import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;

import android.widget.FrameLayout;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.activity.MainActivity;
import com.lanou.ourteam.mirrors.adpter.MainActivityPupwindowListviewAdapter;
import com.lanou.ourteam.mirrors.base.BaseFragment;
import com.lanou.ourteam.mirrors.imagedao.DaoEntityHelper;
import com.lanou.ourteam.mirrors.imagedao.MenuItemEntity;
import com.lanou.ourteam.mirrors.listenerinterface.PoisitionListener;

import java.util.List;

/**
 * Created by dllo on 16/4/11.
 */
public class MenuFragment extends BaseFragment {
    RelativeLayout menuFragmentLayout;
    MainActivity mainActivity;
    TextView returnHomeTv;//返回首页
    TextView exitTv;
    String title;
    ListView menulistView;
    PoisitionListener poisitionListener;
//*********
    //数据库

    List<MenuItemEntity> menuItemEntityList;
    private DaoEntityHelper daoEntityHelper;

    //*******
    @Override
    protected int setContent() {

        return R.layout.fragment_menu;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        menulistView = (ListView) getView().findViewById(R.id.acticty_main_popwondow_listview);
        menuFragmentLayout = (RelativeLayout) getView().findViewById(R.id.menuLayout);
        returnHomeTv = (TextView) getView().findViewById(R.id.acticty_main_popwondow_listview_returnhome);
        exitTv = (TextView) getView().findViewById(R.id.acticty_main_popwondow_listview_exit);
//初始化listview
        Bundle bundle = getArguments();
        title = bundle.getString("menutitle");
        initiView();
    }

    private void initiView() {

        mainActivity = (MainActivity) getContext();
        daoEntityHelper = DaoEntityHelper.getInstance();
        // 一个自定义的布局，作为显示的内容 加载popwindow的布局
        menuItemEntityList = daoEntityHelper.queryMenu();


        MainActivityPupwindowListviewAdapter activityPupwindowListviewAdapter = new MainActivityPupwindowListviewAdapter(menuItemEntityList, getContext(), title);
        menulistView.setAdapter(activityPupwindowListviewAdapter);
        //listview的点击事件
        menulistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poisitionListener.getPoisition(position);
                //mainActivity.getDatafromFragment(position);
                mainActivity.dissMenu();
                // imageViewItemiv.setVisibility(View.VISIBLE);


            }
        });

        menuFragmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.dissMenu();
            }
        });
        returnHomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.dissMenu();
                poisitionListener.getPoisition(menuItemEntityList.size() - 1);
            }
        });
        exitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.dissMenu();
                poisitionListener.getPoisition(0);
            }
        });
    }

    public void getPositionListenner(PoisitionListener poisitionListener) {
        this.poisitionListener = poisitionListener;
    }
}
