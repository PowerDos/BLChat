package com.zhbit.lw.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhbit.lw.model.dao.ChatTable;
import com.zhbit.lw.model.dao.FriendTable;
import com.zhbit.lw.model.dao.MomentTable;
import com.zhbit.lw.model.dao.UserTable;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String name){
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建朋友圈表
        db.execSQL(MomentTable.CreateTable);
        //创建个人信息表
        db.execSQL(UserTable.CREATE_TABLE);
        //创建朋友表
        db.execSQL(FriendTable.CREATE_TABLE);
        //创建好友表
        db.execSQL(FriendTable.CREATE_TABLE);
        //创建聊天表
        db.execSQL(ChatTable.CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

//        db.execSQL("DROP TABLE IF EXISTS moment;");
//        db.execSQL(MomentTable.CreateTable);
//
//        db.execSQL("DROP TABLE IF EXISTS user_infor;");
//        db.execSQL(UserTable.CREATE_TABLE);
//
//        db.execSQL("DROP TABLE IF EXISTS friend_infor;");
//        db.execSQL(FriendTable.CREATE_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
