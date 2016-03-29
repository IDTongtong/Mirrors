package com.lanou.ourteam.mirrors.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by zt on 16/3/29.
 */
public class BaseApplication extends Application {
//    获取context对象
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
    context = this;

    }
    public static Context getContext(){
        return context;
    }

}
