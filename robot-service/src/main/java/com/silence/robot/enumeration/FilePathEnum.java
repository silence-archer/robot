package com.silence.robot.enumeration;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/8
 */
public enum FilePathEnum {

    //
    AUTO_INTERFACE_PATH("%s/robot/autoInterface/%s/","接口自动生成界面路径"),
    AUTO_INTERFACE_COMMON_PATH("%s/js/","接口自动生成界面js配置路径"),
    ;

    private final String code;

    private final String desc;

    FilePathEnum(String code, String desc) {
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
