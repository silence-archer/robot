package com.silence.robot.model;

import java.math.BigDecimal;
import java.util.Date;

public class TMemberTransDetail {

    /**
     * 主键
     */
    private String id;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 会员描述
     */
    private String transType;
    /**
     * 会员电话
     */
    private BigDecimal transAmount;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
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

    @Override
    public String toString() {
        return "TMemberTransDetail{" + "id='" + id + '\'' + ", memberName='" + memberName + '\'' + ", transType='"
            + transType + '\'' + ", transAmount=" + transAmount + ", remark='" + remark + '\'' + ", createUser='"
            + createUser + '\'' + ", createTime=" + createTime + ", updateUser='" + updateUser + '\'' + ", updateTime="
            + updateTime + '}';
    }
}
