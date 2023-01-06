package com.silence.robot.domain;

import java.util.Date;

public class AutoTestJobDto {
    /**
     * 主键
     */
    private String id;
    /**
     * 自动化测试任务名称
     */
    private String jobName;
    /**
     * 自动化测试任务描述
     */
    private String jobDesc;
    /**
     * 自动运行标志
     */
    private String enableAutoFlag;
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

    public String getEnableAutoFlag() {
        return enableAutoFlag;
    }

    public void setEnableAutoFlag(String enableAutoFlag) {
        this.enableAutoFlag = enableAutoFlag;
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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    @Override
    public String toString() {
        return "TAutoTestJob{" + "id='" + id + '\'' + ", jobName='" + jobName + '\'' + ", jobDesc='" + jobDesc + '\''
            + ", enableAutoFlag='" + enableAutoFlag + '\'' + ", createUser='" + createUser + '\'' + ", createTime="
            + createTime + ", updateUser='" + updateUser + '\'' + ", updateTime=" + updateTime + '}';
    }
}
