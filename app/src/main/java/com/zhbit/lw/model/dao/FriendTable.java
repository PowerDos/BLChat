package com.zhbit.lw.model.dao;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class FriendTable {
    //这里写好友的表
    public static final String TableName = "Friend";
    public static final String RealName = "realname";  //真实姓名,备注用
    public static final String NickName = "nickname";  //昵称
    public static final String UserId = "userid"; //账号
    public static final String HeadPhoto = "headphoto"; //头像
    public static final String Address = "address"; //地址

    public static final String CreateTable = "create table "
            + TableName + " ("
            + "[_id] integer autoinc primary key,"
            + RealName + " varchar(32),"
            + NickName + " varchar(32),"
            + UserId + " varchar(32),"
            + HeadPhoto + " varchar(108),"
            + Address + " varchar(64));";
}
