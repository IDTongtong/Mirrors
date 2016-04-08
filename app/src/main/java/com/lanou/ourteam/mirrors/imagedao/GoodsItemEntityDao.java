package com.lanou.ourteam.mirrors.imagedao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.lanou.ourteam.mirrors.imagedao.GoodsItemEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GOODS_ITEM_ENTITY".
*/
public class GoodsItemEntityDao extends AbstractDao<GoodsItemEntity, Long> {

    public static final String TABLENAME = "GOODS_ITEM_ENTITY";

    /**
     * Properties of entity GoodsItemEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Type = new Property(1, String.class, "type", false, "TYPE");
        public final static Property Goods_img = new Property(2, String.class, "goods_img", false, "GOODS_IMG");
        public final static Property Goods_name = new Property(3, String.class, "goods_name", false, "GOODS_NAME");
        public final static Property Goods_price = new Property(4, String.class, "goods_price", false, "GOODS_PRICE");
        public final static Property Produce_area = new Property(5, String.class, "produce_area", false, "PRODUCE_AREA");
        public final static Property Brand = new Property(6, String.class, "brand", false, "BRAND");
    };


    public GoodsItemEntityDao(DaoConfig config) {
        super(config);
    }
    
    public GoodsItemEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GOODS_ITEM_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TYPE\" TEXT," + // 1: type
                "\"GOODS_IMG\" TEXT," + // 2: goods_img
                "\"GOODS_NAME\" TEXT," + // 3: goods_name
                "\"GOODS_PRICE\" TEXT," + // 4: goods_price
                "\"PRODUCE_AREA\" TEXT," + // 5: produce_area
                "\"BRAND\" TEXT);"); // 6: brand
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GOODS_ITEM_ENTITY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, GoodsItemEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(2, type);
        }
 
        String goods_img = entity.getGoods_img();
        if (goods_img != null) {
            stmt.bindString(3, goods_img);
        }
 
        String goods_name = entity.getGoods_name();
        if (goods_name != null) {
            stmt.bindString(4, goods_name);
        }
 
        String goods_price = entity.getGoods_price();
        if (goods_price != null) {
            stmt.bindString(5, goods_price);
        }
 
        String produce_area = entity.getProduce_area();
        if (produce_area != null) {
            stmt.bindString(6, produce_area);
        }
 
        String brand = entity.getBrand();
        if (brand != null) {
            stmt.bindString(7, brand);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public GoodsItemEntity readEntity(Cursor cursor, int offset) {
        GoodsItemEntity entity = new GoodsItemEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // type
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // goods_img
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // goods_name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // goods_price
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // produce_area
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // brand
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, GoodsItemEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setType(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGoods_img(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGoods_name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGoods_price(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setProduce_area(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBrand(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(GoodsItemEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(GoodsItemEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
