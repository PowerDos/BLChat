package com.zhbit.lw.model.bean;

import java.util.List;
import java.util.Map;

/**
 * ChatInfo: 聊天对象
 * Created by wjh on 17-5-13.
 */

public class ChatInfo {

    // 聊天的基本属性
    private int userId, friendId;
    private String userName, userHead, friendName, friendHead, friendExpandRelation;

    private List<Map<String, Object>> chatMsgData;      // ChatMsgActivity的聊天记录数据
    private List<Map<String, Object>> recentChatData;         // ChatFragment的最近聊天对象数据

    public ChatInfo() {
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFriendExpandRelation() {
        return friendExpandRelation;
    }

    public void setFriendExpandRelation(String friendExpandRelation) {
        this.friendExpandRelation = friendExpandRelation;
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
