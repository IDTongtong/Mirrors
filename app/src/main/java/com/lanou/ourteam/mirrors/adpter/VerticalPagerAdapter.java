package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.lanou.ourteam.mirrors.bean.MenuBean;
import com.lanou.ourteam.mirrors.fragment.GoodsListFragment;
import com.lanou.ourteam.mirrors.fragment.MrtjFragment;
import com.lanou.ourteam.mirrors.fragment.ShopCarFragment;
import com.lanou.ourteam.mirrors.fragment.StoryFragment;
import com.lanou.ourteam.mirrors.utils.Content;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class VerticalPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<String> info_dataList;
private MenuBean menuBean;
    public VerticalPagerAdapter(FragmentManager fm,Context context,List<String> info_dataList,MenuBean menuBean) {
        super(fm);
        this.context = context;
        this.info_dataList = info_dataList;
      this.menuBean = menuBean;
//        Log.d("sssVerticalPagerAdapter", title);

    }

    @Override
    public Fragment getItem(int position) {
        switch (menuBean.getData().getList().get(position).getStore()) {
            case "10":
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return MrtjFragment.setUrlBodyGetInstance(Content.MRTJ, menuBean.getData().getList().get(position).getTitle());

            case "9":
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return GoodsListFragment.setUrlBodyGetInstance(Content.GOODS_LIST, "269",menuBean.getData().getList().get(position).getTitle());

            case "8":
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return GoodsListFragment.setUrlBodyGetInstance(Content.GOODS_LIST, "268",menuBean.getData().getList().get(position).getTitle());

            case "7":
                    //!!!!等待进一步解析：
                return MrtjFragment.setUrlBodyGetInstance(Content.MRTJ,menuBean.getData().getList().get(position).getTitle());
            case "6":
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return StoryFragment.setUrlBodyGetInstance(Content.STORY_LIST,menuBean.getData().getList().get(position).getTitle());
           case "5":

               return ShopCarFragment.setUrlBodyGetInstance(menuBean.getData().getList().get(position).getTitle());
            default:
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return null;

        }
    }

    @Override
    public int getCount() {
        return menuBean.getData().getList().size();
    }
}
