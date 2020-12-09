package org.company.iendo.my.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import java.util.List;
import org.company.iendo.bean.beandb.image.DimConverter;
import org.company.iendo.bean.beandb.image.ReallyConverter;

import org.company.iendo.bean.beandb.image.ImageListDownDBBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "IMAGE_LIST_DOWN_DBBEAN".
*/
public class ImageListDownDBBeanDao extends AbstractDao<ImageListDownDBBean, Long> {

    public static final String TABLENAME = "IMAGE_LIST_DOWN_DBBEAN";

    /**
     * Properties of entity ImageListDownDBBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ItemID = new Property(1, String.class, "itemID", false, "ITEM_ID");
        public final static Property Tag = new Property(2, String.class, "tag", false, "TAG");
        public final static Property DownTag = new Property(3, String.class, "downTag", false, "DOWN_TAG");
        public final static Property MDimImageList = new Property(4, String.class, "mDimImageList", false, "M_DIM_IMAGE_LIST");
        public final static Property MReallyImageList = new Property(5, String.class, "mReallyImageList", false, "M_REALLY_IMAGE_LIST");
    }

    private final DimConverter mDimImageListConverter = new DimConverter();
    private final ReallyConverter mReallyImageListConverter = new ReallyConverter();

    public ImageListDownDBBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ImageListDownDBBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"IMAGE_LIST_DOWN_DBBEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ITEM_ID\" TEXT UNIQUE ," + // 1: itemID
                "\"TAG\" TEXT," + // 2: tag
                "\"DOWN_TAG\" TEXT," + // 3: downTag
                "\"M_DIM_IMAGE_LIST\" TEXT," + // 4: mDimImageList
                "\"M_REALLY_IMAGE_LIST\" TEXT);"); // 5: mReallyImageList
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"IMAGE_LIST_DOWN_DBBEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ImageListDownDBBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String itemID = entity.getItemID();
        if (itemID != null) {
            stmt.bindString(2, itemID);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(3, tag);
        }
 
        String downTag = entity.getDownTag();
        if (downTag != null) {
            stmt.bindString(4, downTag);
        }
 
        List mDimImageList = entity.getMDimImageList();
        if (mDimImageList != null) {
            stmt.bindString(5, mDimImageListConverter.convertToDatabaseValue(mDimImageList));
        }
 
        List mReallyImageList = entity.getMReallyImageList();
        if (mReallyImageList != null) {
            stmt.bindString(6, mReallyImageListConverter.convertToDatabaseValue(mReallyImageList));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ImageListDownDBBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String itemID = entity.getItemID();
        if (itemID != null) {
            stmt.bindString(2, itemID);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(3, tag);
        }
 
        String downTag = entity.getDownTag();
        if (downTag != null) {
            stmt.bindString(4, downTag);
        }
 
        List mDimImageList = entity.getMDimImageList();
        if (mDimImageList != null) {
            stmt.bindString(5, mDimImageListConverter.convertToDatabaseValue(mDimImageList));
        }
 
        List mReallyImageList = entity.getMReallyImageList();
        if (mReallyImageList != null) {
            stmt.bindString(6, mReallyImageListConverter.convertToDatabaseValue(mReallyImageList));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ImageListDownDBBean readEntity(Cursor cursor, int offset) {
        ImageListDownDBBean entity = new ImageListDownDBBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // itemID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tag
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // downTag
            cursor.isNull(offset + 4) ? null : mDimImageListConverter.convertToEntityProperty(cursor.getString(offset + 4)), // mDimImageList
            cursor.isNull(offset + 5) ? null : mReallyImageListConverter.convertToEntityProperty(cursor.getString(offset + 5)) // mReallyImageList
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ImageListDownDBBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setItemID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTag(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDownTag(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMDimImageList(cursor.isNull(offset + 4) ? null : mDimImageListConverter.convertToEntityProperty(cursor.getString(offset + 4)));
        entity.setMReallyImageList(cursor.isNull(offset + 5) ? null : mReallyImageListConverter.convertToEntityProperty(cursor.getString(offset + 5)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ImageListDownDBBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ImageListDownDBBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ImageListDownDBBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
