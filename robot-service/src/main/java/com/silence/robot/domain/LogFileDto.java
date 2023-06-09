package com.silence.robot.domain;

import java.util.Date;

/**
 * 文件配置DTO
 *
 * @author silence
 * @since 2021/2/4
 */
public class LogFileDto {
    /**
     * id
     */
    private String id;
    /**
     * 日志时间
     */
    private Date dateTime;

    /**
     * 系统名称
     */
    private String serviceName;

    /**
     * 流水号
     */
    private String traceId;

    /**
     * 交易码
     */
    private String tranCode;

    /**
     * 线程名称
     */
    private String threadName;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 类名称
     */
    private String className;

    /**
     * 行号
     */
    private Integer lineNum;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 子流水号
     */
    private String subTraceId;
    /**
     * 业务类型
     */
    private String businessType;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSubTraceId() {
        return subTraceId;
    }

    public void setSubTraceId(String subTraceId) {
        this.subTraceId = subTraceId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LogFileDto{" +
                "id='" + id + '\'' +
                ", dateTime=" + dateTime +
                ", serviceName='" + serviceName + '\'' +
                ", traceId='" + traceId + '\'' +
                ", tranCode='" + tranCode + '\'' +
                ", threadName='" + threadName + '\'' +
                ", level='" + level + '\'' +
                ", className='" + className + '\'' +
                ", lineNum=" + lineNum +
                ", content='" + content + '\'' +
                ", subTraceId='" + subTraceId + '\'' +
                ", businessType='" + businessType + '\'' +
                '}';
    }
}
