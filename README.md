## GreenDao

### 什么是GreenDao

greenDao是一个将对象映射到SQLite数据库中的快速且轻量的ORM解决方案。[GreenDao官网地址](http://greenrobot.org/greendao/)


### GreenDao如何使用

1. 在android studio中导包(以3.0为例)
```
compile 'org.greenrobot:greendao:3.0.1'
compile 'org.greenrobot:greendao-generator:3.0.0'
```
greendao-generator是用来生成对象文件的工具包。

2. 使用greendao-generator生成model类

 参考本demo中的GreenDaoGenerator类，对本类做以下说明：
 * SCHEMA_VERSION是数据库的版本号,每次有修改就需要改下版本号
 * Schema schema = new Schema(SCHEMA_VERSION, "com.panmin.model");com.panmin.model表示命名空间以及生成实体类的路径
 * schema.setDefaultJavaPackageDao("com.panmin.model.dao");com.panmin.model.dao表示生成工具类的命名空间以及路径，此路径生成的对象主要用来对数据库CRUD的操作。
 * schema.enableKeepSectionsByDefault();这个设置可以使每次生成对象时自定义的某些字段可以不会被覆盖，
 比如服务器给返回的json字符串有不少的字段不必存到本地数据库，但是某些字段还需要使用时，可以在生成的对象类的--keep标记块类写.
 * new DaoGenerator().generateAll(schema, "./app/src/main/java"); 生成代码的总路径
 * 生成Entity，这个要重点说一下：
    1. 表的主键是Long类型的，设置自动生成autoincrement时必须是Long类型不能是Int类型，还有一个坑主键不能设置notNull，不然保存时会失败
    2. 因为我一般从服务器获取数据之后会直接转成对象（我的其它github项目上有json工具使用的方式总结），
    服务器上一般都会用id这个名字作为主键，无奈只能定义一个_id作为本地数据库表的主键;
    这里要说下fastjson有个问题以"_"开头的在转换时会被去掉下划线，所以这里用到了greendao的codeBeforeGetterAndSetter，在get和set方法上加上@JSONField(name = "_id")来解决此问题
    3. 设置默认值，greendao没法自己设置默认值，只能是int或者long默认值是0，设置notNull就行了,比如String设置notNull也不会设置为""（空字符串）；（想学习修改源码来设置默认值的，可以参考[传送门](http://www.cnblogs.com/enyusmile/p/4442271.html)）
    4. 时间类型的建议使用Long类型，建议服务器和手机端都用long类型，虽然fastjson能转换但是gson就不好转换了，如果服务器今天传的是"yyyy-MM-dd HH:mm:ss",明天又传个"yyyy.MM.dd HH:mm:ss",后天又改成时间戳,咋办，没办法，所以项目前期要和服务器端商量好时间类型格式（已踩过坑）
    5. 创建关系表的方法(参考[传送门](http://blog.csdn.net/yuyuanhuang/article/details/42751673)),本Demo中也有,不过个人建议还是不要弄成关系数据库，因为不好管理，需要获取主表关联子表的数据，可以自己在查询的时候用代码查询一下就ok了，SQLite本来就是小型数据库建议大家不要搞得太复杂。
`这个生成有点像是node.js中mongodb数据库生成包的使用方式，但是没那个好用；重要的事情说3遍，我再说一遍时间建议用long类型，奇葩的UE可能今天让界面显示"yyyy-MM-dd HH:mm"，明天又显示"yyyy.MM.dd HH:mm"，还是long好转换`


### GreenDao的原理

看了生成的Dao文件你就能明白greenDao为什么能这么高效这么快了，它不是用反射，而是把生成数据库和操作数据库的代码都生成出来了,原理不多讲，大家看看源码就知道了。

### GreenDao和afinal中db模块对比
因为公司项目中上个版本用的是afinal，之后被我改成GreenDao了，所以说下使用过程中afinal的缺点：
1. 保存实体时只能一个个的循环保存，速度慢，没法批量操作，对事务的支持也不好
2. 保存实体后不返回保存后数据库的Id，还需要再去查，太麻烦，效率不好
3. 没有项目Demo，框架时间比较久，代码冗余比较多
`说一个afinal的优点：能每个表单独设置版本号更新`


### GreenDao从2.x升级到3.x
与2.x版本的区别：
* 包名修改：从de.greenrobot改成org.greenrobot
* 新加的注解，以前想加个注解都不敢加，怕加上之后下次生成又要手动去加,比如gson的@SerializedName("id")
* 3.0新增了加密功能，可以给数据库加密了


### GreenDao缓存
在DaoMaster中可以看到初始化DaoSession时默认是new DaoSession(db, IdentityScopeType.Session, daoConfigMap);IdentityScopeType.Session就是开启缓存模式了。
如果传入的是IdentityScopeType.Session，同样的查询只会执行一次，查询结果对象List保存在内存里会重复使用。会导致的问题是如果List对象属性改变，未持久化它，再次做query,不会从数据库查询，只是缓存中的结果。会导致与数据库表数据不一致。
解决方案：
1. 再次执行查询之前执行daoSession.clear();方法。
daoSession.clear()会删除所有表的缓存，那么如何对单表清除缓存呢？这就要用到dao.detachAll()了
```
noteDao = daoSession.getNoteDao();
noteDao.detachAll();
或者
note = noteDao.load(key);
//...如果在这里修改了note的值，后面要update修改时，再次查询只会查询缓存里的数据
noteDao.detach(note);
```
2. 或者在这种情况下之不保留缓存，用IdentityScopeType.None。

### GreenDao进阶
官方推荐只实例化一个DaoMaster,而且为了方便我们以后切换model模块的底层实现方式，需要抽象一个中间层来连接自己的app和GreenDao。下面我们就来讲到中间层的实现方式。
* 数据库更新时可能会需要做一些操作，比如清除SharePreference(参考本Demo的MyDBHelper实现)
* 这个中间层需要实现基本的CRUD(参考本Demo的DBController实现)