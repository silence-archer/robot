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
    DING_CLOCK_ADDRESS("address","打卡配置"),
    DING_CLOCK_GET_WAIT_DEAL("get_wait_deal","打卡配置"),
    DING_CLOCK_GET_SIGN_RULE_DATE("get_sign_rule_date","打卡配置"),
    DING_CLOCK_GET_CARD("get_card","打卡配置"),
    DING_CLOCK_GET_OPEN_ID("get_open_id","打卡配置"),
    DING_CLOCK_SAVE_SIGN_RULE_DATE("save_sign_rule_date","打卡配置"),
    MAIL_ADDRESS("mail_address","运维邮件地址"),
    COMMEMORATING_MAIL_ADDRESS("commemorating_mail_address","纪念邮件地址"),
    COMMEMORATING_URL("commemorating_url","纪念url"),
    ESB_URI_ENUM("esb_uri","esb请求路径"),
    FREE_MARKER_VERSION_ENUM("free_marker_version","生成界面版本"),
    COMMEMORATING_PATH_ENUM("commemorating_path","纪念界面路径"),
    MEMBER_MAIL_ADDRESS("member_mail_address","会员管理邮件地址"),
    ;

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
