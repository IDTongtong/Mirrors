package com.lanou.ourteam.mirrors.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.activity.MainActivity;
import com.lanou.ourteam.mirrors.adpter.MainActivityPupwindowListviewAdapter;
import com.lanou.ourteam.mirrors.adpter.MainActivityRecycleViewAdapter;
import com.lanou.ourteam.mirrors.base.BaseApplication;
import com.lanou.ourteam.mirrors.base.BaseFragment;
import com.lanou.ourteam.mirrors.bean.MenuBean;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/3/30.
 */
public class MainActivityRecycleViewFragemt extends BaseFragment {
    RecyclerView recyclerView;
    MainActivityRecycleViewAdapter activityRecycleViewAdapter;
    ArrayList<String> data;
    TextView textViewTab;
    private MainActivity mainActivity;

    //当fragment和activity被关联时调用。
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获得他绑定的activity
        mainActivity = (MainActivity) context;
    }

    @Override
    protected int setContent() {
        return R.layout.activity_main_recycleview_fragment;
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
        //显示popwindow的
        textViewTab = (TextView) getView().findViewById(R.id.mainactivity_recyclerview_tabtext);
        textViewTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });
    }

    //左边的pupwindow
    private void showPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容 加载popwindow的布局
        View contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.activity_main_pupwindow_listview, null);
//初始化listview
        final ListView menulistView = (ListView) contentView.findViewById(R.id.acticty_main_popwondow_listview);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, 1500, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Log.i("mengdd", "onTouch : ");
                popupWindow.dismiss();
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //给pupwindow设置动画
        popupWindow.setAnimationStyle(R.style.AnimationPreview);

        popupWindow.showAtLocation(view, Gravity.getAbsoluteGravity(0, 0), 0, 0);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug

        // 设置好参数之后再show

        popupWindow.showAsDropDown(view);
        View listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_pupwindow_listview_item,null);
        TextView textViewItemiv = (TextView) listItemView.findViewById(R.id.acticty_main_popwondow_listview_tv);
        final ImageView imageViewItemiv = (ImageView) listItemView.findViewById(R.id.popwindow_all_line_iv);
         //listview的点击事件

        Map<String, String> params = new HashMap();


        params.put("token", "");

        NetHelper.getInstance().volleyPostTogetNetData(Content.MENU_LIST, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Gson gson = new Gson();
                Log.d("ssssMainActivityRecycleView", string);
                try {

                    JSONObject jsonObject = new JSONObject(string);
                    final MenuBean menuBean = gson.fromJson(jsonObject.toString(), MenuBean.class);
                    MainActivityPupwindowListviewAdapter activityPupwindowListviewAdapter = new MainActivityPupwindowListviewAdapter(menuBean, getContext(),null);
                    menulistView.setAdapter(activityPupwindowListviewAdapter);
                    menulistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            popupWindow.dismiss();
                            mainActivity.getDatafromFragment(position);
                            // imageViewItemiv.setVisibility(View.VISIBLE);


                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("qqq", "===" + string);
            }

            @Override
            public void onFail(String failStr) {
                Log.d("MainActivityRecycleView", "===" + "Fail");
            }
        });


    }

}
