package com.silence.robot.enumeration;

/**
 * 文件格式枚举
 *
 * @author silence
 * @date 2020/10/6
 */
public enum FileFormatEnum {

    JSON("access_token","获取到的微信公众号凭证"),
    SIGH_MARK("access_token","获取到的微信公众号凭证"),
    VERTICAL_SEPARATOR_SKIP("delicacy","每日菜单");

    private String name;

    private String desc;

    FileFormatEnum(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }
}
