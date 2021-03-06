package com.example.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.example.greenBean.DistrictBean;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table DISTRICT_BEAN.
*/
public class DistrictBeanDao extends AbstractDao<DistrictBean, String> {

    public static final String TABLENAME = "DISTRICT_BEAN";

    /**
     * Properties of entity DistrictBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property AreaCode = new Property(0, String.class, "AreaCode", true, "AREA_CODE");
        public final static Property AreaName = new Property(1, String.class, "AreaName", false, "AREA_NAME");
        public final static Property ParentID = new Property(2, String.class, "ParentID", false, "PARENT_ID");
    };


    public DistrictBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DistrictBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DISTRICT_BEAN' (" + //
                "'AREA_CODE' TEXT PRIMARY KEY NOT NULL ," + // 0: AreaCode
                "'AREA_NAME' TEXT," + // 1: AreaName
                "'PARENT_ID' TEXT);"); // 2: ParentID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DISTRICT_BEAN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DistrictBean entity) {
        stmt.clearBindings();
 
        String AreaCode = entity.getAreaCode();
        if (AreaCode != null) {
            stmt.bindString(1, AreaCode);
        }
 
        String AreaName = entity.getAreaName();
        if (AreaName != null) {
            stmt.bindString(2, AreaName);
        }
 
        String ParentID = entity.getParentID();
        if (ParentID != null) {
            stmt.bindString(3, ParentID);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public DistrictBean readEntity(Cursor cursor, int offset) {
        DistrictBean entity = new DistrictBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // AreaCode
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // AreaName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // ParentID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DistrictBean entity, int offset) {
        entity.setAreaCode(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAreaName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setParentID(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(DistrictBean entity, long rowId) {
        return entity.getAreaCode();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(DistrictBean entity) {
        if(entity != null) {
            return entity.getAreaCode();
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
