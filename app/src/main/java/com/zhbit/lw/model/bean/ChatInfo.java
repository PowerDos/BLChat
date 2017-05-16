package com.zhbit.lw.model.bean;

import java.util.List;
import java.util.Map;

/**
 * ChatInfo: 聊天对象
 * Created by wjh on 17-5-13.
 */

public class ChatInfo {

    private int userId, targetId;
    private String userName, targetName;

    private List<Map<String, Object>> chatMsgData;      // ChatMsgActivity的聊天记录数据
    private List<Map<String, Object>> recentChatData;         // ChatFragment的最近聊天对象数据

    // 默认聊天属性
    public static final String USER_ID = "userId";
    public static final String TARGET_ID = "targetId";
    public static final String USER_NAME = "userName";
    public static final String TARGET_NAME = "targetName";
    public static final String USER_HEAD = "usreHead";
    public static final String TARGET_HEAD = "targetHead";
    public static final String CHAT_MSG_CONTENT = "chatMsgContent";

    // ChatMsgActivity需要的信息
    public static final String CONTENT = "content";
    public static final String TYPE = "type";
    public static final String TIME = "time";

    // ChatFragment需要的信息
    public static final String LAST_CHAT_TIME = "lastChatTime";
    public static final String LAST_CHAT_CONTENT = "lastChatContent";
    public static final String EXPAND_RELATION = "expandRelation";

    // 测试使用
    public ChatInfo() {
    }

    // 根据目标ID获得最后一次聊天信息　用于ChatFragment
    public ChatInfo(int targetId) {
        this.targetId = targetId;
    }

    // 根据两个ID获得所有聊天信息　用于ChatMsgActivity
    public ChatInfo(int userId, int targetId) {
        this.userId = userId;
        this.targetId = targetId;
    }

    // 测试使用
    public ChatInfo(int userId, String userName, int targetId, String targetName, List<Map<String, Object>> chatMsgData, List<Map<String, Object>>recentChatData) {
        this.userId = userId;
        this.userName = userName;
        this.targetId = targetId;
        this.targetName = targetName;
        this.chatMsgData = chatMsgData;
        this.recentChatData = recentChatData;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public void setChatContent(List<Map<String, Object>> chatMsgData) {
        this.chatMsgData = chatMsgData;
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

    public int getTargetId() {
        return this.targetId;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public List<Map<String, Object>> getChatContent() {
        return this.chatMsgData;
    }

    public List<Map<String, Object>> getRecentChatData() {
        return this.recentChatData;
    }

}
