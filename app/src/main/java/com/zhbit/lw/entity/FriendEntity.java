package com.zhbit.lw.entity;

/**
 * Created by wjh on 17-5-14.
 */

public class FriendEntity {

    private String groupName, friendName, nickName, friendSex, friendAccount, friendHead, friendLocation, friendRecentPhoto;

    public FriendEntity(String groupName, String friendName, String nickName, String friendSex, String friendAccount, String friendHead, String friendLocation, String friendRecentPhoto) {
        this.groupName = groupName;
        this.friendName = friendName;
        this.nickName = nickName;
        this.friendSex = friendSex;
        this.friendAccount = friendAccount;
        this.friendHead = friendHead;
        this.friendLocation = friendLocation;
        this.friendRecentPhoto = friendRecentPhoto;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFriendSex() {
        return friendSex;
    }

    public void setFriendSex(String friendSex) {
        this.friendSex = friendSex;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFriendAccount() {
        return friendAccount;
    }

    public void setFriendAccount(String friendAccount) {
        this.friendAccount = friendAccount;
    }

    public String getFriendHead() {
        return friendHead;
    }

    public void setFriendHead(String friendHead) {
        this.friendHead = friendHead;
    }

    public String getFriendLocation() {
        return friendLocation;
    }

    public void setFriendLocation(String friendLocation) {
        this.friendLocation = friendLocation;
    }

    public String getFriendRecentPhoto() {
        return friendRecentPhoto;
    }

    public void setFriendRecentPhoto(String friendRecentPhoto) {
        this.friendRecentPhoto = friendRecentPhoto;
    }

}
