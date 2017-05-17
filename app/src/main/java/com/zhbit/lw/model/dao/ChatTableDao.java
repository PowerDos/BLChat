package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zhbit.lw.model.bean.ChatInfo;
import com.zhbit.lw.model.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            int friendId = lastChatTiemCursor.getInt(lastChatTiemCursor.getColumnIndex(ChatTable.FRIEND_ID));
            String lastChatTime = lastChatTiemCursor.getString(lastChatTiemCursor.getColumnIndex(ChatTable.CHAT_MSG_TIME));
            // 根据好友查询和最后一次聊天时间获取好友姓名，头像，聊天内容和最后聊天时间
            String chatListSql = "SELECT chat.user_id, user_name, user_head, chat.friend_id, friend_name, friend_head, " +
                    "friend_expand_relation, chat_msg_content, chat_msg_time " +
                    "FROM user_infor user, friend_infor friend, chat_msg chat " +
                    "WHERE user.user_id = chat.user_id AND friend.friend_id = chat.friend_id " +
                    "AND chat.friend_id=? AND chat_msg_time=?;";
            Cursor chatListCursor = db.rawQuery(chatListSql, new String[]{""+friendId, lastChatTime});
            while(chatListCursor.moveToNext()) {
                map = new HashMap<String, Object>();
                map.put(FriendTable.FRIEND_ID, chatListCursor.getString(chatListCursor.getColumnIndex(FriendTable.FRIEND_ID)));
                map.put(FriendTable.FRIEND_HEAD, chatListCursor.getString(chatListCursor.getColumnIndex(FriendTable.FRIEND_HEAD)));
                map.put(FriendTable.FRIEND_NAME, chatListCursor.getString(chatListCursor.getColumnIndex(FriendTable.FRIEND_NAME)));
                map.put(FriendTable.FRIEND_EXPAND_RELATION, chatListCursor.getString(chatListCursor.getColumnIndex(FriendTable.FRIEND_EXPAND_RELATION)));
                map.put(ChatTable.CHAT_MSG_CONTENT, chatListCursor.getString(chatListCursor.getColumnIndex(ChatTable.CHAT_MSG_CONTENT)));
                map.put(ChatTable.CHAT_MSG_TIME, chatListCursor.getString(chatListCursor.getColumnIndex(ChatTable.CHAT_MSG_TIME)));
                chatList.add(map);
            }
        }
        db.close();
        return chatList;
    }

    public ChatInfo getChatMsgInfo(int userId, int friendId) {
        ChatInfo chatInfo = new ChatInfo();
        List<Map<String, Object>> chatMsgList = new ArrayList<>();
        Map<String, Object> map;

        chatInfo.setUserId(userId);
        chatInfo.setFriendId(friendId);

        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select distinct chat.user_id, user_name, user_head, chat.friend_id, friend_name, friend_head, " +
                "friend_expand_relation, chat_msg_content, chat_msg_time, chat_msg_type " +
                "from chat_msg chat, user_infor user, friend_infor friend " +
                "where chat.user_id=? and chat.friend_id=? order by chat_msg_time;";

        sql = "select distinct chat.user_id, user_name, user_head, chat.friend_id, friend_name, friend_head, " +
                "friend_expand_relation, chat_msg_content, chat_msg_time, chat_msg_type " +
                "from chat_msg chat, user_infor user, friend_infor friend " +
                "where chat.user_id='" + userId + "' and chat.friend_id='" + friendId + "' order by chat_msg_time;";
//        Cursor cursor = db.rawQuery(sql, new String[] {""+userId, ""+friendId});
        Cursor cursor = db.rawQuery(sql, null);
        // 如果能获取 第一条数据先设置聊天的基本属性
        if (cursor.moveToNext()) {
            chatInfo.setUserName(cursor.getString(cursor.getColumnIndex(UserTable.USER_NAME)));
            chatInfo.setUserHead(cursor.getString(cursor.getColumnIndex(UserTable.USER_HEAD)));
            chatInfo.setFriendName(cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_NAME)));
            chatInfo.setFriendHead(cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_HEAD)));
            chatInfo.setFriendExpandRelation(cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_EXPAND_RELATION)));

            map = new HashMap<String, Object>();
            map.put(ChatTable.CHAT_MSG_CONTENT, cursor.getString(cursor.getColumnIndex(ChatTable.CHAT_MSG_CONTENT)));
            map.put(ChatTable.CHAT_MSG_TIME, cursor.getString(cursor.getColumnIndex(ChatTable.CHAT_MSG_TIME)));
            map.put(ChatTable.CHAT_MSG_TYPE, cursor.getString(cursor.getColumnIndex(ChatTable.CHAT_MSG_TYPE)));
            chatMsgList.add(map);
        }else{
            return null;
        }
        // 不再需要重复设置聊天的基本属性
        while(cursor.moveToNext()) {
            map = new HashMap<String, Object>();
            map.put(ChatTable.CHAT_MSG_CONTENT, cursor.getString(cursor.getColumnIndex(ChatTable.CHAT_MSG_CONTENT)));
            map.put(ChatTable.CHAT_MSG_TIME, cursor.getString(cursor.getColumnIndex(ChatTable.CHAT_MSG_TIME)));
            map.put(ChatTable.CHAT_MSG_TYPE, cursor.getString(cursor.getColumnIndex(ChatTable.CHAT_MSG_TYPE)));
            chatMsgList.add(map);
        }
        chatInfo.setChatMsgData(chatMsgList);

        db.close();
        for (int i=0;i < chatMsgList.size();i++) {
            Log.i("WJH", chatMsgList.get(i).get(ChatTable.CHAT_MSG_CONTENT).toString());
        }
        return chatInfo;
    }

}
