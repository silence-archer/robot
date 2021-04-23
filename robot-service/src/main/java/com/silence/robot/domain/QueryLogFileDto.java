package com.silence.robot.domain;

/**
 * 查询日志条件DTO
 *
 * @author silence
 * @date 2021/2/4
 */
public class QueryLogFileDto {
    /**
     * 起始日期
     */
    private String startDate;
    /**
     * 终止日期
     */
    private String endDate;

    /**
     * 系统名称
     */
    private String serviceName;

    /**
     * 流水号
     */
    private String traceId;

    /**
     * 子流水号
     */
    private String subTraceId;

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
    private Integer page;
    private Integer limit;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getSubTraceId() {
        return subTraceId;
    }

    public void setSubTraceId(String subTraceId) {
        this.subTraceId = subTraceId;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
