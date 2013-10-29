package com.server.online;

public class OnlineUser {

    /** 用户编号 */
    private String userId;

    /** 登录时间 */
    private String loginDate;

    /** 会话id */
    private String sessionId;

    /** 用户名 */
    private String userName;

    /** 用户最后一次访问时间 */
    private Long lastAccessTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Override
    public String toString() {
        return "OnlineUser [ lastAccessTime=" + lastAccessTime + ", loginDate="
                + loginDate + ", sessionId=" + sessionId + ", userId=" + userId
                + ", userName=" + userName + "]";
    }

}
