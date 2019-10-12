package com.silence.robot.dto;


public class DataResponse<T> {

    public DataResponse(){
        this.code = "000000";
        this.message = "交易成功";
    }
    public DataResponse(T data){
        this.code = "000000";
        this.message = "交易成功";
        this.data = data;
    }
    public DataResponse(String code, String message){
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
