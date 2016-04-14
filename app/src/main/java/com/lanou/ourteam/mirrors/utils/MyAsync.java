package com.lanou.ourteam.mirrors.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dllo on 16/4/11.
 */
public class MyAsync extends AsyncTask<String, Integer, Bitmap> {
    private ProgressDialog dialog;
    private Context context;
    private ImageView iv;

    public void setiv(ImageView iv) {
        this.iv = iv;
    }


    /**
     * params 后台执行任务，类似于线程的run方法
     * Java的类型 意思是参数个数不定
     * 声明异步任务时的第三个参数类型
     * @param params
     * @return
     */

    @Override
    protected Bitmap doInBackground(String... params) {
        String path = params[0];
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL url = new URL(path);
            try {
                connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    is = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public MyAsync(Context context) {
        this.context = context;
    }

    /**
     * 异步任务 AsyncTask
     * 泛型有三个参数
     * 1.参与任务的参数类型 如string类型URL
     * 2.后台执行任务的返回进度类型
     * 3.执行任务的返回结果类型
     * 有写类型 没有时Void
     * 作为泛型来使用 基本数据使用包装类Inter，Long
     * 准备工作的方法 在后台任务启动前 相当于run方法启动前
     * 在此方法做一些准备工作
     */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    /**
     * 更新进度
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.e("xxx", String.valueOf(values[0]++));
    }


    /**
     * 发布后台任务执行结果
     * 后台任务执行完毕调用 类似于线程run方法执行完毕
     *
     * @param bitmap
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        //使用后台任务获取的Bitmap
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
        }
    }
}
