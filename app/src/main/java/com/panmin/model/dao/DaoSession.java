package com.panmin.model.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.panmin.model.TaskCall;
import com.panmin.model.TableFather;
import com.panmin.model.TableSon;

import com.panmin.model.dao.TaskCallDao;
import com.panmin.model.dao.TableFatherDao;
import com.panmin.model.dao.TableSonDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig taskCallDaoConfig;
    private final DaoConfig tableFatherDaoConfig;
    private final DaoConfig tableSonDaoConfig;

    private final TaskCallDao taskCallDao;
    private final TableFatherDao tableFatherDao;
    private final TableSonDao tableSonDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        taskCallDaoConfig = daoConfigMap.get(TaskCallDao.class).clone();
        taskCallDaoConfig.initIdentityScope(type);

        tableFatherDaoConfig = daoConfigMap.get(TableFatherDao.class).clone();
        tableFatherDaoConfig.initIdentityScope(type);

        tableSonDaoConfig = daoConfigMap.get(TableSonDao.class).clone();
        tableSonDaoConfig.initIdentityScope(type);

        taskCallDao = new TaskCallDao(taskCallDaoConfig, this);
        tableFatherDao = new TableFatherDao(tableFatherDaoConfig, this);
        tableSonDao = new TableSonDao(tableSonDaoConfig, this);

        registerDao(TaskCall.class, taskCallDao);
        registerDao(TableFather.class, tableFatherDao);
        registerDao(TableSon.class, tableSonDao);
    }
    
    public void clear() {
        taskCallDaoConfig.getIdentityScope().clear();
        tableFatherDaoConfig.getIdentityScope().clear();
        tableSonDaoConfig.getIdentityScope().clear();
    }

    public TaskCallDao getTaskCallDao() {
        return taskCallDao;
    }

    public TableFatherDao getTableFatherDao() {
        return tableFatherDao;
    }

    public TableSonDao getTableSonDao() {
        return tableSonDao;
    }

}
