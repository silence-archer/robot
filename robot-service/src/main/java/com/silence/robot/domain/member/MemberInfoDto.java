package com.silence.robot.domain.member;

import java.math.BigDecimal;
import java.util.Date;

public class MemberInfoDto {

    /**
     * 主键
     */
    private String id;
    /**
     * 商户名称
     */
    private String merchantName;
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
     * 会员地址
     */
    private String memberAddress;

    /**
     * 会员余额
     */
    private BigDecimal memberBalance;
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
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

    public BigDecimal getMemberBalance() {
        return memberBalance;
    }

    public void setMemberBalance(BigDecimal memberBalance) {
        this.memberBalance = memberBalance;
    }

    @Override
    public String toString() {
        return "TMemberInfo{" + "id='" + id + '\'' + ", merchantName='" + merchantName + '\'' + ", memberName='"
            + memberName + '\'' + ", memberDesc='" + memberDesc + '\'' + ", memberPhone='" + memberPhone + '\''
            + ", memberAddress='" + memberAddress + '\'' + ", memberBalance=" + memberBalance + ", remark='" + remark
            + '\'' + ", createUser='" + createUser + '\'' + ", createTime=" + createTime + ", updateUser='" + updateUser
            + '\'' + ", updateTime=" + updateTime + '}';
    }
}
