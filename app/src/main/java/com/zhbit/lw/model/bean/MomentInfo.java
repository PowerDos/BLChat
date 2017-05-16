package com.zhbit.lw.model.bean;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class MomentInfo {
    private String FriendName; //朋友的名字
    private String FriendId; //朋友的账号
    private String HeadPhoto; //头像
    private String PublishTime; //发布时间
    private String PublishText; //发布内容
    private String PublishImg; //发布的图片

    public String getFriendName() {
        return FriendName;
    }

    public void setFriendName(String friendName) {
        FriendName = friendName;
    }

    public String getFriendId() {
        return FriendId;
    }

    public void setFriendId(String friendId) {
        FriendId = friendId;
    }

    public String getHeadPhoto() {
        return HeadPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        HeadPhoto = headPhoto;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public String getPublishText() {
        return PublishText;
    }

    public void setPublishText(String publishText) {
        PublishText = publishText;
    }

    public String getPublishImg() {
        return PublishImg;
    }

    public void setPublishImg(String publishImg) {
        PublishImg = publishImg;
    }
}
