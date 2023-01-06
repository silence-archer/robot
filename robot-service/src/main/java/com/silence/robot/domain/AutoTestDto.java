package com.silence.robot.domain;

import java.util.Date;

public class AutoTestDto {
    /**
     * 主键
     */
    private String id;
    /**
     * 测试案例名称
     */
    private String testCaseName;
    /**
     * 测试案例描述
     */
    private String testCaseDesc;
    /**
     * 自动化测试任务名称
     */
    private String jobName;
    /**
     * 循环次数
     */
    private Integer loopNum;
    /**
     * 排序
     */
    private Integer sort;
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

    public String getTestCaseDesc() {
        return testCaseDesc;
    }

    public void setTestCaseDesc(String testCaseDesc) {
        this.testCaseDesc = testCaseDesc;
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

    public Integer getLoopNum() {
        return loopNum;
    }

    public void setLoopNum(Integer loopNum) {
        this.loopNum = loopNum;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "AutoTestDto{" + "id='" + id + '\'' + ", testCaseName='" + testCaseName + '\'' + ", testCaseDesc='"
            + testCaseDesc + '\'' + ", jobName='" + jobName + '\'' + ", loopNum=" + loopNum + ", sort=" + sort
            + ", createUser='" + createUser + '\'' + ", createTime=" + createTime + ", updateUser='" + updateUser + '\''
            + ", updateTime=" + updateTime + '}';
    }
}
