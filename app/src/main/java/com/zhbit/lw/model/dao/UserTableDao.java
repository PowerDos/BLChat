package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.entity.UserEntity;
import com.zhbit.lw.model.db.DBHelper;

import static com.zhbit.lw.model.dao.UserTable.USER_ACCOUNT;
import static com.zhbit.lw.model.dao.UserTable.USER_HEAD;
import static com.zhbit.lw.model.dao.UserTable.USER_ID;
import static com.zhbit.lw.model.dao.UserTable.USER_LOCATION;
import static com.zhbit.lw.model.dao.UserTable.USER_NAME;
import static com.zhbit.lw.model.dao.UserTable.USER_SEX;
import static com.zhbit.lw.model.dao.UserTable.USER_SIGN;

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
            int userId = cursor.getInt(cursor.getColumnIndex(USER_ID));
            String userName = cursor.getString(cursor.getColumnIndex(USER_NAME));
            String userHead = cursor.getString(cursor.getColumnIndex(USER_HEAD));
            String userSex = cursor.getString(cursor.getColumnIndex(USER_SEX));
            String userAccount = cursor.getString(cursor.getColumnIndex(USER_ACCOUNT));
            String userLocation = cursor.getString(cursor.getColumnIndex(USER_LOCATION));
            String userSign = cursor.getString(cursor.getColumnIndex(USER_SIGN));
            UserEntity userEntity = new UserEntity(userId, userName, userHead, userSex, userAccount, userLocation, userSign);
            db.close();
            return userEntity;
        }
        return null;
    }

    public void initUserTableDao(){
        //初始化，创建两条数据测试，后期可删掉
        String Sql = "insert into user_infor(user_id, user_name, user_head, user_sex, user_account, user_location, user_sign)"
                +"values(1, 'Wjh', 'R.drawable.head', '男', 'XR_HUI', '清远市', '水流心赤.')";
        //创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据
        db.execSQL(Sql);
        db.close();
    }
}
