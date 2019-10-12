package com.silence.robot.exception;



public enum ExceptionCode {

    PRECISION_ERROR("100001","精度不足，请添加精度位数后再试"),
    LOGIN_ERROR("100002","用户名密码错误"),
    AUTH_ERROR("100004","当前未登录不能访问该页面"),
    MENU_ERROR("100005","暂时只支持二级菜单"),
    NO_EXIST("100006","记录不存在"),
    VERIFY_ERROR("100003","验证码输入错误");

    private String code;

    private String message;

    ExceptionCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
