package com.silence.robot.domain.member;

import java.util.Date;

public class BusinessInfoDto {

    /**
     * 主键
     */
    private String id;
    /**
     * 经销商名称
     */
    private String businessName;
    /**
     * 经销商描述
     */
    private String businessDesc;
    /**
     * 经销商电话
     */
    private String businessPhone;
    /**
     * 经销商操作员
     */
    private String businessOperator;
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

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

    public String getBusinessOperator() {
        return businessOperator;
    }

    public void setBusinessOperator(String businessOperator) {
        this.businessOperator = businessOperator;
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

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    @Override
    public String toString() {
        return "TBusinessInfo{" + "id='" + id + '\'' + ", businessName='" + businessName + '\'' + ", businessDesc='"
            + businessDesc + '\'' + ", businessPhone='" + businessPhone + '\'' + ", businessOperator='"
            + businessOperator + '\'' + ", remark='" + remark + '\'' + ", createUser='" + createUser + '\''
            + ", createTime=" + createTime + ", updateUser='" + updateUser + '\'' + ", updateTime=" + updateTime + '}';
    }
}
