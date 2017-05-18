package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

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
        String lastChatTimeSql = "SELECT DISTINCT friend_id, max(chat_msg_time) as chat_msg_time FROM chat_msg WHERE user_id=? GROUP BY friend_id ORDER BY chat_msg_time DESC;";
        Cursor lastChatTiemCursor = db.rawQuery(lastChatTimeSql, new String[]{""+userId});

        while (lastChatTiemCursor.moveToNext()) {
            // 获取好友Id以及最后一次聊天的时间
            int friendId = lastChatTiemCursor.getInt(lastChatTiemCursor.getColumnIndex(ChatTable.FRIEND_ID));
            String lastChatTime = lastChatTiemCursor.getString(lastChatTiemCursor.getColumnIndex(ChatTable.CHAT_MSG_TIME));
            // 根据好友查询和最后一次聊天时间获取好友姓名，头像，聊天内容和最后聊天时间
            String chatListSql = "SELECT chat.user_id, chat.friend_id, friend_name, friend_head, " +
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

    // 获取聊天记录信息
    public ChatInfo getChatMsgInfo(int userId, int friendId) {
        ChatInfo chatInfo = new ChatInfo();
        List<Map<String, Object>> chatMsgList = new ArrayList<>();
        Map<String, Object> map;

        chatInfo.setUserId(userId);
        chatInfo.setFriendId(friendId);

        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT chat.user_id, user_name, user_head, chat.friend_id, friend_name, friend_head, " +
                "friend_expand_relation, chat_msg_content, chat_msg_time, chat_msg_type, show_time_flag " +
                "FROM chat_msg chat, user_infor user, friend_infor friend " +
                "WHERE chat.user_id = user.user_id AND friend.friend_id = chat.friend_id AND " +
                "chat.user_id=? AND chat.friend_id=? ORDER BY chat_msg_time;";
        Cursor cursor = db.rawQuery(sql, new String[] {""+userId, ""+friendId});
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
            map.put(ChatTable.SHOW_TIME_FLAG, cursor.getString(cursor.getColumnIndex(ChatTable.SHOW_TIME_FLAG)));
            chatMsgList.add(map);
        }else{
            // 如果获取聊天记录为空　则只获取好友信息即可
            String friendSql = "SELECT friend_name, friend_head FROM friend_infor WHERE friend_id = ?";
            Cursor friendInforCursor = db.rawQuery(friendSql, new String[]{""+friendId});

            if (friendInforCursor.moveToNext()) {
                chatInfo.setFriendName(friendInforCursor.getString(friendInforCursor.getColumnIndex(FriendTable.FRIEND_NAME)));
                chatInfo.setFriendHead(friendInforCursor.getString(friendInforCursor.getColumnIndex(FriendTable.FRIEND_HEAD)));
                chatInfo.setChatMsgData(chatMsgList);
            }
            return chatInfo;
        }
        // 不再需要重复设置聊天的基本属性
        while(cursor.moveToNext()) {
            map = new HashMap<String, Object>();
            map.put(ChatTable.CHAT_MSG_CONTENT, cursor.getString(cursor.getColumnIndex(ChatTable.CHAT_MSG_CONTENT)));
            map.put(ChatTable.CHAT_MSG_TIME, cursor.getString(cursor.getColumnIndex(ChatTable.CHAT_MSG_TIME)));
            map.put(ChatTable.CHAT_MSG_TYPE, cursor.getString(cursor.getColumnIndex(ChatTable.CHAT_MSG_TYPE)));
            map.put(ChatTable.SHOW_TIME_FLAG, cursor.getString(cursor.getColumnIndex(ChatTable.SHOW_TIME_FLAG)));
            chatMsgList.add(map);
        }
        chatInfo.setChatMsgData(chatMsgList);

        db.close();
        return chatInfo;
    }

    public void insertNewChatMsg(int userId, int friendId, String msgContent, String time, String type, int showTimeFlag) {

        // 获取可写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String chatSql = "INSERT INTO chat_msg(user_id, friend_id, chat_msg_content, chat_msg_time, chat_msg_type, show_time_flag)" +
                " values(" + userId + ", " + friendId + ", '" + msgContent + "', '" + time + "', '" + type + "', " + showTimeFlag + ")";
        db.execSQL(chatSql);
        db.close();
    }

}
