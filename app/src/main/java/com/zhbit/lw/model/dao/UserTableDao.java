package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.entity.MomentInfo;
import com.zhbit.lw.entity.UserEntity;
import com.zhbit.lw.model.db.DBHelper;

import java.util.ArrayList;

/**
 * Created by wjh on 17-5-14.
 */

public class UserTableDao {

    private DBHelper dbHelper;

    public UserTableDao(DBHelper helper){
        dbHelper = helper;
    }

    // 获取用户信息
    public UserEntity getUserInforById(){
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询语句
        String Sql = "select * from user_infor";
        Cursor cursor = db.rawQuery(Sql, null);
        //获取数据
        while (cursor.moveToNext()) {
            String userName = cursor.getString(cursor.getColumnIndex(UserTable.USER_NAME));
            String userHead = cursor.getString(cursor.getColumnIndex(UserTable.USER_HEAD));
            String userSex = cursor.getString(cursor.getColumnIndex(UserTable.USER_SEX));
            String userAccount = cursor.getString(cursor.getColumnIndex(UserTable.USER_ACCOUNT));
            String userLocation = cursor.getString(cursor.getColumnIndex(UserTable.USER_LOCATION));
            String userSign = cursor.getString(cursor.getColumnIndex(UserTable.USER_SIGN));
            UserEntity userEntity = new UserEntity(userName, userHead, userSex, userAccount, userLocation, userSign);
            db.close();
            return userEntity;
        }
        return null;
    }

    public void initUserTableDao(){
        //初始化，创建两条数据测试，后期可删掉
        String Sql = "insert into user_infor(user_name, user_head, user_sex, user_account, user_location)"
                +"values('Wjh', 'R.drawable.head', '男', 'XR_HUI', '英德市')";
        //创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据
        db.execSQL(Sql);
        db.close();
    }
}
