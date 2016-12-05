package com.panmin.greendaodemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.panmin.model.TableFather;
import com.panmin.model.TableSon;
import com.panmin.model.TaskCall;
import com.panmin.model.dao.DaoMaster;
import com.panmin.model.dao.TaskCallDao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.panmin.greendaodemo", appContext.getPackageName());
    }

    @Test
    public void saveTaskCallData(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        TaskCallDao taskCallDao = DaoMaster.newDevSession(appContext,"com.panmin.greendao").getTaskCallDao();
        TaskCall taskCall = new TaskCall();
        taskCall.setId("idididididididid");
        taskCall.setTitle("title1");
        taskCall.setUserId("userId1");
        taskCall.setCreateTime(new Date().getTime());
        taskCall.setRemarks("oldRemark");
        taskCallDao.insert(taskCall);
        Assert.assertTrue(taskCall.get_id()!=0);
    }

    @Test
    public void queryUpdateQuery(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        TaskCallDao taskCallDao = DaoMaster.newDevSession(appContext,"com.panmin.greendao").getTaskCallDao();
        TaskCall load = taskCallDao.load(1l);
        Log.i(TAG, "queryUpdateQuery: load="+load.getRemarks());

        load.setRemarks("newRemark");
        taskCallDao.update(load);

        TaskCall taskCall = taskCallDao.load(1l);
        Log.i(TAG, "queryUpdateQuery: taskCall="+taskCall.getRemarks());
        Assert.assertTrue(taskCall.getRemarks().equals("newRemark"));
    }

    @Test
    public void saveFatherSonTable(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBManager tableFatherManager = new DBManager(appContext, TableFather.class);
        DBManager tableSonManager = new DBManager(appContext, TableSon.class);
        TableFather tf = new TableFather();tf.setFather("f1");
        tableFatherManager.save(tf);

        TableSon ts1 = new TableSon();ts1.setSon("s1");ts1.setFatherId(tf.getId());tableSonManager.save(ts1);
        TableSon ts2 = new TableSon();ts2.setSon("s1");ts2.setFatherId(tf.getId());tableSonManager.save(ts2);
        TableSon ts3 = new TableSon();ts3.setSon("s1");ts3.setFatherId(tf.getId());tableSonManager.save(ts3);
        TableSon ts4 = new TableSon();ts4.setSon("s1");ts4.setFatherId(tf.getId());tableSonManager.save(ts4);
        TableSon ts5 = new TableSon();ts5.setSon("s1");ts5.setFatherId(tf.getId());tableSonManager.save(ts5);

    }
    @Test
    public void testQuery(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        //第一次查询
        DBManager tableSonManager = new DBManager(appContext, TableSon.class);
        List<TableSon> ft = tableSonManager
                .queryBuilder()
                .list();

        //对数据库中的一条数据进行更新：
        TableSon cs = ft.get(0);
        Log.d("son",cs.getSon());
        cs.setSon(cs.getSon() + "new");
        //tableSonManager.update(cs);//不update时，第二次查询也会查询缓存中的值，值是改变了的。

        //第二次查询
        List<TableSon> chats = tableSonManager
                .queryBuilder()
                .list();

        Log.d("son",chats.get(0).getSon());
    }
}
