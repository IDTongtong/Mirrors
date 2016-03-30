package com.zh.b.section.double_mirror.util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.zh.b.section.double_mirror.base.BaseApplication;
import com.zh.b.section.double_mirror.listenerinterface.NetListener;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZHDelete on 16/3/29.
 */
public class NetHelper {

    private static NetHelper netHelperInstance;
    private RequestQueue requestQueue;

    private NetHelper() {
        Log.d("NetHelper", "5");
        requestQueue = Volley.newRequestQueue(BaseApplication.getMyAppContext());
        Log.d("NetHelper", "6");
        //netHelperInstance = new NetHelper();
    }

    public static NetHelper getInstance() {
        Log.d("NetHelper", "1");
        if (netHelperInstance == null) {
            Log.d("NetHelper", "2");
            synchronized (NetHelper.class) {
                if (netHelperInstance == null) {
                    Log.d("NetHelper", "3");
                    netHelperInstance = new NetHelper();
                    Log.d("NetHelper", "4");
                }
            }
        }
        return netHelperInstance;
    }

//    public void volleyPostTogetNetData(String url_body, final Map<String, String> params, final NetListener netListener) {
//        Log.d("NetHelper", "11");
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Content.URL_HEAD + url_body,
//                null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("NetHelper", "22");
//                netListener.onSuccess(response);
//                Log.d("NetHelper", "33");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                netListener.onFail("拉取失败");
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                //Log.d("NetHelper", "**" + params.get("phone number"));
//                Map<String, String> par = new HashMap<>();
//                par.put("phone number", "17071590613");
//                return par;
//            }
//        };
//        requestQueue.add(jsonObjectRequest);
//    }

    public void okHttpPost(String url_body,RequestBody requestBody ,Callback responseCallback) {

        Request request = new Request.Builder()
                .url(Content.URL_HEAD + url_body)
                .post(requestBody)
                .build();

        OkHttpTools.enqueue(request, responseCallback);
    }


}
