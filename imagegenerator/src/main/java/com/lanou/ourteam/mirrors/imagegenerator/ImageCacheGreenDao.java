package com.lanou.ourteam.mirrors.imagegenerator;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ImageCacheGreenDao {
    public static void main(String args[]) {
        Schema schema = new Schema(1, "com.lanou.ourteam.mirrors.imagedao");

        Entity mrtjItemEntity = schema.addEntity("MrtjItemEntity");

        mrtjItemEntity.addIdProperty().primaryKey().autoincrement();
        mrtjItemEntity.addStringProperty("goods_img");
        mrtjItemEntity.addStringProperty("goods_name");
        mrtjItemEntity.addStringProperty("goods_price");
        mrtjItemEntity.addStringProperty("produce_area");
        mrtjItemEntity.addStringProperty("brand");


        Entity goodsItemEntity = schema.addEntity("GoodsItemEntity");

        goodsItemEntity.addIdProperty().primaryKey().autoincrement();
        goodsItemEntity.addStringProperty("type");
        goodsItemEntity.addStringProperty("goods_img");
        goodsItemEntity.addStringProperty("goods_name");
        goodsItemEntity.addStringProperty("produce_area");
        goodsItemEntity.addStringProperty("brand");

        Entity storyItemEntity = schema.addEntity("StoryItemEntity");

        storyItemEntity.addIdProperty().primaryKey().autoincrement();
        storyItemEntity.addStringProperty("story_img");
        storyItemEntity.addStringProperty("story_title");


        Entity menuItemEntity = schema.addEntity("MenuItemEntity");

        menuItemEntity.addIdProperty().primaryKey().autoincrement();
        menuItemEntity.addStringProperty("title");
        menuItemEntity.addStringProperty("topColor");
        menuItemEntity.addStringProperty("buttomColor");
        menuItemEntity.addStringProperty("type");
        menuItemEntity.addStringProperty("info_data");
        menuItemEntity.addStringProperty("store");


        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
