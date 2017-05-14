package com.zhbit.lw.model.dao;

/**
 * Created by wjh on 17-5-14.
 */

public class UserTable {

    public static final String TABLE_NAME = "user_infor";
    public static final String USER_NAME = "user_name";
    public static final String USER_HEAD = "user_head";
    public static final String USER_SEX = "user_sex";
    public static final String USER_SIGN = "user_sign";
    public static final String USER_ACCOUNT = "user_account";
    public static final String USER_LOCATION = "user_location";

    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + " ("
            + "_id integer autoinc primary key,"
            + USER_NAME + " varchar(32),"
            + USER_HEAD + " varchar(32),"
            + USER_SEX + " varchar(4),"
            + USER_SIGN + " varchar(64),"
            + USER_ACCOUNT + " varchar(32),"
            + USER_LOCATION + " varchar(20));";

}
