package com.lanou.ourteam.mirrors.common.customhem;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
import com.lanou.ourteam.mirrors.bean.MenuBean;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/4/1.
 * 封装的popwindow方法
 */

public class MyPopWindow {
    Context context;
    MainActivity mainActivity;
TextView returnHomeTv;//返回首页
    TextView exitTv;
    public MyPopWindow(Context context) {
        this.context = context;
    }

    //左边的pupwindow
    public void showPopupWindow(View view, final String title) {
        mainActivity = (MainActivity) context;

        // 一个自定义的布局，作为显示的内容 加载popwindow的布局
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.activity_main_pupwindow_listview, null);
        returnHomeTv = (TextView) contentView.findViewById(R.id.acticty_main_popwondow_listview_returnhome);
       exitTv = (TextView) contentView.findViewById(R.id.acticty_main_popwondow_listview_exit);
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
        //返回首页是跳转到购物车


        Map<String, String> params = new HashMap();


        params.put("token", "");

        NetHelper.getInstance().volleyPostTogetNetData(Content.MENU_LIST, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Gson gson = new Gson();
                try {

                    JSONObject jsonObject = new JSONObject(string);
                    final MenuBean menuBean = gson.fromJson(jsonObject.toString(), MenuBean.class);
                    MainActivityPupwindowListviewAdapter activityPupwindowListviewAdapter = new MainActivityPupwindowListviewAdapter(menuBean, context,title);
                    menulistView.setAdapter(activityPupwindowListviewAdapter);
                    //listview的点击事件
                    menulistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            popupWindow.dismiss();

                            mainActivity.getDatafromFragment(position);
                            // imageViewItemiv.setVisibility(View.VISIBLE);


                        }
                    });
                    returnHomeTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            mainActivity.getDatafromFragment(menuBean.getData().getList().size() - 1);
                        }
                    });
                    exitTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            mainActivity.getDatafromFragment(0);
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
