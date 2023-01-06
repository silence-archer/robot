package com.silence.robot.domain;

import java.util.Date;

public class AutoTestDetailDto {
    /**
     * 主键
     */
    private String id;
    /**
     * 测试案例名称
     */
    private String testCaseName;
    /**
     * 测试案例名称
     */
    private String testCaseDesc;
    /**
     * 交易码
     */
    private String tranCode;
    /**
     * 交易描述
     */
    private String tranDesc;
    /**
     * 接口场景id
     */
    private String sceneId;
    /**
     * 接口场景描述
     */
    private String sceneType;
    /**
     * 接口场景描述
     */
    private String sceneDesc;

    /**
     * 业务类型-环境地址
     */
    private String businessType;
    /**
     * 业务类型描述
     */
    private String businessTypeDesc;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 间隔时间
     */
    private Integer delayTime;

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

    public String getTestCaseDesc() {
        return testCaseDesc;
    }

    public void setTestCaseDesc(String testCaseDesc) {
        this.testCaseDesc = testCaseDesc;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getTranDesc() {
        return tranDesc;
    }

    public void setTranDesc(String tranDesc) {
        this.tranDesc = tranDesc;
    }

    public String getSceneDesc() {
        return sceneDesc;
    }

    public void setSceneDesc(String sceneDesc) {
        this.sceneDesc = sceneDesc;
    }

    public String getBusinessTypeDesc() {
        return businessTypeDesc;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public void setBusinessTypeDesc(String businessTypeDesc) {
        this.businessTypeDesc = businessTypeDesc;
    }

    public String getId() {
        return id;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
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
        return "AutoTestDetailDto{" + "id='" + id + '\'' + ", testCaseName='" + testCaseName + '\'' + ", testCaseDesc='"
            + testCaseDesc + '\'' + ", tranCode='" + tranCode + '\'' + ", tranDesc='" + tranDesc + '\'' + ", sceneId='"
            + sceneId + '\'' + ", sceneType='" + sceneType + '\'' + ", sceneDesc='" + sceneDesc + '\''
            + ", businessType='" + businessType + '\'' + ", businessTypeDesc='" + businessTypeDesc + '\'' + ", sort="
            + sort + ", delayTime=" + delayTime + ", createUser='" + createUser + '\'' + ", createTime=" + createTime
            + ", updateUser='" + updateUser + '\'' + ", updateTime=" + updateTime + '}';
    }
}
