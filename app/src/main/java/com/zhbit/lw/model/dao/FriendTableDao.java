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

public class FriendTableDao {

    private DBHelper dbHelper;

    public FriendTableDao(DBHelper helper){
        dbHelper = helper;
    }

    // 获取好友信息
    public FriendEntity getFriendInforByUserName(String friendName){
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询语句
        String Sql = "select * from friend_infor where friend_name = ?";
        Cursor cursor = db.rawQuery(Sql, new String[]{friendName});
        //获取数据
        while (cursor.moveToNext()) {
            FriendEntity friendEntity = new FriendEntity();
            friendEntity.setFriendId(cursor.getInt(cursor.getColumnIndex(FRIEND_ID)));
            friendEntity.setFriendName(friendName);
            friendEntity.setNickName(cursor.getString(cursor.getColumnIndex(NICK_NAME)));
            friendEntity.setFriendAccount(cursor.getString(cursor.getColumnIndex(FRIEND_ACCOUNT)));
            friendEntity.setFriendSex(cursor.getString(cursor.getColumnIndex(FRIEND_SEX)));
            friendEntity.setFriendLocation(cursor.getString(cursor.getColumnIndex(FRIEND_LOCATION)));
            friendEntity.setFriendRecentPhoto(cursor.getString(cursor.getColumnIndex(FRIEND_RECENT_PHOTO)));
            db.close();
            return friendEntity;
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
            groupList.add(cursor.getString(cursor.getColumnIndex(GROUP_NAME)));
        }
        db.close();
        return groupList;
    }

    // 获取分组子列表
    public List<List<String>> getGrouopChildList() {
        List<String> parentList = getGroupList();
        List<List<String>> childList = new ArrayList<List<String>>();

        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<String> child;
        for(int i = 0;i < parentList.size();i++) {
            String sql = "SELECT * FROM friend_infor WHERE group_name=?";
            Cursor cursor = db.rawQuery(sql, new String[]{parentList.get(i)});

            child = new ArrayList<String>();
            while (cursor.moveToNext()) {
                child.add(cursor.getString(cursor.getColumnIndex(FRIEND_NAME)));
            }
            childList.add(child);
        }
        db.close();
        return childList;
    }

    // 获取新好友列表数据
    public List<Map<String, Object>> getNewFriendList() {
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询新好友列表
        String sql = "SELECT friend_head, friend_name, new_friend_request_msg FROM friend_infor WHERE new_friend_flag=?";
        Cursor cursor = db.rawQuery(sql, new String[]{""+1});

        // 将新好友列表数据遍历出来
        List<Map<String, Object>> newFriendList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        while(cursor.moveToNext()) {
            map = new HashMap<String, Object>();
            map.put(FRIEND_HEAD, cursor.getString(cursor.getColumnIndex(FRIEND_HEAD)));
            map.put(FRIEND_NAME, cursor.getString(cursor.getColumnIndex(FRIEND_NAME)));
            map.put(NEW_FRIEND_REQUEST_MSG, cursor.getString(cursor.getColumnIndex(NEW_FRIEND_REQUEST_MSG)));
            newFriendList.add(map);
        }
        return newFriendList;
    }

    public void initFriendTableDao(){
        //初始化，创建两条数据测试，后期可删掉
        String Sql = "insert into friend_infor(friend_id, group_name, friend_name, nick_name, friend_sex, friend_account, friend_head, friend_location, friend_recent_photo, new_friend_flag, new_friend_request_msg)"
                +"values(2, 'friend', '一本正经、', 'nick_name', '男', 'BLCHAT_01', 'R.drawable.head', '英德市', null, 1, '您好，我是您的老同学。')";

        //创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据
        db.execSQL(Sql);
        db.close();
    }
}
