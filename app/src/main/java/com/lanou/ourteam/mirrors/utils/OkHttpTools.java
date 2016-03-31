package com.lanou.ourteam.mirrors.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.concurrent.TimeUnit;
import com.squareup.okhttp.Callback;

/**
 * Created by ZHDelete on 16/3/30.
 */
public class OkHttpTools {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    static{
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    public static void enqueue(Request request, Callback responseCallback) {
        mOkHttpClient.newCall(request)
                .enqueue(responseCallback);
    }
}
