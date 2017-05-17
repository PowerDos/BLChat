package com.zhbit.lw.model.db;

import android.content.Context;

import com.zhbit.lw.model.dao.ChatTableDao;
import com.zhbit.lw.model.dao.FriendTableDao;
import com.zhbit.lw.model.dao.MomentTable;
import com.zhbit.lw.model.dao.MomentTableDao;
import com.zhbit.lw.model.dao.UserTableDao;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class DBManager {
    private final DBHelper dbHelper;

    public DBManager(Context context, String name){
        //创建数据库
        dbHelper = new DBHelper(context, name);
    }
    //返回朋友圈操作对象
    public MomentTableDao getMomentTableDao(){
        return new MomentTableDao(dbHelper);
    }

    public UserTableDao getUserTableDao() {
        return  new UserTableDao(dbHelper);
    }

    public FriendTableDao getFriendTableDao() {
        return  new FriendTableDao(dbHelper);
    }

    public ChatTableDao getChatTableDao() {
        return  new ChatTableDao(dbHelper);
    }

    //关闭数据库
    public void close(){
        dbHelper.close();
    }
}
