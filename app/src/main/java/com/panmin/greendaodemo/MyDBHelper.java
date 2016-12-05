package com.panmin.greendaodemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.panmin.model.dao.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * Created by panmin on 16-12-5.
 * 实现SQLite的版本切换
 */

public class MyDBHelper extends DaoMaster.DevOpenHelper {
    public MyDBHelper(Context context, String name) {
        super(context, name);
    }

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        // TODO: 更新数据库时会先删除数据库然后新建数据库，这时可以做一些操作，比如SharePreference的删除
    }
}
