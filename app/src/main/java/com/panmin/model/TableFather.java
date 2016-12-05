package com.panmin.model;

import org.greenrobot.greendao.annotation.*;

import java.util.List;
import com.panmin.model.dao.DaoSession;
import org.greenrobot.greendao.DaoException;

import com.panmin.model.dao.TableFatherDao;
import com.panmin.model.dao.TableSonDao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "TableFather".
 */
@Entity(active = true, nameInDb = "TableFather")
public class TableFather {

    @Id(autoincrement = true)
    private Long id;
    private String father;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient TableFatherDao myDao;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "fatherId")
    })
    private List<TableSon> fatherList;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public TableFather() {
    }

    public TableFather(Long id) {
        this.id = id;
    }

    @Generated
    public TableFather(Long id, String father) {
        this.id = id;
        this.father = father;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTableFatherDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<TableSon> getFatherList() {
        if (fatherList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TableSonDao targetDao = daoSession.getTableSonDao();
            List<TableSon> fatherListNew = targetDao._queryTableFather_FatherList(id);
            synchronized (this) {
                if(fatherList == null) {
                    fatherList = fatherListNew;
                }
            }
        }
        return fatherList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetFatherList() {
        fatherList = null;
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
