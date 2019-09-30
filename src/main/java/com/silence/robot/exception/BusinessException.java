package com.silence.robot.exception;



public class BusinessException extends RuntimeException {

    private String code;

    private String message;

    public BusinessException(String code, String message){
        this.code = code;
        this.message = message;
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

    @Override
    public String toString() {
        return "BusinessException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
