package com.panmin.greendaodemo;

import android.content.Context;

import com.panmin.model.dao.DaoMaster;
import com.panmin.model.dao.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by panmin on 16-12-5.
 * SQLite数据库控制类
 */

public class DBController {
    private static final String DEFAULT_DB_NAME = "com.panmin.db";
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private Context mContext;
    private String DB_NAME;

    private DBController(Context context) {
        init(context, DEFAULT_DB_NAME);
    }
    private static DBController mDBController;
    public static DBController getInstance(Context context){
        if(mDBController == null){
            synchronized (DBController.class){
                if(mDBController == null){
                    mDBController = new DBController(context.getApplicationContext());
                }
            }
        }
        return mDBController;
    }

    public void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = context.getApplicationContext();
        DB_NAME = dbName;
        // FIXME: 2016/1/22 just for debug,release please close it
        enableQueryBuilderLog();
        getDaoSession();
    }

    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            synchronized (DBController.class) {
                if (daoMaster == null) {
                    //DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
                    MyDBHelper helper = new MyDBHelper(mContext,DB_NAME,null);
                    daoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            synchronized (DBController.class) {
                if (daoSession == null) {
                    if (daoMaster == null) {
                        daoMaster = getDaoMaster();
                    }
                }
                daoSession = daoMaster.newSession();
            }
        }
        return daoSession;
    }
    //开启sqlite的log
    public void enableQueryBuilderLog(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}
