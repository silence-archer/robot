package com.silence.robot.dto;


public class DataResponse<T> {

    public DataResponse(){
        this.code = 0;
        this.msg = "交易成功";
    }
    public DataResponse(T data){
        this.code = 0;
        this.msg = "交易成功";
        this.data = data;
    }
    public DataResponse(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private String serialNumber;

    private int code;
    private String msg;

    private String type;

    private Long count;

    private String token;

    private T data;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "DataResponse{" +
                "serialNumber='" + serialNumber + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
