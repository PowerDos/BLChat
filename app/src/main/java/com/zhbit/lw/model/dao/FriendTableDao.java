package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public List<String> getGroupList() {
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select distinct group_name from friend_infor";
        Cursor cursor = db.rawQuery(sql, null);
        List<String> groupList = new ArrayList<String>();
        while(cursor.moveToNext()) {
            groupList.add(cursor.getString(cursor.getColumnIndex(FriendTable.GROUP_NAME)));
        }
        db.close();
        return groupList;
    }

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

    public void initFriendTableDao(){
        //初始化，创建两条数据测试，后期可删掉
        String Sql = "insert into friend_infor(user_id, friend_id, group_name, friend_name, nick_name, friend_sex, friend_account, friend_head, friend_location, friend_recent_photo, new_friend_flag, new_friend_request_msg)"
                +"values(1, 2, 'friend', '2: 一本正经、', 'nick_name', '男', 'BLCHAT_01', 'R.drawable.head', '英德市', null, 1, '您好，我是您的老同学。')";

        String senondSql = "insert into friend_infor(user_id, friend_id, group_name, friend_name, nick_name, friend_sex, friend_account, friend_head, friend_location, friend_recent_photo, new_friend_flag, new_friend_request_msg)"
                +"values(1, 3, 'family', '3: 胡说八道、', 'second_name', '女', 'BLCHAT_02', 'R.drawable.head', '珠海市', null, 1, '您好，还记得我吗？')";
        //创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据
        db.execSQL(Sql);
        db.execSQL(senondSql);
        db.close();
    }
}
