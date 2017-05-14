package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.entity.FriendEntity;
import com.zhbit.lw.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

import static com.zhbit.lw.model.dao.FriendTable.FRIEND_ACCOUNT;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_HEAD;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_LOCATION;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_RECENT_PHOTO;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_SEX;
import static com.zhbit.lw.model.dao.FriendTable.GROUP_NAME;
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
    public FriendEntity getFriendInforByUserName(String friendName){
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询语句
//        String Sql = "select * from friend_infor where friend_name = '" + friendName + "'";
//        String Sql = "select * from friend_infor where friend_name = '一本正经、'";
        String Sql = "select * from friend_infor where friend_name = ?";
        Cursor cursor = db.rawQuery(Sql, new String[]{"一本正经、"});
        //获取数据
        while (cursor.moveToNext()) {
            String groupName = cursor.getString(cursor.getColumnIndex(GROUP_NAME));
            friendName = cursor.getString(cursor.getColumnIndex(FRIEND_NAME));
            String nickName = cursor.getString(cursor.getColumnIndex(NICK_NAME));
            String friendSex = cursor.getString(cursor.getColumnIndex(FRIEND_SEX));
            String friendAccount = cursor.getString(cursor.getColumnIndex(FRIEND_ACCOUNT));
            String friendHead = cursor.getString(cursor.getColumnIndex(FRIEND_HEAD));
            String friendLocation = cursor.getString(cursor.getColumnIndex(FRIEND_LOCATION));
            String friendRecentPhoto = cursor.getString(cursor.getColumnIndex(FRIEND_RECENT_PHOTO));

            FriendEntity friendEntity = new FriendEntity(groupName, friendName, nickName, friendSex, friendAccount, friendHead, friendLocation, friendRecentPhoto);
            db.close();
            return friendEntity;
        }
        return null;
    }

    public List<String> getGroupList() {
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT DISTINCT group_name FROM friend_infor GROUP BY group_name";
        Cursor cursor = db.rawQuery(sql, null);

        List<String> groupList = new ArrayList<String>();
        while(cursor.moveToNext()) {
            groupList.add(cursor.getString(cursor.getColumnIndex(FRIEND_NAME)));
        }
        db.close();
        return groupList;
    }

    public List<List<String>> getGrouopChildList() {
        List<String> parentList = new ArrayList<String>();
        List<List<String>> childList = new ArrayList<List<String>>();

        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        for(int i = 0;i < parentList.size();i++) {
            String sql = "SELECT friend_name, friend_head FROM friend_infor WHERE group_name = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{parentList.get(i)});
            childList.get(i).add(cursor.getString(cursor.getColumnIndex(FRIEND_NAME)));
        }
        db.close();
        return childList;
    }

    public void initFriendTableDao(){
        //初始化，创建两条数据测试，后期可删掉
        String Sql = "insert into friend_infor(group_name, friend_name, nick_name, friend_sex, friend_account, friend_head, friend_location, friend_recent_photo)"
                +"values('friend', '一本正经、', 'nick_name', '男', 'BLCHAT_01', 'R.drawable.head', '英德市', null)";

        //创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入数据
        db.execSQL(Sql);
        db.close();
    }
}
