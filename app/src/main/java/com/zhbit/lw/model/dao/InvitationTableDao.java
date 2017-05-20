package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.model.bean.InvitationInfo;
import com.zhbit.lw.model.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fl5900 on 2017/5/19.
 */

public class InvitationTableDao {
    private DBHelper dbHelper;

    public InvitationTableDao(DBHelper helper){
        dbHelper = helper;
    }

    public void addInvitation(InvitationInfo invitationInfo){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String Sql = "insert into invitation(account,reason,status) values('"
                + invitationInfo.getAccount()+"','"
                + invitationInfo.getReason()+"',1);";
        db.execSQL(Sql);
        db.close();
    }

    public List<Map<String, Object>> getInvitationInfo(){
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询新好友列表
        String sql = "SELECT * FROM invitation WHERE status=1 ORDER BY _id DESC";
        Cursor cursor = db.rawQuery(sql, null);
        // 将新好友列表数据遍历出来
        List<Map<String, Object>> newFriendList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        while(cursor.moveToNext()) {
            map = new HashMap<String, Object>();
            map.put(InvitationTable.ACCOUNT, cursor.getString(cursor.getColumnIndex(InvitationTable.ACCOUNT)));
            map.put(InvitationTable.REASON, cursor.getString(cursor.getColumnIndex(InvitationTable.REASON)));
            newFriendList.add(map);
        }
        return newFriendList;
    }

    public void setAdded(String Account){
        //创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //更新语句
        String sql = "update invitation set status=0 where account='"+Account+"'";
        //更新
        db.execSQL(sql);
        //关闭
        db.close();
    }
}
