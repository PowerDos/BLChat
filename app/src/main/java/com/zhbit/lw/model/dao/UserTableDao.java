package com.zhbit.lw.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.model.bean.UserInfo;
import com.zhbit.lw.model.db.DBHelper;

/**
 * UserTableDao: 用户表的数据库操作
 * Created by wjh on 17-5-14.
 */

public class UserTableDao {

    private DBHelper dbHelper;

    public UserTableDao(DBHelper helper){
        dbHelper = helper;
    }

    // 获取用户的Id
    public int getUserId() {
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询语句
        String Sql = "SELECT user_id FROM user_infor";
        Cursor cursor = db.rawQuery(Sql, null);
        //获取数据设置到UseInfo实例当中
        if (cursor.moveToNext()) {
            int userId = cursor.getInt(cursor.getColumnIndex(UserTable.USER_ID));
            db.close();
            return userId;
        }
        // 如果没有获取到就返回-1
        return -1;
    }

    // 获取用户信息
    public UserInfo getUserInfor(){
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询语句
        String Sql = "SELECT * FROM user_infor";
        Cursor cursor = db.rawQuery(Sql, null);
        //获取数据设置到UseInfo实例当中
        if (cursor.moveToNext()) {
            int userId = cursor.getInt(cursor.getColumnIndex(UserTable.USER_ID));
            String userName = cursor.getString(cursor.getColumnIndex(UserTable.USER_NAME));
            String userHead = cursor.getString(cursor.getColumnIndex(UserTable.USER_HEAD));
            String userSex = cursor.getString(cursor.getColumnIndex(UserTable.USER_SEX));
            String userAccount = cursor.getString(cursor.getColumnIndex(UserTable.USER_ACCOUNT));
            String userLocation = cursor.getString(cursor.getColumnIndex(UserTable.USER_LOCATION));
            String userSign = cursor.getString(cursor.getColumnIndex(UserTable.USER_SIGN));
            UserInfo userInfo = new UserInfo(userId, userName, userHead, userSex, userAccount, userLocation, userSign);
            db.close();
            return userInfo;
        }
        return null;
    }

    //添加用户到数据库
    public void addUserAccount(UserInfo userInfo){
        //实例化数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //执行添加操作，使用replace的好处是有的时候添加，没有的时候覆盖
        ContentValues values = new ContentValues();
        values.put(UserTable.USER_ID,userInfo.getUserId());
        values.put(UserTable.USER_NAME, userInfo.getUserName());
        values.put(UserTable.USER_ACCOUNT, userInfo.getUserAccount());
        values.put(UserTable.USER_HEAD, userInfo.getUserHead());
        values.put(UserTable.USER_SEX, userInfo.getUserSex());
        values.put(UserTable.USER_LOCATION, userInfo.getUserLocation());
        values.put(UserTable.USER_SIGN, userInfo.getUserSign());
        db.replace(UserTable.TABLE_NAME,null,values);
        db.close();
    }

}
