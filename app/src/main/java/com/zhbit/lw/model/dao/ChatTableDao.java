package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.model.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhbit.lw.model.dao.ChatTable.CHAT_MSG_CONTENT;
import static com.zhbit.lw.model.dao.ChatTable.CHAT_MSG_TIME;
import static com.zhbit.lw.model.dao.ChatTable.CHAT_MSG_TYPE;
import static com.zhbit.lw.model.dao.ChatTable.FRIEND_EXPAND_RELATION;
import static com.zhbit.lw.model.dao.ChatTable.FRIEND_ID;
import static com.zhbit.lw.model.dao.ChatTable.USER_ID;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_HEAD;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;
import static com.zhbit.lw.model.dao.UserTable.USER_HEAD;
import static com.zhbit.lw.model.dao.UserTable.USER_NAME;

/**
 * Created by wjh on 17-5-14.
 */

public class ChatTableDao {

    private DBHelper dbHelper;

    public ChatTableDao(DBHelper helper){
        dbHelper = helper;
    }

    // 获取聊天列表
    public List<Map<String, Object>> getChatList(int userId) {
        List<Map<String, Object>> chatList = new ArrayList<Map<String, Object>>();;
        Map<String, Object> map;

        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询出最后一条聊天记录的时间
        String lastChatTimeSql = "SELECT friend_id, max(chat_msg_time) as chat_msg_time FROM chat_msg WHERE user_id=? GROUP BY friend_id;";
        Cursor lastChatTiemCursor = db.rawQuery(lastChatTimeSql, new String[]{""+userId});

        while (lastChatTiemCursor.moveToNext()) {
            // 获取好友Id以及最后一次聊天的时间
            int friendId = lastChatTiemCursor.getInt(lastChatTiemCursor.getColumnIndex(FRIEND_ID));
            String lastChatTime = lastChatTiemCursor.getString(lastChatTiemCursor.getColumnIndex(CHAT_MSG_TIME));
            // 根据好友查询和最后一次聊天时间获取好友姓名，头像，聊天内容和最后聊天时间
            String chatListSql = "SELECT chat.user_id, user_name, user_head, chat.friend_id, friend_name, friend_head, " +
                    "friend_expand_relation, chat_msg_content, chat_msg_time " +
                    "FROM user_infor user, friend_infor friend, chat_msg chat " +
                    "WHERE user.user_id = chat.user_id AND friend.friend_id = chat.friend_id " +
                    "AND chat.friend_id=? AND chat_msg_time=?;";
            Cursor chatListCursor = db.rawQuery(chatListSql, new String[]{""+friendId, lastChatTime});
            while(chatListCursor.moveToNext()) {
                map = new HashMap<String, Object>();
                map.put(FRIEND_ID, chatListCursor.getString(chatListCursor.getColumnIndex(FRIEND_ID)));
                map.put(FRIEND_HEAD, chatListCursor.getString(chatListCursor.getColumnIndex(FRIEND_HEAD)));
                map.put(FRIEND_NAME, chatListCursor.getString(chatListCursor.getColumnIndex(FRIEND_NAME)));
                map.put(FRIEND_EXPAND_RELATION, chatListCursor.getString(chatListCursor.getColumnIndex(FRIEND_EXPAND_RELATION)));
                map.put(CHAT_MSG_CONTENT, chatListCursor.getString(chatListCursor.getColumnIndex(CHAT_MSG_CONTENT)));
                map.put(CHAT_MSG_TIME, chatListCursor.getString(chatListCursor.getColumnIndex(CHAT_MSG_TIME)));
                chatList.add(map);
            }
        }

        db.close();
        return chatList;
    }

    public List<Map<String, Object>> getChatMsgList(int userId, int friendId) {
        List<Map<String, Object>> chatMsgList = new ArrayList<>();
        Map<String, Object> map;

        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT chat.user_id, user_name, user_head, chat.friend_id, friend_name, friend_head, " +
                "friend_expand_relation, chat_msg_content, chat_msg_time, chat_msg_type " +
                "FROM chat_msg chat, user_infor user, friend_infor friend " +
                "WHERE chat.user_id=? AND chat.friend_id=? ORDER BY chat_msg_time;";
        Cursor cursor = db.rawQuery(sql, new String[] {""+userId, ""+friendId});
        while(cursor.moveToNext()) {
            map = new HashMap<String, Object>();
            map.put(USER_ID, userId);
            map.put(USER_NAME, cursor.getString(cursor.getColumnIndex(USER_NAME)));
            map.put(USER_HEAD, cursor.getString(cursor.getColumnIndex(USER_HEAD)));
            map.put(FRIEND_ID, friendId);
            map.put(FRIEND_NAME, cursor.getString(cursor.getColumnIndex(FRIEND_NAME)));
            map.put(FRIEND_HEAD, cursor.getString(cursor.getColumnIndex(FRIEND_HEAD)));
            map.put(FRIEND_EXPAND_RELATION, cursor.getString(cursor.getColumnIndex(FRIEND_EXPAND_RELATION)));
            map.put(CHAT_MSG_CONTENT, cursor.getString(cursor.getColumnIndex(CHAT_MSG_CONTENT)));
            map.put(CHAT_MSG_TIME, cursor.getString(cursor.getColumnIndex(CHAT_MSG_TIME)));
            map.put(CHAT_MSG_TYPE, cursor.getString(cursor.getColumnIndex(CHAT_MSG_TYPE)));
            chatMsgList.add(map);
        }

        db.close();
        return chatMsgList;
    }

    public void initChatTableDao(){

        String Sql = "insert into chat_msg(user_id, friend_id, friend_expand_relation, " +
                "chat_msg_content, chat_msg_time, chat_msg_type)" +
                " values(1, 2, null, '您好，好久不见，近来可好。', '2017-2-18 12:30', 'receive')";

        String secondSql = "insert into chat_msg(user_id, friend_id, friend_expand_relation, " +
                "chat_msg_content, chat_msg_time, chat_msg_type)" +
                " values(1, 2, null, '还不错啦。', '2017-2-18 12:35', 'send')";

        String thirdSql = "insert into chat_msg(user_id, friend_id, friend_expand_relation, " +
                "chat_msg_content, chat_msg_time, chat_msg_type)" +
                " values(1, 3, null, '在吗？。', '2017-2-18 11:30', 'send')";

        //创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据
        db.execSQL(Sql);
        db.execSQL(secondSql);
        db.execSQL(thirdSql);
        db.close();
    }
}
