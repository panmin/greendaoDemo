package com.panmin.greendaodemo;

import android.content.Context;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panmin on 16-12-5.
 * 统一实现SQLite的CRUD
 */

public class DBManager<T,K> {
    private Context mContext;
    /**
     * CRUD核心类
     */
    private AbstractDao<T, K> mDao;
    public DBManager(Context context,Class<T> classOfT) {
        mContext = context;
        AbstractDao dao = DBController.getInstance(mContext).getDaoSession().getDao(classOfT);
        mDao = dao;
    }


    public void save(T item) {
        mDao.insert(item);
    }

    public void save(T... items) {
        mDao.insertInTx(items);
    }

    public void save(List<T> items) {
        mDao.insertInTx(items);
    }

    public void saveOrUpdate(T item) {
        mDao.insertOrReplace(item);
    }

    public void saveOrUpdate(T... items) {
        mDao.insertOrReplaceInTx(items);
    }

    public void saveOrUpdate(List<T> items) {
        mDao.insertOrReplaceInTx(items);
    }

    public void deleteByKey(K key) {
        mDao.deleteByKey(key);
    }

    public void delete(T item) {
        mDao.delete(item);
    }

    public void delete(T... items) {
        mDao.deleteInTx(items);
    }

    public void delete(List<T> items) {
        mDao.deleteInTx(items);
    }

    public void deleteAll() {
        mDao.deleteAll();
    }

    public void update(T item) {
        mDao.update(item);
    }

    public void update(T... items) {
        mDao.updateInTx(items);
    }

    public void update(List<T> items) {
        mDao.updateInTx(items);
    }

    public  T query(K key) {
        return  mDao.load(key);
    }

    public List<T> queryAll() {
        List<T> ts = mDao.loadAll();
        if (ts != null && ts.size() != 0) {
            return ts;
        } else {
            return new ArrayList<>();
        }
    }

    public List<T> query(String where, String... params) {
        List<T> ts = mDao.queryRaw(where, params);
        if (ts != null && ts.size() != 0) {
            return ts;
        } else {
            return new ArrayList<>();
        }
    }

    public QueryBuilder<T> queryBuilder() {

        return mDao.queryBuilder();
    }

    public long count() {
        return mDao.count();
    }

    public void refresh(T item) {
        mDao.refresh(item);
    }

    public void detach(T item) {
        mDao.detach(item);
    }

    public void detachAll(){
        mDao.detachAll();
    }
}
