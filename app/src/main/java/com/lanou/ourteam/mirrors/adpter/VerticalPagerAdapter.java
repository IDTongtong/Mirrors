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
import com.lanou.ourteam.mirrors.imagedao.MenuItemEntity;
import com.lanou.ourteam.mirrors.utils.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class VerticalPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    //private List<String> info_dataList;
    private MenuBean menuBean;
    private List<MenuItemEntity> menuItemEntityList;

    public VerticalPagerAdapter(FragmentManager fm, Context context,List<MenuItemEntity> menuItemEntityList) {
        super(fm);
        Log.d("VerticalPagerAdapter", "构造构造构造");
        this.context = context;
        this.menuItemEntityList = menuItemEntityList;
        //this.info_dataList = info_dataList;
    }


    @Override
    public Fragment getItem(int position) {
        switch (menuItemEntityList.get(position).getType()) {
            case "6":
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return MrtjFragment.setUrlBodyGetInstance(Content.MRTJ, menuItemEntityList.get(position).getTitle());

            case "3":
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return GoodsListFragment.setUrlBodyGetInstance(Content.GOODS_LIST, menuItemEntityList.get(position).getInfo_data(),  menuItemEntityList.get(position).getTitle());


            case "4":
                //!!!!等待进一步解析：
                //接口里 又改为了 购物车
                return ShopCarFragment.setUrlBodyGetInstance( menuItemEntityList.get(position).getTitle());
            case "2":
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return StoryFragment.setUrlBodyGetInstance(Content.STORY_LIST,  menuItemEntityList.get(position).getTitle());
//            case 5:
//
////                return MrtjFragment.setUrlBodyGetInstance(Content.MRTJ, menuBean.getData().getList().get(position).getTitle());
//                return MrtjFragment.setUrlBodyGetInstance(Content.MRTJ,  menuItemEntityList.get(position).getTitle());
            default:
                Log.d("VerticalPagerIIAdapter", "viewpager位置:" + position);
                return null;

        }


    }

    @Override
    public int getCount() {
        return  menuItemEntityList.size();

    }
}
