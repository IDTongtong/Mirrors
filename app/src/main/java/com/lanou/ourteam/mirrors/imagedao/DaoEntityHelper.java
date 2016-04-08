package com.lanou.ourteam.mirrors.imagedao;



import android.content.Context;
import android.util.Log;

import com.lanou.ourteam.mirrors.base.BaseApplication;

import java.util.List;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by ZHDelete on 16/4/8.
 */
public class DaoEntityHelper {
    private Context context;
    private static final String DATABASE_NAME = "ImageTextCache.db";
    private volatile static DaoEntityHelper instance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DaoEntityHelper() {
        context = BaseApplication.getContext();


        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static DaoEntityHelper getInstance() {
        if (instance == null) {
            synchronized (DaoEntityHelper.class) {
                if (instance == null) {
                    instance = new DaoEntityHelper();
                }
            }
        }
        return instance;
    }

    public long insert(MenuItemEntity menuItemEntity) {
        return daoSession.getMenuItemEntityDao().insert(menuItemEntity);
    }

    public void insertMenuList(List<MenuItemEntity> menuItemEntityList) {
        daoSession.getMenuItemEntityDao().insertInTx(menuItemEntityList);

    }


    public long insert(MrtjItemEntity mrtjItemEntity) {
        return daoSession.getMrtjItemEntityDao().insert(mrtjItemEntity);

    }

    public void insetMrtjList(List<MrtjItemEntity> mrtjItemEntityList) {
        daoSession.getMrtjItemEntityDao().insertInTx(mrtjItemEntityList);

    }

    public long insert(GoodsItemEntity goodsItemEntity) {
        return daoSession.getGoodsItemEntityDao().insert(goodsItemEntity);
    }

    public void insertGoodsList(List<GoodsItemEntity> goodsItemEntityList) {
        daoSession.getGoodsItemEntityDao().insertInTx(goodsItemEntityList);

    }

    public long insert(StoryItemEntity storyItemEntity) {
        return daoSession.getStoryItemEntityDao().insert(storyItemEntity);
    }

    public void insertStortyList(List<StoryItemEntity> storyItemEntityList) {
        daoSession.getStoryItemEntityDao().insertInTx(storyItemEntityList);

    }

    public void deleteMenuByKey(long position) {
        daoSession.getMenuItemEntityDao().deleteByKey(position);

    }
    public void deleteMenuAll() {
        daoSession.getMenuItemEntityDao().deleteAll();
    }


    public void deleteMrtjByKey(long position) {
        daoSession.getMrtjItemEntityDao().deleteByKey(position);

    }

    public void deleteMrtjAll() {
        daoSession.getMrtjItemEntityDao().deleteAll();
    }

    public void deleteGoodsByKey(long position) {
        daoSession.getGoodsItemEntityDao().deleteByKey(position);

    }
    public void deleteGoodsAll() {
        daoSession.getGoodsItemEntityDao().deleteAll();
    }
    public void deleteStoryByKey(long position) {
        daoSession.getStoryItemEntityDao().deleteByKey(position);
    }

    public void deleteStoryAll() {
        daoSession.getStoryItemEntityDao().deleteAll();
    }

    public List<MenuItemEntity> queryMenu() {
        List<MenuItemEntity> menuItemEntityList = daoSession.getMenuItemEntityDao().
                queryBuilder().list();
        return menuItemEntityList;
    }

    public List<MrtjItemEntity> queryMrtj() {
        List<MrtjItemEntity> mrtjItemEntityList = daoSession.getMrtjItemEntityDao().
                queryBuilder().list();
        return mrtjItemEntityList;
    }

    public List<GoodsItemEntity> queryGoods() {
//        List<PersonEntity> queryList = personDao.queryBuilder().list();
//        queryList = personDao.queryBuilder().listLazy();
//        queryList = personDao.loadAll();

        List<GoodsItemEntity> goodsItemEntityList = daoSession.getGoodsItemEntityDao().
                queryBuilder().list();
        return goodsItemEntityList;
    }

    public List<StoryItemEntity> queryStory() {
        List<StoryItemEntity> storyItemEntityList = daoSession.getStoryItemEntityDao().
                queryBuilder().list();
        return storyItemEntityList;
    }

    public boolean queryMrtjItemByUrl(MrtjItemEntity mrtjItemEntity) {
        QueryBuilder qb = daoSession.getMrtjItemEntityDao().queryBuilder();
        qb.where(MrtjItemEntityDao.Properties.Goods_img.eq(mrtjItemEntity.getGoods_img()));
        List<MrtjItemEntity> mrtjItemEntityList = qb.list();
        for (int i = 0; i < mrtjItemEntityList.size(); i++) {
            Log.d("DaoEntityHelper", "DaoEntiryHelper---query_mrtj_url" + mrtjItemEntityList.get(i).getGoods_img());

        }
        if (mrtjItemEntityList.size() > 0) {
            return true;
        } else {
            return false;
        }

    }
    public boolean queryGoodsItemByUrl(GoodsItemEntity goodsItemEntity) {
        QueryBuilder qb = daoSession.getGoodsItemEntityDao().queryBuilder();
        qb.where(GoodsItemEntityDao.Properties.Goods_img.eq(goodsItemEntity.getGoods_img()));
        List<GoodsItemEntity> goodsItemEntityList = qb.list();
        for (int i = 0; i < goodsItemEntityList.size(); i++) {

            Log.d("DaoEntityHelper", "DaoEntiryHelper---query_goods_url" + goodsItemEntityList.get(i).getGoods_img());
        }

        if (goodsItemEntityList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<GoodsItemEntity> queryGoodsByTpye(String type) {
        QueryBuilder qb = daoSession.getGoodsItemEntityDao().queryBuilder();
        qb.where(GoodsItemEntityDao.Properties.Type.eq(type));
        List<GoodsItemEntity> goodsItemEntityList = qb.list();
        for (int i = 0; i < goodsItemEntityList.size(); i++) {
            Log.d("DaoEntityHelper", "DaoEntiryHelper---query_goods_by_type_url" + goodsItemEntityList.get(i).getGoods_img());

        }
        return goodsItemEntityList;

    }
    public boolean queryStoryItemByUrl(StoryItemEntity storyItemEntity) {
        QueryBuilder qb = daoSession.getStoryItemEntityDao().queryBuilder();
        qb.where(StoryItemEntityDao.Properties.Story_img.eq(storyItemEntity.getStory_img()));
        List<StoryItemEntity> storyItemEntityList = qb.list();
        for (int i = 0; i < storyItemEntityList.size(); i++) {
            Log.d("DaoEntityHelper", "DaoEntiryHelper---query_story_url" + storyItemEntityList.get(i).getStory_img());
        }
        if (storyItemEntityList.size() > 0) {
            return true;
        } else {
            return false;
        }

    }



}
