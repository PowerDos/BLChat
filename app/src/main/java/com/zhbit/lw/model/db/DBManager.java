package com.zhbit.lw.model.db;

import android.content.Context;

import com.zhbit.lw.model.dao.MomentTable;
import com.zhbit.lw.model.dao.MomentTableDao;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class DBManager {
    private final DBHelper dbHelper;
    private final MomentTableDao momentTableDao;

    public DBManager(Context context, String name){
        //创建数据库
        dbHelper = new DBHelper(context, name);
        //实例化朋友圈操作类
        momentTableDao = new MomentTableDao(dbHelper);
    }
    //返回朋友圈操作对象
    public MomentTableDao getMomentTableDao(){
        return momentTableDao;
    }


    //关闭数据库
    public void close(){
        dbHelper.close();
    }
}
