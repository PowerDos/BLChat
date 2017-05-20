package com.zhbit.lw.model.bean;

/**
 * Created by fl5900 on 2017/5/19.
 */

public class InvitationInfo {
    private String Account;
    private String Reason;
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

}
