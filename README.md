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
    1. 表的主键是Long类型的，设置自动生成autoincrement时必须是Long类型不能是Int类型
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