package com.lanou.ourteam.mirrors.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.ButterKnife;

/**
 * Created by zt on 16/3/29.
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * setContent()绑定布局
     * initData()加入数据
     * initView()创建视图
     * bindView()绑定视图
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //  实现无标题栏（但有系统自带的任务栏）：
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        BaseApplication.addActivity(this);

        //Fresco初始化:相关
        Fresco.initialize(this);
        setContentView(setContent());
        ButterKnife.inject(this);
        initView();
        initData();
    }

    /**
     * 设置布局文件
     * @return layout布局文件id
     */
    protected abstract int setContent();

    // 加入数据
    protected abstract void initData();

    // 创建视图
    protected abstract void initView();

    // 绑定视图
    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }


    //页面跳转
    public void jumpToActivity(Context context, Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    //带有返回值的页面跳转
    public void jumpToActivityForResult(Context context, Class<?> targetActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        BaseApplication.removeActivity(this);
        super.onDestroy();
    }
}
