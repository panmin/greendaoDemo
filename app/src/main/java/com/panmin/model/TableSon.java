package com.panmin.model;

import org.greenrobot.greendao.annotation.*;

import com.panmin.model.dao.DaoSession;
import org.greenrobot.greendao.DaoException;

import com.panmin.model.dao.TableFatherDao;
import com.panmin.model.dao.TableSonDao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "TableSon".
 */
@Entity(active = true, nameInDb = "TableSon")
public class TableSon {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "son")
    private String son;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient TableSonDao myDao;

    @ToOne(joinProperty = "id")
    private TableFather father;

    @Generated
    private transient Long father__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public TableSon() {
    }

    public TableSon(Long id) {
        this.id = id;
    }

    @Generated
    public TableSon(Long id, String son) {
        this.id = id;
        this.son = son;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTableSonDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
        this.son = son;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public TableFather getFather() {
        Long __key = this.id;
        if (father__resolvedKey == null || !father__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TableFatherDao targetDao = daoSession.getTableFatherDao();
            TableFather fatherNew = targetDao.load(__key);
            synchronized (this) {
                father = fatherNew;
            	father__resolvedKey = __key;
            }
        }
        return father;
    }

    @Generated
    public void setFather(TableFather father) {
        synchronized (this) {
            this.father = father;
            id = father == null ? null : father.getId();
            father__resolvedKey = id;
        }
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
