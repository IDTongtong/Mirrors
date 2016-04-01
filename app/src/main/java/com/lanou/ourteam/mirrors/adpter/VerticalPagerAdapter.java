package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.lanou.ourteam.mirrors.fragment.GoodsListFragment;
import com.lanou.ourteam.mirrors.fragment.MrtjFragment;
import com.lanou.ourteam.mirrors.fragment.StoryFragment;
import com.lanou.ourteam.mirrors.utils.Content;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class VerticalPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<String> info_dataList;

    public VerticalPagerAdapter(FragmentManager fm,Context context,List<String> info_dataList) {
        super(fm);
        this.context = context;
        this.info_dataList = info_dataList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return MrtjFragment.setUrlBodyGetInstance(Content.MRTJ);

            case 1:
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return GoodsListFragment.setUrlBodyGetInstance(Content.GOODS_LIST, "269");

            case 2:
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return GoodsListFragment.setUrlBodyGetInstance(Content.GOODS_LIST, "268");

            case 3:
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return StoryFragment.setUrlBodyGetInstance(Content.STORY_LIST);
            default:
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return null;

        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
