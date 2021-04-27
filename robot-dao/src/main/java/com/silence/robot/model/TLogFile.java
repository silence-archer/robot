package com.silence.robot.model;

import java.io.Serializable;
import java.util.Date;

/**
 * t_log_file
 * @author 
 */
public class TLogFile implements Serializable {
    /**
     * 物理主键
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

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSubTraceId() {
        return subTraceId;
    }

    public void setSubTraceId(String subTraceId) {
        this.subTraceId = subTraceId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TLogFile other = (TLogFile) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDateTime() == null ? other.getDateTime() == null : this.getDateTime().equals(other.getDateTime()))
            && (this.getServiceName() == null ? other.getServiceName() == null : this.getServiceName().equals(other.getServiceName()))
            && (this.getTraceId() == null ? other.getTraceId() == null : this.getTraceId().equals(other.getTraceId()))
            && (this.getTranCode() == null ? other.getTranCode() == null : this.getTranCode().equals(other.getTranCode()))
            && (this.getThreadName() == null ? other.getThreadName() == null : this.getThreadName().equals(other.getThreadName()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
            && (this.getLineNum() == null ? other.getLineNum() == null : this.getLineNum().equals(other.getLineNum()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getSubTraceId() == null ? other.getSubTraceId() == null : this.getSubTraceId().equals(other.getSubTraceId()))
            && (this.getBusinessType() == null ? other.getBusinessType() == null : this.getBusinessType().equals(other.getBusinessType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDateTime() == null) ? 0 : getDateTime().hashCode());
        result = prime * result + ((getServiceName() == null) ? 0 : getServiceName().hashCode());
        result = prime * result + ((getTraceId() == null) ? 0 : getTraceId().hashCode());
        result = prime * result + ((getTranCode() == null) ? 0 : getTranCode().hashCode());
        result = prime * result + ((getThreadName() == null) ? 0 : getThreadName().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getLineNum() == null) ? 0 : getLineNum().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getSubTraceId() == null) ? 0 : getSubTraceId().hashCode());
        result = prime * result + ((getBusinessType() == null) ? 0 : getBusinessType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", serviceName=").append(serviceName);
        sb.append(", traceId=").append(traceId);
        sb.append(", tranCode=").append(tranCode);
        sb.append(", threadName=").append(threadName);
        sb.append(", level=").append(level);
        sb.append(", className=").append(className);
        sb.append(", lineNum=").append(lineNum);
        sb.append(", content=").append(content);
        sb.append(", subTraceId=").append(subTraceId);
        sb.append(", businessType=").append(businessType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}