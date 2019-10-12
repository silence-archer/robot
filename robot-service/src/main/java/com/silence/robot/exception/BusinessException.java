package com.silence.robot.exception;



public class BusinessException extends RuntimeException {

    private String code;

    private String message;

    private Throwable throwable;

    public BusinessException(String message, Throwable throwable){
        this.code = "66666";
        this.message = message;
        this.throwable = throwable;
    }

    public BusinessException(ExceptionCode exceptionCode){
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
