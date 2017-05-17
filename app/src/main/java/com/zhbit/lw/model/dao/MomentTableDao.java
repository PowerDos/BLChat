package com.zhbit.lw.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.model.bean.MomentInfo;
import com.zhbit.lw.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class MomentTableDao {

    private DBHelper dbHelper;
    public MomentTableDao(DBHelper helper){
        dbHelper = helper;
    }

    // 获取全部朋友圈
    // 目前先获取全部朋友圈，后期再改成15条15条获取
    public List<MomentInfo> getAllMomentInfo(){
        List<MomentInfo> momentInfoList = new ArrayList<>();
        //创建数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询语句
        String Sql = "select * from moment";
        Cursor cursor = db.rawQuery(Sql, null);
        //获取数据
        while (cursor.moveToNext()){
            MomentInfo momentInfo = new MomentInfo();
            momentInfo.setFriendName(cursor.getString(cursor.getColumnIndex(MomentTable.FriendName)));
            momentInfo.setFriendId(cursor.getString(cursor.getColumnIndex(MomentTable.FriendId)));
            momentInfo.setHeadPhoto(cursor.getString(cursor.getColumnIndex(MomentTable.HeadPhoto)));
            momentInfo.setPublishTime(cursor.getString(cursor.getColumnIndex(MomentTable.PublishTime)));
            momentInfo.setPublishText(cursor.getString(cursor.getColumnIndex(MomentTable.PublishText)));
            momentInfo.setPublishImg(cursor.getString(cursor.getColumnIndex(MomentTable.PublishImg)));
            momentInfoList.add(momentInfo);
        }
        db.close();
        return momentInfoList;
    }

}
