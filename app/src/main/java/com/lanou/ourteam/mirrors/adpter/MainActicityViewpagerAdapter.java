package com.lanou.ourteam.mirrors.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by zt on 16/3/29.
 */
public class MainActicityViewpagerAdapter extends FragmentPagerAdapter {
ArrayList<Fragment> datas;


    public MainActicityViewpagerAdapter(FragmentManager fm,ArrayList<Fragment> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
