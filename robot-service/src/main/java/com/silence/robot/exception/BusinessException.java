package com.silence.robot.exception;



public class BusinessException extends RuntimeException {

    private int code;

    private String msg;

    private Throwable throwable;

    public BusinessException(String msg, Throwable throwable){
        super(msg,throwable);
        this.code = 66666;
        this.msg = msg;
        this.throwable = throwable;

    }

    public BusinessException(String msg){
        this.code = 77777;
        this.msg = msg;
    }

    public BusinessException(ExceptionCode exceptionCode, Object... params){
        super(exceptionCode.getMsg());
        this.code = exceptionCode.getCode();
        this.msg = String.format(exceptionCode.getMsg(), params);
    }
    public BusinessException(ExceptionCode exceptionCode){
        super(exceptionCode.getMsg());
        this.code = exceptionCode.getCode();
        this.msg = exceptionCode.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", throwable=" + throwable +
                '}';
    }
}
