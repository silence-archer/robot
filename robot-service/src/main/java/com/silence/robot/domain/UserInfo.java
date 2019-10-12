package com.silence.robot.domain;

public class UserInfo {
    private String username;
    private String password;

    private String imageCode;

    private String nickname;

    private String imageWithVerifyCode;

    private String id;



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

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", imageCode='" + imageCode + '\'' +
                ", nickname='" + nickname + '\'' +
                ", imageWithVerifyCode='" + imageWithVerifyCode + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
