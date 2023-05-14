package com.silence.robot.domain.member;

import java.math.BigDecimal;
import java.util.Date;

public class MemberTransDetailDto {

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
    private String memberDesc;
    /**
     * 会员电话
     */
    private String memberPhone;
    /**
     * 交易类型
     */
    private String transType;
    /**
     * 交易金额
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

    public String getMemberDesc() {
        return memberDesc;
    }

    public void setMemberDesc(String memberDesc) {
        this.memberDesc = memberDesc;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    @Override
    public String toString() {
        return "MemberTransDetailDto{" + "id='" + id + '\'' + ", memberName='" + memberName + '\'' + ", memberDesc='"
            + memberDesc + '\'' + ", memberPhone='" + memberPhone + '\'' + ", transType='" + transType + '\''
            + ", transAmount=" + transAmount + ", remark='" + remark + '\'' + ", createUser='" + createUser + '\''
            + ", createTime=" + createTime + ", updateUser='" + updateUser + '\'' + ", updateTime=" + updateTime + '}';
    }
}
