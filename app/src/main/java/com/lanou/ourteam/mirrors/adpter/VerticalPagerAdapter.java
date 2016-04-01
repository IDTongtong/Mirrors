package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanou.ourteam.mirrors.fragment.MrtjFragment;
import com.lanou.ourteam.mirrors.utils.Content;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class VerticalPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragmentList;

    public VerticalPagerAdapter(FragmentManager fm,Context context,List<Fragment> fragmentList) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;

    }

    @Override
    public Fragment getItem(int position) {
        return MrtjFragment.setUrlBodyGetInstance(Content.MRTJ);
        //return GoodsListFragment.setUrlBodyGetInstance(Content.GOODS_LIST);
        //return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
