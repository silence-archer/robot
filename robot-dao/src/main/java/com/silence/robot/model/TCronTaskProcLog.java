package com.silence.robot.model;

import java.util.Date;

public class TCronTaskProcLog {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_cron_task_proc_log.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_cron_task_proc_log.job_name
     *
     * @mbg.generated
     */
    private String jobName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_cron_task_proc_log.job_desc
     *
     * @mbg.generated
     */
    private String jobDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_cron_task_proc_log.proc_status
     *
     * @mbg.generated
     */
    private String procStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_cron_task_proc_log.error_msg
     *
     * @mbg.generated
     */
    private String errorMsg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_cron_task_proc_log.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_cron_task_proc_log.create_user
     *
     * @mbg.generated
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_cron_task_proc_log.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_cron_task_proc_log.update_user
     *
     * @mbg.generated
     */
    private String updateUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_cron_task_proc_log.ID
     *
     * @return the value of t_cron_task_proc_log.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_cron_task_proc_log.ID
     *
     * @param id the value for t_cron_task_proc_log.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_cron_task_proc_log.job_name
     *
     * @return the value of t_cron_task_proc_log.job_name
     *
     * @mbg.generated
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_cron_task_proc_log.job_name
     *
     * @param jobName the value for t_cron_task_proc_log.job_name
     *
     * @mbg.generated
     */
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_cron_task_proc_log.job_desc
     *
     * @return the value of t_cron_task_proc_log.job_desc
     *
     * @mbg.generated
     */
    public String getJobDesc() {
        return jobDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_cron_task_proc_log.job_desc
     *
     * @param jobDesc the value for t_cron_task_proc_log.job_desc
     *
     * @mbg.generated
     */
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc == null ? null : jobDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_cron_task_proc_log.proc_status
     *
     * @return the value of t_cron_task_proc_log.proc_status
     *
     * @mbg.generated
     */
    public String getProcStatus() {
        return procStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_cron_task_proc_log.proc_status
     *
     * @param procStatus the value for t_cron_task_proc_log.proc_status
     *
     * @mbg.generated
     */
    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus == null ? null : procStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_cron_task_proc_log.error_msg
     *
     * @return the value of t_cron_task_proc_log.error_msg
     *
     * @mbg.generated
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_cron_task_proc_log.error_msg
     *
     * @param errorMsg the value for t_cron_task_proc_log.error_msg
     *
     * @mbg.generated
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_cron_task_proc_log.create_time
     *
     * @return the value of t_cron_task_proc_log.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_cron_task_proc_log.create_time
     *
     * @param createTime the value for t_cron_task_proc_log.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_cron_task_proc_log.create_user
     *
     * @return the value of t_cron_task_proc_log.create_user
     *
     * @mbg.generated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_cron_task_proc_log.create_user
     *
     * @param createUser the value for t_cron_task_proc_log.create_user
     *
     * @mbg.generated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_cron_task_proc_log.update_time
     *
     * @return the value of t_cron_task_proc_log.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_cron_task_proc_log.update_time
     *
     * @param updateTime the value for t_cron_task_proc_log.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_cron_task_proc_log.update_user
     *
     * @return the value of t_cron_task_proc_log.update_user
     *
     * @mbg.generated
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_cron_task_proc_log.update_user
     *
     * @param updateUser the value for t_cron_task_proc_log.update_user
     *
     * @mbg.generated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}