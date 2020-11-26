package org.company.iendo.my.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import org.company.iendo.bean.beandb.UserDBBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_DBBEAN".
*/
public class UserDBBeanDao extends AbstractDao<UserDBBean, Long> {

    public static final String TABLENAME = "USER_DBBEAN";

    /**
     * Properties of entity UserDBBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Username = new Property(1, String.class, "username", false, "USERNAME");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property UserType = new Property(3, String.class, "userType", false, "USER_TYPE");
        public final static Property Tag = new Property(4, String.class, "tag", false, "TAG");
    }


    public UserDBBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserDBBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_DBBEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USERNAME\" TEXT UNIQUE ," + // 1: username
                "\"PASSWORD\" TEXT," + // 2: password
                "\"USER_TYPE\" TEXT," + // 3: userType
                "\"TAG\" TEXT);"); // 4: tag
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_DBBEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserDBBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String userType = entity.getUserType();
        if (userType != null) {
            stmt.bindString(4, userType);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(5, tag);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserDBBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String userType = entity.getUserType();
        if (userType != null) {
            stmt.bindString(4, userType);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(5, tag);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserDBBean readEntity(Cursor cursor, int offset) {
        UserDBBean entity = new UserDBBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // username
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userType
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // tag
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserDBBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTag(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserDBBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserDBBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserDBBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
