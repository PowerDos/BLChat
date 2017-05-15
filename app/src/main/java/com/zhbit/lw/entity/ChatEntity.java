package com.zhbit.lw.entity;

import java.util.List;
import java.util.Map;

/**
 * ChatEntity: 聊天对象
 * Created by wjh on 17-5-13.
 */

public class ChatEntity {

    private int userId, friendId;
    private String userName, userHead, friendName, friendHead, chatMsgContent, chatMsgTime, chatMsgType;

    private List<Map<String, Object>> chatMsgData;      // ChatMsgActivity的聊天记录数据
    private List<Map<String, Object>> recentChatData;         // ChatFragment的最近聊天对象数据

//    // 默认聊天属性
//    public static final String USER_ID = "userId";
//    public static final String TARGET_ID = "targetId";
//    public static final String USER_NAME = "userName";
//    public static final String TARGET_NAME = "targetName";
//    public static final String USER_HEAD = "usreHead";
//    public static final String TARGET_HEAD = "targetHead";
//    public static final String CHAT_MSG_CONTENT = "chatMsgContent";
//
//    // ChatMsgActivity需要的信息
//    public static final String CONTENT = "content";
//    public static final string type = "type";
//    public static final string time = "time";
//
//    // chatfragment需要的信息
//    public static final string last_chat_time = "lastchattime";
//    public static final string last_chat_content = "lastchatcontent";
//    public static final string expand_relation = "expandrelation";

    public ChatEntity() {
    }

    public ChatEntity(int userId, String userName, String userHead, int friendId, String friendName, String friendHead
                      , String chatMsgContent, String chatMsgTime, String chatMsgType,
                      List<Map<String, Object>> chatMsgData, List<Map<String, Object>>recentChatData) {
        this.userId = userId;
        this.userName = userName;
        this.userHead = userHead;
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendHead = friendHead;
        this.chatMsgContent = chatMsgContent;
        this.chatMsgTime = chatMsgTime;
        this.chatMsgType = chatMsgType;
        this.chatMsgData = chatMsgData;
        this.recentChatData = recentChatData;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFriendId() {
        return friendId;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getFriendHead() {
        return friendHead;
    }

    public void setFriendHead(String friendHead) {
        this.friendHead = friendHead;
    }

    public String getChatMsgContent() {
        return chatMsgContent;
    }

    public void setChatMsgContent(String chatMsgContent) {
        this.chatMsgContent = chatMsgContent;
    }

    public String getChatMsgTime() {
        return chatMsgTime;
    }

    public void setChatMsgTime(String chatMsgTime) {
        this.chatMsgTime = chatMsgTime;
    }

    public String getChatMsgType() {
        return chatMsgType;
    }

    public void setChatMsgType(String chatMsgType) {
        this.chatMsgType = chatMsgType;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public List<Map<String, Object>> getChatMsgData() {
        return chatMsgData;
    }

    public void setChatMsgData(List<Map<String, Object>> chatMsgData) {
        this.chatMsgData = chatMsgData;
    }

    public List<Map<String, Object>> getRecentChatData() {
        return recentChatData;
    }

    public void setRecentChatData(List<Map<String, Object>> recentChatData) {
        this.recentChatData = recentChatData;
    }


    public int getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

}
