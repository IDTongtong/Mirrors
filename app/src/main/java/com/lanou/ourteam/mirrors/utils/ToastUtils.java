package com.lanou.ourteam.mirrors.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.lanou.ourteam.mirrors.base.BaseApplication;

/**Toast工具类
 * Created by zt on 16/4/1.
 */
public class ToastUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;
    private static Object mObj = new Object();
   private Context content;


    public  ToastUtils(Content content) {
        this.content = BaseApplication.getContext();

    }

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     * @param content
     * @param msg
     */
    public static void showToast(Context content, String msg) {
        showToast(content, msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     * @param context
     * @param msg
     */
    public static void showToastLong(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     * @param context
     * @param msg
     */
    public static void showToast(Context context, int msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     * @param context
     * @param msg
     */
    public static void showToastLong(Context context, int msg) {
        showToast(context, msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息
     * @param context
     * @param msg
     * @param len
     */
    public static void showToast(final Context context, final int msg,
                                 final int len) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (mObj) {
                            if (toast != null) {
                                toast.cancel();
                                toast.setText(msg);
                                toast.setDuration(len);
                            } else {
                                toast = Toast.makeText(context, msg, len);
                            }
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * Toast发送消息

     * @param context
     * @param msg
     * @param len
     */
    public static void showToast(final Context context, final String msg,
                                 final int len) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (mObj) {
                            if (toast != null) {
                                toast.cancel();
                                toast.setText(msg);
                                toast.setDuration(len);
                            } else {
                                toast = Toast.makeText(context, msg, len);
                            }
                            toast.show();
                        }

                    }
                });
            }
        }).start();
    }

    /**
     * 关闭当前Toast
     */
    public static void cancelCurrentToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}



