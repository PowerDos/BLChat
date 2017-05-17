package com.zhbit.lw.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhbit.lw.model.dao.ChatTable;
import com.zhbit.lw.model.dao.ChatTableDao;
import com.zhbit.lw.model.dao.FriendTable;
import com.zhbit.lw.model.dao.FriendTableDao;
import com.zhbit.lw.model.dao.MomentTable;
import com.zhbit.lw.model.dao.MomentTableDao;
import com.zhbit.lw.model.dao.UserTable;
import com.zhbit.lw.model.dao.UserTableDao;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String name){
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 先判断是否存在表　如果存在就先删除
        db.execSQL("DROP TABLE IF EXISTS moment;");
        db.execSQL("DROP TABLE IF EXISTS user_infor;");
        db.execSQL("DROP TABLE IF EXISTS friend_infor;");
        db.execSQL("DROP TABLE IF EXISTS chat_msg;");
        //创建朋友圈表
        db.execSQL(MomentTable.CreateTable);
        //创建个人信息表
        db.execSQL(UserTable.CREATE_TABLE);
        //创建好友表
        db.execSQL(FriendTable.CREATE_TABLE);
        //创建聊天表
        db.execSQL(ChatTable.CREATE_TABLE);

//        // 插入聊天的测试数据
//        String chatSql = "insert into chat_msg(user_id, friend_id, chat_msg_content, chat_msg_time, chat_msg_type, show_time_flag)" +
//                " values(1, 2, '您好，好久不见，近来可好。', '2017-2-16 12:30', 'receive', 1)";
//        String secondChatSql = "insert into chat_msg(user_id, friend_id, chat_msg_content, chat_msg_time, chat_msg_type, show_time_flag)" +
//                " values(1, 2, '还不错啦。', '2017-2-16 12:32', 'send', 1)";
//        String thirdChatSql = "insert into chat_msg(user_id, friend_id, chat_msg_content, chat_msg_time, chat_msg_type, show_time_flag)" +
//                " values(1, 3, '在吗？。', '2017-2-16 11:30', 'send', 1)";
//        db.execSQL(chatSql);
//        db.execSQL(secondChatSql);
//        db.execSQL(thirdChatSql);

        // 插入用户的测试数据
        String userSql = "insert into user_infor(user_id, user_name, user_head, user_sex, user_account, user_location, user_sign)"
                +"values(1, 'Wjh', 'R.drawable.head', '男', 'XR_HUI', '清远市', '水流心赤.')";
        db.execSQL(userSql);

        // 插入好友的测试数据
        String friendSql = "insert into friend_infor(user_id, friend_id, group_name, friend_name, nick_name, friend_sex, friend_account, friend_head, friend_location, friend_recent_photo, new_friend_flag, new_friend_request_msg)"
                +"values(1, 2, 'friend', '2: 一本正经、', 'nick_name', '男', 'BLCHAT_01', 'R.drawable.head', '英德市', null, 1, '您好，我是您的老同学。')";
        String senondFriendSql = "insert into friend_infor(user_id, friend_id, group_name, friend_name, nick_name, friend_sex, friend_account, friend_head, friend_location, friend_recent_photo, new_friend_flag, new_friend_request_msg)"
                +"values(1, 3, 'family', '3: 胡说八道、', 'second_name', '女', 'BLCHAT_02', 'R.drawable.head', '珠海市', null, 1, '您好，还记得我吗？')";
        db.execSQL(friendSql);
        db.execSQL(senondFriendSql);

        // 插入朋友圈的测试数据
        String momentSql = "insert into moment(friendname,friendid,headphoto,publishtime,publishtext,publishImg)"
                +"values('Gavin Lin','liz0607','@drawable/head','2017/5/12','富强、民主、文明、和谐，倡导自由"
                + "、平等、公正、法治，倡导爱国、敬业、诚信、友善','@drawable/app_icon')";
        db.execSQL(momentSql);
        db.execSQL(momentSql);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
