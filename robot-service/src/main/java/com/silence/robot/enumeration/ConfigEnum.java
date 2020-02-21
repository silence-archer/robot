package com.silence.robot.enumeration;

/**
 * 配置项枚举类
 */
public enum ConfigEnum {


    ACCESS_TOKEN_ENUM("access_token","获取到的微信公众号凭证"),
    DELICACY_ENUM("delicacy","每日菜单");

    private String name;

    private String desc;

    ConfigEnum(String name, String desc){
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
