package com.zhbit.lw.model.dao;

/**
 * Created by fl5900 on 2017/5/19.
 * 邀请表
 */

public class InvitationTable {
    public static String ACCOUNT = "account";
    public static String REASON = "reason";
    public static String STATUS = "status";
    public static String TABLE_NAME = "invitation";
    public static String CREATE_TABLE = "create table "
            + TABLE_NAME + " ("
            + "[_id] integer autoinc primary key,"
            + ACCOUNT + " varchar(32),"
            + REASON + " varchar(32),"
            + STATUS +" integer);";
}
