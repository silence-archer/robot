package com.silence.robot.model;

import java.util.Date;

public class TMerchantInfo {

    /**
     * 主键
     */
    private String id;
    /**
     * 经销商名称
     */
    private String businessName;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户描述
     */
    private String merchantDesc;
    /**
     * 商户电话
     */
    private String merchantPhone;
    /**
     * 商户地址
     */
    private String merchantAddress;
    /**
     * 商户操作员
     */
    private String merchantOperator;
    /**
     * 备注
     */
    private String remark;

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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantDesc() {
        return merchantDesc;
    }

    public void setMerchantDesc(String merchantDesc) {
        this.merchantDesc = merchantDesc;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getMerchantOperator() {
        return merchantOperator;
    }

    public void setMerchantOperator(String merchantOperator) {
        this.merchantOperator = merchantOperator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    @Override
    public String toString() {
        return "TMerchantInfo{" + "id='" + id + '\'' + ", businessName='" + businessName + '\'' + ", merchantName='"
            + merchantName + '\'' + ", merchantDesc='" + merchantDesc + '\'' + ", merchantPhone='" + merchantPhone
            + '\'' + ", merchantAddress='" + merchantAddress + '\'' + ", merchantOperator='" + merchantOperator + '\''
            + ", remark='" + remark + '\'' + ", createUser='" + createUser + '\'' + ", createTime=" + createTime
            + ", updateUser='" + updateUser + '\'' + ", updateTime=" + updateTime + '}';
    }
}
