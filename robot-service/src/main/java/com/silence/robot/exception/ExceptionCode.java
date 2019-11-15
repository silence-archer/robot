package com.silence.robot.exception;



public enum ExceptionCode {

    PRECISION_ERROR(100001,"精度不足，请添加精度位数后再试"),
    LOGIN_ERROR(100002,"用户名密码错误"),
    AUTH_ERROR(100004,"当前未登录不能访问该页面"),
    MENU_ERROR(100005,"暂时只支持二级菜单"),
    NO_EXIST(100006,"记录不存在"),
    CMD_ERROR(100007,"输入的命令不正确"),
    CMD_STATE_ERROR(100008,"当前没有正在运行的命令"),
    CMD_CHECKOUT_ERROR(100009,"当前有正在运行的命令"),
    CHECK_NULL_ERROR(100010,"入参不能为空"),
    QUERY_ERROR(100011,"查询出来的数据条数不正确"),
    RUNNING_ERROR(100012,"任务正在执行中，请稍后再试"),
    VERIFY_ERROR(100003,"验证码输入错误");

    private int code;

    private String msg;

    ExceptionCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
