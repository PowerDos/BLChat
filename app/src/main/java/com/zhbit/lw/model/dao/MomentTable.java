package com.zhbit.lw.model.dao;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class MomentTable {
    public static final String TableName = "moment"; //表面
    public static final String FriendName = "friendname"; //朋友的名字
    public static final String FriendId = "friendid"; //朋友的账号
    public static final String HeadPhoto = "headphoto"; //头像
    public static final String PublishTime = "publishtime"; //发布时间
    public static final String PublishText = "publishtext"; //发布内容
    public static final String PublishImg = "PublishImg";
    //评论暂未做，目前只先做发布一张图片
    public static final String CreateTable = "create table"
            + TableName + "("
            + "_id integer autoinc primary key,"
            + FriendName + "varchar(32),"
            + FriendId + "varchar(32),"
            + PublishTime + "varchar(32),"
            + HeadPhoto + "varchar(108),"
            + PublishImg + "varchar(108),"
            + PublishText + "text);";
}
