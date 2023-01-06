package com.silence.robot.exception;



/**
 * @author silence
 */

public enum ExceptionCode {
    //
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
    EXIST_ERROR(100013,"当前记录已存在"),
    SESSION_ERROR(100014,"当前登录已失效,请刷新重试"),
    PASSWORD_ERROR(100015,"原密码输入错误"),
    PASSWORD_SAME_ERROR(100016,"新密码与原密码重复"),
    ROLE_NO_EXIST(100017,"角色代码不能为空"),
    ILLEGAL_REQUEST(100018,"请求方非微信服务器"),
    CRON_ERROR(100019,"cron表达式输入有误"),
    SCHEDULER_START_ERROR(100020,"定时任务启动失败"),
    SCHEDULER_STOP_ERROR(100021,"定时任务停止失败"),
    SCHEDULER_RESTART_ERROR(100022,"定时任务重启失败"),
    SUBSCRIBE_TOKEN_ERROR(100023,"获取微信公众号凭证失败"),
    HTTP_REQUEST_ERROR(100024,"http请求失败"),
    UPLOAD_ERROR(100025,"上传文件失败"),
    FILE_READ_ERROR(100026,"文件读取失败[%s]"),
    EMPTY_ERROR(100027,"存在为空数据项"),
    DATABASE_CONNECT_ERROR(100028,"自定义数据库链接失败"),
    JSON_PARSE_ERROR(100029,"暂不支持json报文进行数组嵌套"),
    JSON_TEXT_ERROR(100030,"json格式不正确"),
    NO_EXIST_PARAM(100031,"当前用户[%s]对应的杂类参数[%s]不存在"),
    TOKEN_VERIFY_ERROR(100032,"token检查失败"),
    TOKEN_PARSE_ERROR(100033,"token解析失败"),
    SCHEDULER_RESTART_CHECK_ERROR(100034,"当前定时任务已执行成功，无需重试"),
    DAY_END_BATCH_PROCESS_ERROR(100035,"日终批处理执行失败"),
    VERIFY_ERROR(100003,"验证码输入错误");

    private final int code;

    private final String msg;

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
