package com.zhbit.lw.model.dao;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class FriendTable {

    public static final String TABLE_NAME = "friend_infor";

    public static final String USER_ID = "user_id";
    public static final String FRIEND_ID = "friend_id";
    public static final String GROUP_NAME = "group_name";
    public static final String FRIEND_NAME = "friend_name";
    public static final String NICK_NAME = "nick_name";
    public static final String FRIEND_SEX = "friend_sex";
    public static final String FRIEND_ACCOUNT = "friend_account";
    public static final String FRIEND_HEAD = "friend_head";
    public static final String FRIEND_LOCATION = "friend_location";
    public static final String FRIEND_RECENT_PHOTO = "friend_recent_photo";
    public static final String NEW_FRIEND_FLAG = "new_friend_flag";
    public static final String NEW_FRIEND_REQUEST_MSG = "new_friend_request_msg";

    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME + " ("
            + "[_id] integer autoinc primary key,"
            + USER_ID + " integer,"
            + FRIEND_ID + " integer,"
            + GROUP_NAME + " varchar(32),"
            + FRIEND_NAME + " varchar(32),"
            + NICK_NAME + " varchar(32),"
            + FRIEND_SEX + " varchar(4),"
            + FRIEND_ACCOUNT + " varchar(32),"
            + FRIEND_HEAD + " varchar(108),"
            + FRIEND_RECENT_PHOTO + " varchar(108),"
            + FRIEND_LOCATION + " varchar(64),"
            + NEW_FRIEND_FLAG + " integer,"
            + NEW_FRIEND_REQUEST_MSG + " varchar(50));";
}
