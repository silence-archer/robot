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

    private int code;
    private String msg;

    private T data;

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

    @Override
    public String toString() {
        return "DataResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
