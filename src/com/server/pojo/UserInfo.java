package com.server.pojo;

import com.th.mobile.collection.annotation.Excluded;


public class UserInfo  {

    private int id;

    /** 用户序号 */
    private int uid;
    
    /** 用户名 */
    private String userName;

    /** 密码 */
    private String pwd;

    /** 会话id，登录成功后由服务端设置 */
    private String sessionId;

    /** 用户状态 */
    private Integer state;

    /** 用户使用的apk版本号 */
    @Excluded
    private String apkVersion;

    /** 昵称 */
    private String nikeName;

    /** 个性签名 */
    private String sign;

    public UserInfo() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "UserInfo [id=" + id + ", uid=" + uid + ", userName=" + userName
                + ", pwd=" + pwd + ", sessionId=" + sessionId + ", state="
                + state + ", apkVersion=" + apkVersion + ", nikeName="
                + nikeName + ", sign=" + sign + "]";
    }

}
