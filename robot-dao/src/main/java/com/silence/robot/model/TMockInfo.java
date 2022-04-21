package com.silence.robot.model;

import java.util.Date;

public class TMockInfo {

    private String id;


    private String mockName;


    private String mockModule;


    private String mockUrl;


    private String mockInput;

    private String mockOutput;

    private String mockStatus;


    private Date createTime;


    private String createUser;


    private Date updateTime;


    private String updateUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMockName() {
        return mockName;
    }

    public void setMockName(String mockName) {
        this.mockName = mockName;
    }

    public String getMockModule() {
        return mockModule;
    }

    public void setMockModule(String mockModule) {
        this.mockModule = mockModule;
    }

    public String getMockUrl() {
        return mockUrl;
    }

    public void setMockUrl(String mockUrl) {
        this.mockUrl = mockUrl;
    }

    public String getMockInput() {
        return mockInput;
    }

    public void setMockInput(String mockInput) {
        this.mockInput = mockInput;
    }

    public String getMockOutput() {
        return mockOutput;
    }

    public void setMockOutput(String mockOutput) {
        this.mockOutput = mockOutput;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getMockStatus() {
        return mockStatus;
    }

    public void setMockStatus(String mockStatus) {
        this.mockStatus = mockStatus;
    }

    @Override
    public String toString() {
        return "TMockInfo{" +
                "id='" + id + '\'' +
                ", mockName='" + mockName + '\'' +
                ", mockModule='" + mockModule + '\'' +
                ", mockUrl='" + mockUrl + '\'' +
                ", mockInput='" + mockInput + '\'' +
                ", mockOutput='" + mockOutput + '\'' +
                ", mockStatus='" + mockStatus + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}