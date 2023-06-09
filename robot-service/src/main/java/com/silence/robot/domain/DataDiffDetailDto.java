package com.silence.robot.domain;

/**
 * TODO
 *
 * @author silence
 * @since 2021/6/27
 */
public class DataDiffDetailDto {
    /**
     * 主键
     */
    private String primalKey;
    /**
     * 原差值
     */
    private String origDetailValue;
    /**
     * 目标差值
     */
    private String destDetailValue;

    public String getPrimalKey() {
        return primalKey;
    }

    public void setPrimalKey(String primalKey) {
        this.primalKey = primalKey;
    }

    public String getOrigDetailValue() {
        return origDetailValue;
    }

    public void setOrigDetailValue(String origDetailValue) {
        this.origDetailValue = origDetailValue;
    }

    public String getDestDetailValue() {
        return destDetailValue;
    }

    public void setDestDetailValue(String destDetailValue) {
        this.destDetailValue = destDetailValue;
    }
}
