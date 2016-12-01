package com.panmin.greendaodemo;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;
import org.greenrobot.greendao.generator.ToMany;
import org.greenrobot.greendao.generator.ToOne;

/**
 * Created by panmin on 16-12-1.
 */

public class GreenDaoGenerator {
    public static final int SCHEMA_VERSION = 40;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(SCHEMA_VERSION, "com.panmin.model");
        schema.setDefaultJavaPackageDao("com.panmin.model.dao");
        schema.enableKeepSectionsByDefault();
        //schema.enableActiveEntitiesByDefault();
        addEntity(schema);

        //创建关系型数据库
        addRelationshipEntity(schema);

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addEntity(Schema schema) {
        Entity entity = schema.addEntity("TaskCall");
        entity.addImport("java.util.List");//导包
        /*entity.addImport("com.alibaba.fastjson.annotation.JSONField");//未引用fastjson的包，暂不生成
        entity.addLongProperty("_id").columnName("_id").primaryKey().autoincrement().codeBeforeGetterAndSetter("@JSONField(name = \"_id\")");*/
        entity.addLongProperty("_id").columnName("_id").primaryKey().autoincrement();
        //entity.addIntProperty("_id").columnName("_id").primaryKey().autoincrement();//XXXXXX
        entity.addStringProperty("id").columnName("id");
        entity.addStringProperty("userId").columnName("userId").notNull();
        entity.addStringProperty("title").columnName("title");
        entity.addStringProperty("remarks").columnName("remarks");
        entity.addLongProperty("remindTime").columnName("remindTime").notNull();
    }

    //创建关系型数据库
    private static void addRelationshipEntity(Schema schema) {
        //主表
        Entity entityTableFather = schema.addEntity("TableFather");
        entityTableFather.setTableName("TableFather");//不设置的话数据库里面都变成大写了
        Property fatherId = entityTableFather.addIdProperty().autoincrement().getProperty();//这种方式生成的主键实体类中叫id,在数据库文件中是_id
        entityTableFather.addStringProperty("father");
        Property sonId = entityTableFather.addLongProperty("sonId").columnName("sonId").notNull().getProperty();//外键Id

        //子表
        Entity entityTableSon = schema.addEntity("TableSon");
        entityTableSon.setTableName("TableSon");
        entityTableSon.addIdProperty().autoincrement();//子表Id
        entityTableSon.addStringProperty("son").columnName("son");

        //设置外键关系
        ToMany toMany = entityTableFather.addToMany(entityTableSon, sonId);
        toMany.setName("fatherList");

        ToOne toOne = entityTableSon.addToOne(entityTableFather,fatherId);
        toOne.setName("father");
        //这样设置完两边就都能点出来了
    }
}
