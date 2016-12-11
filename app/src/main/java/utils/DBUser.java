package utils;

import android.content.Context;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import Entity.DaoMaster;
import Entity.DaoSession;
import Entity.User;
import Entity.UserDao;


/**
 *  itemDao 增删改查的辅助类
 */
public class DBUser {
    private Context context;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserDao userDao;
    private static DBUser dbUser;

    /**
     * 构造方法中 获得了 daoMaster，daoSession ，itemDao
     */
    private DBUser(Context context){
        this.context = context;
        daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
        daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
    }

    /**
     * 单例
     */
    public static DBUser getInstance(Context  context){
        if(dbUser == null){
            synchronized (DBUser.class){
                if(dbUser == null){
                    dbUser = new DBUser(context);
                }
            }
        }
        return  dbUser;
    }

    /**
     * 单个插入到 User 这张表中
     */
    public void insertUser(User user){
        // 插入前判断该user是否已在数据库中
        if(queryById(user.getName()) != null){
            return;
        }
        if(user != null){
            userDao.insert(user);
        }
    }

    /**
     * 多个插入到 User 这张表中
     */
    public void insetUserList(List<User> list){
        if(list == null || list.isEmpty()){
            return;
        }
        userDao.insertInTx(list);
    }

    /**
     * 通过 ID 查询 User 这张表
     */
    public User queryById(String userName){
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Name.eq(userName));
        if(qb.list() == null || qb.list().size() == 0){
            return null;
        }
        return qb.list().get(0);
    }

    /**
     * 查询 User 这张表 所有数据，返回集合
     */
    public List<User> queryAllUser(){
        QueryBuilder<User> qb = userDao.queryBuilder();
        return qb.list();
    }

    /**
     * 删除 User 这张表名称为XXX的数据
     */
    public void deleteByName(String userName){
        QueryBuilder<User> qb = userDao.queryBuilder();
        DeleteQuery<User> dq = qb.where(UserDao.Properties.Name.eq(userName)).buildDelete();
        dq.executeDeleteWithoutDetachingEntities();// 久的实体可能会存在缓存问题
    }

    /**
     * 删除表中的全部数据
     */
    public void clear(){
        userDao.deleteAll();
    }

    /**
     * 更改表中的数据
     */
    public void update(User user){
        // 1 查询指定条目
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Name.eq(user.getName()));

        // 2 对数据进行判断
        List<User> list = qb.list();
        if(list == null ||list.size()== 0){
           return;
        }

        // 3 拿到数据，更改数据
        User item  = list.get(0);
        item.setAge(user.getAge());
        item.setName(user.getName());

        // 4 插入或者替换
        userDao.insertOrReplace(item);
    }

}
