package com.silence.robot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class TCommemoratingInfo {

    private String id;

    private String commemoratingName;
    private String commemoratingDesc;
    private String imageName;
    private Date commemoratingDate;

    private String commemoratingStory;




    private String createUser;


    private Date createTime;


    private String updateUser;


    private Date updateTime;

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

    public Date getCommemoratingDate() {
        return commemoratingDate;
    }

    public void setCommemoratingDate(Date commemoratingDate) {
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



    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}