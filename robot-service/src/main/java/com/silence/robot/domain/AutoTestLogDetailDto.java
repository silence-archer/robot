package com.silence.robot.domain;

import java.util.Date;

public class AutoTestLogDetailDto {

    /**
     * 主键
     */
    private String id;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 接口场景id
     */
    private String sceneId;

    /**
     * 业务类型-环境地址
     */
    private String businessType;

    /**
     * 响应码
     */
    private String responseCode;

    /**
     * 响应信息
     */
    private String responseMessage;

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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
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
        return "AutoTestLogDetailDto{" + "id='" + id + '\'' + ", batchNo='" + batchNo + '\'' + ", sceneId='" + sceneId
            + '\'' + ", responseCode='" + responseCode + '\'' + ", responseMessage='" + responseMessage + '\''
            + ", createUser='" + createUser + '\'' + ", createTime=" + createTime + ", updateUser='" + updateUser + '\''
            + ", updateTime=" + updateTime + '}';
    }
}
