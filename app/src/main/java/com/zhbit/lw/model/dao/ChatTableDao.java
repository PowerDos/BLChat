package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.entity.FriendEntity;
import com.zhbit.lw.model.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhbit.lw.model.dao.FriendTable.FRIEND_ACCOUNT;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_HEAD;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_ID;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_LOCATION;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_RECENT_PHOTO;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_SEX;
import static com.zhbit.lw.model.dao.FriendTable.GROUP_NAME;
import static com.zhbit.lw.model.dao.FriendTable.NEW_FRIEND_REQUEST_MSG;
import static com.zhbit.lw.model.dao.FriendTable.NICK_NAME;

/**
 * Created by wjh on 17-5-14.
 */

public class ChatTableDao {

    private DBHelper dbHelper;

    public ChatTableDao(DBHelper helper){
        dbHelper = helper;
    }

    public List<Map<String, Object>> getChatList() {
        List<Map<String, Object>> chatList;
        chatList = new ArrayList<Map<String, Object>>();

        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT target_id, target_name, target_head, chat_msg_content, chat_msg_time, target_expand_relation " +
                "FROM chat_msg HAVING chat_msg_time=max(chat_msg_time) ORDER BY chat_msg_time DESC limit 1";
        Cursor cursor = db.rawQuery(sql, null);

        return chatList;
    }

    public void initFriendTableDao(){

        //初始化，创建两条数据测试，后期可删掉
        String Sql = "insert into chat_msg(user_id, user_name, user_head, target_id, target_name, target_head," +
                " target_expand_relation, chat_msg_content, chat_msg_time, chat_msg_type)" +
                " values(1, 'wjh', 'R.drawable.head', 2, '一本正经、', 'R.drawable.head', " +
                ", null, '您好，好久不见，近来可好。', '2017-2-18 12:30', 'receive')";

        //创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据
        db.execSQL(Sql);
        db.close();
    }
}
