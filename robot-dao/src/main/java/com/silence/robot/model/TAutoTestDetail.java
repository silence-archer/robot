package com.silence.robot.model;

import java.util.Date;


public class TAutoTestDetail {
    /**
     * 主键
     */
    private String id;
    /**
     * 测试案例名称
     */
    private String testCaseName;
    /**
     * 接口场景id
     */
    private String sceneId;
    /**
     * 场景类型
     */
    private String sceneType;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 间隔时间
     */
    private Integer delayTime;

    /**
     * 业务类型-环境地址
     */
    private String businessType;

    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TAutoTestDetail{" + "id='" + id + '\'' + ", testCaseName='" + testCaseName + '\'' + ", sceneId='"
            + sceneId + '\'' + ", sceneType='" + sceneType + '\'' + ", sort=" + sort + ", delayTime=" + delayTime
            + ", businessType='" + businessType + '\'' + ", createUser='" + createUser + '\'' + ", createTime="
            + createTime + ", updateUser='" + updateUser + '\'' + ", updateTime=" + updateTime + '}';
    }
}
