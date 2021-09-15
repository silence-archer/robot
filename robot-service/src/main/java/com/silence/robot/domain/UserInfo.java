package com.silence.robot.domain;

import com.alibaba.fastjson.JSONObject;


public class UserInfo {

    /**
     * 用户登录名
     */
    private String username;

    /**
     * 用户登录密码
     */
    private String password;

    /**
     * 用户登录验证码
     */
    private String imageCode;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 角色代码
     */
    private String roleNo;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * IP地址
     */
    private String ipAddr;

    /**
     * 验证码生成的图片信息
     */
    private String imageWithVerifyCode;

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户创建时间
     */
    private String createTime;

    /**
     * 我的签名
     */
    private String sign;

    /**
     * 我的头像
     */
    private String avatar;

    public UserInfo(){

    }


    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImageWithVerifyCode() {
        return imageWithVerifyCode;
    }

    public void setImageWithVerifyCode(String imageWithVerifyCode) {
        this.imageWithVerifyCode = imageWithVerifyCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", imageCode='" + imageCode + '\'' +
                ", nickname='" + nickname + '\'' +
                ", roleNo='" + roleNo + '\'' +
                ", roleName='" + roleName + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", imageWithVerifyCode='" + imageWithVerifyCode + '\'' +
                ", id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", sign='" + sign + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public String toJSONString() {
        return JSONObject.toJSONString(this);
    }
}
