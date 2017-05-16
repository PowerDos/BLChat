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
    public void initMomentTableDao(){
        //初始化，创建两条数据测试，后期可删掉
        String Sql = "insert into moment(friendname,friendid,headphoto,publishtime,publishtext,publishImg)"
                        +"values('Gavin Lin','liz0607','@drawable/head','2017/5/12','富强、民主、文明、和谐，倡导自由"
                        + "、平等、公正、法治，倡导爱国、敬业、诚信、友善','@drawable/app_icon')";
        //创建数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入两条数据
        db.execSQL(Sql);
        db.execSQL(Sql);
        db.close();
    }
}
