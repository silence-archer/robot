package com.silence.robot.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CommemoratingInfoDto {
    private String id;

    private String commemoratingName;
    private String commemoratingDesc;
    private String imageName;
    private LocalDate commemoratingDate;

    private String commemoratingStory;




    private String createUser;


    private LocalDateTime createTime;


    private String updateUser;


    private LocalDateTime updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommemoratingName() {
        return commemoratingName;
    }

    public void setCommemoratingName(String commemoratingName) {
        this.commemoratingName = commemoratingName;
    }

    public String getCommemoratingDesc() {
        return commemoratingDesc;
    }

    public void setCommemoratingDesc(String commemoratingDesc) {
        this.commemoratingDesc = commemoratingDesc;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public LocalDate getCommemoratingDate() {
        return commemoratingDate;
    }

    public void setCommemoratingDate(LocalDate commemoratingDate) {
        this.commemoratingDate = commemoratingDate;
    }

    public String getCommemoratingStory() {
        return commemoratingStory;
    }

    public void setCommemoratingStory(String commemoratingStory) {
        this.commemoratingStory = commemoratingStory;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CommemoratingInfoDto{" + "id='" + id + '\'' + ", commemoratingName='" + commemoratingName + '\''
            + ", commemoratingDesc='" + commemoratingDesc + '\'' + ", imageName='" + imageName + '\''
            + ", commemoratingDate=" + commemoratingDate + ", commemoratingStory='" + commemoratingStory + '\''
            + ", createUser='" + createUser + '\'' + ", createTime=" + createTime + ", updateUser='" + updateUser + '\''
            + ", updateTime=" + updateTime + '}';
    }
}
