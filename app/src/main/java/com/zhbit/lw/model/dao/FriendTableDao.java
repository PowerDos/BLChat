package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.Logs.Logs;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjh on 17-5-14.
 */

public class FriendTableDao {

    private DBHelper dbHelper;

    public FriendTableDao(DBHelper helper){
        dbHelper = helper;
    }

    // 获取用户信息
    public FriendInfo getFriendInforByAccount(String friendAccount){
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询语句
        String Sql = "select * from friend_infor where friend_account=?";
        Cursor cursor = db.rawQuery(Sql, new String[]{friendAccount});
        //获取数据
        if (cursor.moveToNext()) {
            FriendInfo friendInfo = new FriendInfo();
            friendInfo.setFriendId(cursor.getInt(cursor.getColumnIndex(FriendTable.FRIEND_ID)));
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
            while (cursor.moveToNext()) {
                map = new HashMap<String, Object>();
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
    public List<Map<String, Object>> getNewFriendListById() {
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询新好友列表
        String sql = "SELECT friend_id, friend_head, friend_name, new_friend_request_msg FROM friend_infor WHERE new_friend_flag=1";
        Cursor cursor = db.rawQuery(sql, null);


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

    //添加好友
    public boolean AddFriend(FriendInfo friendInfo){
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String friendSql = "insert into friend_infor(user_id, friend_id, group_name, friend_name, nick_name, friend_sex, friend_account, friend_head, friend_location, friend_recent_photo, new_friend_flag, new_friend_request_msg)"
                +" values(1,"
                +friendInfo.getFriendId()
                +",'friend', '"
                +friendInfo.getFriendName()
                +"', 'xxx', '"
                +friendInfo.getFriendSex()
                +"', '"
                +friendInfo.getFriendAccount()
                +"', 'R.drawable.head', '"
                + friendInfo.getFriendLocation()+"', null, 1, '您好，我是您的老同学。')";
        db.execSQL(friendSql);
        Logs.d("ADD_FRIEND_SQL",friendSql);
        db.close();
        return true;
    }

    public FriendInfo getFriendInfoById(int friendId){
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询语句
        String Sql = "select * from friend_infor where friend_id = ?";
        Cursor cursor = db.rawQuery(Sql, new String[]{""+friendId});
        //获取数据
        if (cursor.moveToNext()) {
            FriendInfo friendInfo = new FriendInfo();
            friendInfo.setFriendId(cursor.getInt(cursor.getColumnIndex(FriendTable.FRIEND_ID)));
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
}
