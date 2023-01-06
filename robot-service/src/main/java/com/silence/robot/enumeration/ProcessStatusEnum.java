package com.silence.robot.enumeration;




public enum ProcessStatusEnum {

    WAIT("WAIT", "等待执行"),
    PROCESSING("PROCESSING", "执行中"),
    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),
    ;

    private String code;
    private String desc;

    ProcessStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}