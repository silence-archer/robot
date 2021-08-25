package com.silence.robot.domain;

/**
 * TODO
 *
 * @author silence
 * @date 2021/6/27
 */
public class DataDiffDto extends RobotQueryDto{
    /**
     * 数据名称
     */
    private String enumDesc;
    /**
     * 脚本
     */
    private String runScript;
    /**
     * 原值
     */
    private String origValue;
    /**
     * 目标值
     */
    private String destValue;
    /**
     * 数据表名
     */
    private String enumName;

    /**
     * 数据名称
     */
    private String dataName;

    private String businessType;


    public String getEnumDesc() {
        return enumDesc;
    }

    public void setEnumDesc(String enumDesc) {
        this.enumDesc = enumDesc;
    }

    public String getRunScript() {
        return runScript;
    }

    public void setRunScript(String runScript) {
        this.runScript = runScript;
    }

    public String getOrigValue() {
        return origValue;
    }

    public void setOrigValue(String origValue) {
        this.origValue = origValue;
    }

    public String getDestValue() {
        return destValue;
    }

    public void setDestValue(String destValue) {
        this.destValue = destValue;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
