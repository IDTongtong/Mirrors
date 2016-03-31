package com.lanou.ourteam.mirrors.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lanou.ourteam.mirrors.base.BaseApplication;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZHDelete on 16/3/29.
 */
public class NetHelper {

    private static NetHelper netHelperInstance;
    private RequestQueue requestQueue;
    private String diskPath;
    private ImageLoader imageLoader;
    private Context context;

    private NetHelper() {
        Log.d("NetHelper", "5");
        requestQueue = Volley.newRequestQueue(BaseApplication.getContext());
        this.context = BaseApplication.getContext();
        initImageLoader();
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



    public void volleyPostTogetNetData(String url_body, final Map<String, String> params, final VolleyNetListener netListener) {
        Log.d("NetHelper", "11");

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, Content.URL_HEAD + url_body,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        netListener.onSuccess(response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        netListener.onFail("请求失败");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void okHttpPost(String url_body,RequestBody requestBody ,Callback responseCallback) {

        Request request = new Request.Builder()
                .url(Content.URL_HEAD + url_body)
                .post(requestBody)
                .build();

        OkHttpTools.enqueue(request, responseCallback);
    }

    private void initImageLoader() {
        //定义硬盘图片缓存的根路径
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            diskPath = file.getAbsolutePath();

        } else {
            File file = BaseApplication.getContext().getFilesDir();
            diskPath = file.getAbsolutePath();
        }
        File file = new File(diskPath + "/img");
        if (!file.exists()) {
            file.mkdir();
        }
        diskPath = file.getAbsolutePath();
        Log.i("android", diskPath);

        imageLoader = new ImageLoader(requestQueue, new DoubleCache());
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public class MemoryCache implements
            ImageLoader.ImageCache {
        private LruCache<String, Bitmap> cache;

        public MemoryCache() {
            long maxSize = Runtime.getRuntime().maxMemory() / 1024;

            int cacheSize = (int) (maxSize / 4);

            cache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() *
                            value.getHeight() / 1024;
                }
            };

        }

        @Override
        public Bitmap getBitmap(String url) {
            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
        }
    }

    public class DiskCache implements
            ImageLoader.ImageCache {

        @Override
        public Bitmap getBitmap(String url) {
            //获取url中的图片名称
            String fileName = url.substring(
                    url.lastIndexOf("/") + 1,
                    url.length()
            );
            //用文件名拼接出实际文件存储路径
            String filePath = diskPath + "/" + fileName;

            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            return bitmap;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            //获取url中的图片名称
            String fileName = url.substring(
                    url.lastIndexOf("/") + 1,
                    url.length()
            );
            //用文件名拼接出实际文件存储路径
            String filePath = diskPath + "/" + fileName;

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(filePath);
                //将bitmap对象写入文件中
                bitmap.compress(Bitmap.CompressFormat.PNG,
                        100, fos);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                //关闭文件流
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }

    public class DoubleCache implements
            ImageLoader.ImageCache {
        private MemoryCache memoryCache;
        private DiskCache diskCache;

        public DoubleCache() {
            memoryCache = new MemoryCache();
            diskCache = new DiskCache();
        }

        @Override
        public Bitmap getBitmap(String url) {
            Bitmap bitmap = memoryCache.getBitmap(url);
            if (bitmap == null)
                bitmap = diskCache.getBitmap(url);
            return bitmap;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            memoryCache.putBitmap(url, bitmap);
            diskCache.putBitmap(url, bitmap);
        }
    }


}
