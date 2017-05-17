package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.model.bean.FriendInfo;
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

public class FriendTableDao {

    private DBHelper dbHelper;

    public FriendTableDao(DBHelper helper){
        dbHelper = helper;
    }

    // 获取用户信息
    public FriendInfo getFriendInforByFriendId(int friendId){
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询语句
        String Sql = "select * from friend_infor where friend_id = ?";
        Cursor cursor = db.rawQuery(Sql, new String[]{""+friendId});
        //获取数据
        if (cursor.moveToNext()) {
            FriendInfo friendInfo = new FriendInfo();
            friendInfo.setFriendId(friendId);
            friendInfo.setFriendName(cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_NAME)));
            friendInfo.setNickName(cursor.getString(cursor.getColumnIndex(FriendTable.NICK_NAME)));
            friendInfo.setFriendAccount(cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_ACCOUNT)));
            friendInfo.setFriendSex(cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_SEX)));
            friendInfo.setFriendLocation(cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_LOCATION)));
            friendInfo.setFriendRecentPhoto(cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_RECENT_PHOTO)));
            db.close();
            return friendInfo;
        }
        return null;
    }

    // 获取分组列表
    public List<String> getGroupList() {
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT DISTINCT group_name FROM friend_infor ORDER BY group_name";
        Cursor cursor = db.rawQuery(sql, null);
        List<String> groupList = new ArrayList<String>();
        while(cursor.moveToNext()) {
            groupList.add(cursor.getString(cursor.getColumnIndex(FriendTable.GROUP_NAME)));
        }
        db.close();
        return groupList;
    }

    // 获取分组子列表
    public List<List<Map<String, Object>>> getGrouopChildList(List<String> parentList) {
        List<List<Map<String, Object>>> childList = new ArrayList<List<Map<String, Object>>>();

        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<Map<String, Object>> child;
        Map<String, Object> map;
        for(int i = 0;i < parentList.size();i++) {
            String sql = "SELECT friend_id, friend_name, friend_head FROM friend_infor WHERE group_name=?";
            Cursor cursor = db.rawQuery(sql, new String[]{parentList.get(i)});

            child = new ArrayList<Map<String, Object>>();
            map = new HashMap<String, Object>();
            while (cursor.moveToNext()) {
                map.put(FriendTable.FRIEND_ID, cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_ID)));
                map.put(FriendTable.FRIEND_NAME, cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_NAME)));
                map.put(FriendTable.FRIEND_HEAD, cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_HEAD)));
                child.add(map);
            }
            childList.add(child);
        }
        db.close();
        return childList;
    }

    // 获取新好友列表数据
    public List<Map<String, Object>> getNewFriendListById(int userId) {
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询新好友列表
        String sql = "SELECT friend_id, friend_head, friend_name, new_friend_request_msg FROM friend_infor WHERE new_friend_flag=1 AND user_id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{""+userId});

        // 将新好友列表数据遍历出来
        List<Map<String, Object>> newFriendList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        while(cursor.moveToNext()) {
            map = new HashMap<String, Object>();
            map.put(FriendTable.FRIEND_ID, cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_ID)));
            map.put(FriendTable.FRIEND_HEAD, cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_HEAD)));
            map.put(FriendTable.FRIEND_NAME, cursor.getString(cursor.getColumnIndex(FriendTable.FRIEND_NAME)));
            map.put(FriendTable.NEW_FRIEND_REQUEST_MSG, cursor.getString(cursor.getColumnIndex(FriendTable.NEW_FRIEND_REQUEST_MSG)));
            newFriendList.add(map);
        }
        return newFriendList;
    }
}
