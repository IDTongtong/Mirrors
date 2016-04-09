package com.lanou.ourteam.mirrors.activity;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dllo on 16/3/29.
 */
public class WelcomeActivity extends BaseActivity {
    private ImageView spashIv;

    private String welcomIvUrl;
    NetHelper netHelper = NetHelper.getInstance();
  
    ImageView imageView;

    //安卓os的handler
    Handler handler;


    @Override
    protected int setContent() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {
        String url = "index.php/index/started_img";
        Map<String, String> params = new HashMap();

        params.put("last_time", "");
        params.put("page", "");
        params.put("token", "");
        params.put("device_type", "3");
        params.put("version", "1.0.0");

        netHelper.volleyPostTogetNetData(url, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("WelcomeActivity", "****" + string);
                try {

                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.has("img")) {
                        welcomIvUrl = jsonObject.getString("img");
                        Log.d("WelcomeActivity", "欢迎页图片：" + welcomIvUrl);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                imageView = (ImageView) findViewById(R.id.welcome_iv);

                netHelper.loadImageWithVolley(imageView,welcomIvUrl);
            }

            @Override
            public void onFail(String failStr) {

            }
        });


            //new 一个计时器
            Timer timer = new Timer();
            //tast是计时器的任务
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {

                    finish();
                }
            };
            // 设定计时器的任务以及时间
            timer.schedule(tast, 2000);

    }

    @Override
    protected void initView() {
//        imageView = (ImageView) findViewById(R.id.welcome_iv);
//        ImageLoader imageLoader = netHelper.getImageLoader();
//        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
//                imageView,
//                R.mipmap.ic_launcher,
//                R.mipmap.loading
//
//        );
//        imageLoader.get(welcomIvUrl, imageListener);
//
    }
}
