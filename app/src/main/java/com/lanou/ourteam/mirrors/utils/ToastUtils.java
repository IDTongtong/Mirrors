package com.lanou.ourteam.mirrors.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Toast工具类
 * Created by zt on 16/4/1.
 */
public class ToastUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;
    private static Object mObj = new Object();

    /**
     *Toast发送消息 , 默认Toast.LENGTH_SHORT
     * @param content
     * @param msg
     */

    public static void showMessage(final Content content,final String msg){
        showMessage(content,msg,Toast.LENGTH_SHORT);

    }

    private static void showMessage(Content content, String msg, int lengthShort) {

   
    }


}
