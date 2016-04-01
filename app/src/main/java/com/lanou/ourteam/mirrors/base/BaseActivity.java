package com.lanou.ourteam.mirrors.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        BaseApplication.addActivity(this);
        setContentView(setContent());

        initView();
        initData();
    }

    // 绑定布局
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
