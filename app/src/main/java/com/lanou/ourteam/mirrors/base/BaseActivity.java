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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  实现无标题栏（但有系统自带的任务栏）：
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 加上activity
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

    /**
     * 加入数据
     */
    protected abstract void initData();

    /**
     * 创建视图
     */
    protected abstract void initView();

    /**
     * 绑定视图
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 页面跳转
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public void jumpToActivity(Context context, Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 带有返回值的页面跳转
     * @param context
     * @param targetActivity 目标activity
     * @param requestCode 请求码
     * @param bundle
     */
    public void jumpToActivityForResult(Context context, Class<?> targetActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 移除所有activity
     */
    @Override
    protected void onDestroy() {
        BaseApplication.removeActivity(this);
        super.onDestroy();
    }
}
