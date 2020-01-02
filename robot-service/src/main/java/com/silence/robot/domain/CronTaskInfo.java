/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CronTaskInfo
 * Author:   silence
 * Date:     2019/12/27 14:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @create 2019/12/27
 * @since 1.0.0
 */
public class CronTaskInfo {

    private String id;

    private String jobName;

    private String cronExpr;

    private String jobDesc;

    private String jobClass;

    private String effectFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getEffectFlag() {
        return effectFlag;
    }

    public void setEffectFlag(String effectFlag) {
        this.effectFlag = effectFlag;
    }

    @Override
    public String toString() {
        return "CronTaskInfo{" +
                "id='" + id + '\'' +
                ", jobName='" + jobName + '\'' +
                ", cronExpr='" + cronExpr + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                ", jobClass='" + jobClass + '\'' +
                ", effectFlag='" + effectFlag + '\'' +
                '}';
    }
}