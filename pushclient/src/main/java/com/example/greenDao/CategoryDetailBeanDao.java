package com.example.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.example.greenBean.CategoryDetailBean;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table CATEGORY_DETAIL_BEAN.
*/
public class CategoryDetailBeanDao extends AbstractDao<CategoryDetailBean, String> {

    public static final String TABLENAME = "CATEGORY_DETAIL_BEAN";

    /**
     * Properties of entity CategoryDetailBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property CategoryID = new Property(0, String.class, "CategoryID", true, "CATEGORY_ID");
        public final static Property CategoryDes = new Property(1, String.class, "CategoryDes", false, "CATEGORY_DES");
        public final static Property CategoryName = new Property(2, String.class, "CategoryName", false, "CATEGORY_NAME");
        public final static Property Img = new Property(3, String.class, "Img", false, "IMG");
        public final static Property IsShow = new Property(4, String.class, "IsShow", false, "IS_SHOW");
        public final static Property Orders = new Property(5, String.class, "Orders", false, "ORDERS");
        public final static Property ParentID = new Property(6, String.class, "ParentID", false, "PARENT_ID");
        public final static Property RootID = new Property(7, String.class, "rootID", false, "ROOT_ID");
        public final static Property SortOrder = new Property(8, String.class, "SortOrder", false, "SORT_ORDER");
    };


    public CategoryDetailBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CategoryDetailBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CATEGORY_DETAIL_BEAN' (" + //
                "'CATEGORY_ID' TEXT PRIMARY KEY NOT NULL ," + // 0: CategoryID
                "'CATEGORY_DES' TEXT," + // 1: CategoryDes
                "'CATEGORY_NAME' TEXT," + // 2: CategoryName
                "'IMG' TEXT," + // 3: Img
                "'IS_SHOW' TEXT," + // 4: IsShow
                "'ORDERS' TEXT," + // 5: Orders
                "'PARENT_ID' TEXT," + // 6: ParentID
                "'ROOT_ID' TEXT," + // 7: rootID
                "'SORT_ORDER' TEXT);"); // 8: SortOrder
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CATEGORY_DETAIL_BEAN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CategoryDetailBean entity) {
        stmt.clearBindings();
 
        String CategoryID = entity.getCategoryID();
        if (CategoryID != null) {
            stmt.bindString(1, CategoryID);
        }
 
        String CategoryDes = entity.getCategoryDes();
        if (CategoryDes != null) {
            stmt.bindString(2, CategoryDes);
        }
 
        String CategoryName = entity.getCategoryName();
        if (CategoryName != null) {
            stmt.bindString(3, CategoryName);
        }
 
        String Img = entity.getImg();
        if (Img != null) {
            stmt.bindString(4, Img);
        }
 
        String IsShow = entity.getIsShow();
        if (IsShow != null) {
            stmt.bindString(5, IsShow);
        }
 
        String Orders = entity.getOrders();
        if (Orders != null) {
            stmt.bindString(6, Orders);
        }
 
        String ParentID = entity.getParentID();
        if (ParentID != null) {
            stmt.bindString(7, ParentID);
        }
 
        String rootID = entity.getRootID();
        if (rootID != null) {
            stmt.bindString(8, rootID);
        }
 
        String SortOrder = entity.getSortOrder();
        if (SortOrder != null) {
            stmt.bindString(9, SortOrder);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CategoryDetailBean readEntity(Cursor cursor, int offset) {
        CategoryDetailBean entity = new CategoryDetailBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // CategoryID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // CategoryDes
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // CategoryName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Img
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // IsShow
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Orders
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ParentID
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // rootID
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // SortOrder
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CategoryDetailBean entity, int offset) {
        entity.setCategoryID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCategoryDes(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCategoryName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImg(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIsShow(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOrders(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setParentID(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRootID(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSortOrder(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(CategoryDetailBean entity, long rowId) {
        return entity.getCategoryID();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(CategoryDetailBean entity) {
        if(entity != null) {
            return entity.getCategoryID();
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
