package com.silence.robot.domain;

/**
 * TODO
 *
 * @author silence
 * @date 2021/6/28
 */
public class DatabaseInfoDto {

    /**
     * 数据库类型
     */
    private String type;

    /**
     * 数据库链接
     */
    private String url;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    /**
     * 业务类型
     */
    private String businessType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
