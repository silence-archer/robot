package com.silence.robot.enumeration;

/**
 * 配置项枚举类
 * @author silence
 */
public enum ConfigEnum {

    //
    ACCESS_TOKEN_ENUM("access_token","获取到的微信公众号凭证"),
    LOAN_DATE_ENUM("loan_date","交易系统日期"),
    DELICACY_ENUM("delicacy","每日菜单"),
    UPLOAD_PATH_ENUM("upload_path","上传文件路径"),
    AUTO_INTERFACE_PATH_ENUM("auto_interface_path","自动生成接口文件路径"),
    ESB_URI_ENUM("esb_uri","esb请求路径");

    private final String name;

    private final String desc;

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
