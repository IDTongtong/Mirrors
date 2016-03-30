package com.lanou.ourteam.mirrors.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by zt on 16/3/29.
 */
public class BaseApplication extends Application {
//    获取context对象
    private static Context context;
    private static ArrayList<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
    context = this;

    }
    public static Context getContext(){
        return context;
    }

    public static void addActivity(Activity activity) {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    public static void finishAllActivity() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }



}
