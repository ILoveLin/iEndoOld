package org.company.iendo.my.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import org.company.iendo.mineui.beandb.AddServersBeanDB;

import org.company.iendo.my.db.AddServersBeanDBDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig addServersBeanDBDaoConfig;

    private final AddServersBeanDBDao addServersBeanDBDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        addServersBeanDBDaoConfig = daoConfigMap.get(AddServersBeanDBDao.class).clone();
        addServersBeanDBDaoConfig.initIdentityScope(type);

        addServersBeanDBDao = new AddServersBeanDBDao(addServersBeanDBDaoConfig, this);

        registerDao(AddServersBeanDB.class, addServersBeanDBDao);
    }
    
    public void clear() {
        addServersBeanDBDaoConfig.clearIdentityScope();
    }

    public AddServersBeanDBDao getAddServersBeanDBDao() {
        return addServersBeanDBDao;
    }

}