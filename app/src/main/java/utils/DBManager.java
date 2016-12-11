package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import Entity.DaoMaster;

/**
 * DB管理工具类 ，提供获取 DB 的方法
 * 实现的逻辑是 DaoMaster 获得 Helper ， devOpenHelper 获得 DB
 */
public class DBManager {
    private Context context;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private static DBManager instance;

    private static final String DB_NAME = "user_db";// 数据库名

    /**
     * 构造方法中 初始化 Helper 和 数据库名
     */
    private DBManager(Context context){
        this.context = context;

        // 通过 DaoMaster 获得 Helper
        devOpenHelper = new DaoMaster.DevOpenHelper(context,DB_NAME,null);
    }

    // 提供单例
    public static DBManager getInstance(Context context){
        if(instance == null){
            synchronized (DBManager.class){
                if(instance == null){
                    instance = new DBManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 通过 devOpenHelper 获得 DB
     */
    public SQLiteDatabase getReadableDatabase(){
        if(devOpenHelper == null){
            devOpenHelper = new DaoMaster.DevOpenHelper(context,DB_NAME,null);
        }
        SQLiteDatabase db = devOpenHelper.getReadableDatabase();
        return  db;
    }

}
