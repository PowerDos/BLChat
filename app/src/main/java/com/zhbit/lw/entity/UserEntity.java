package com.zhbit.lw.entity;

/**
 * Created by wjh on 17-5-13.
 */

public class UserEntity {

    private int userId;
    private String userName, UserAccount, userLocation;

    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String USER_LOCATION = "userLocation";

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return UserAccount;
    }

    public void setUserAccount(String userAccount) {
        UserAccount = userAccount;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public UserEntity(int userId, String userName, String usreAccount, String userLocation) {
        this.userId = userId;
        this.userName = userName;
        this.userLocation = userLocation;
    }



}
