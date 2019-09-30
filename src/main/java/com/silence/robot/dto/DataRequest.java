package com.silence.robot.dto;



public class DataRequest<T> {

    private String apiCd;

    private T data;

    public String getApiCd() {
        return apiCd;
    }

    public void setApiCd(String apiCd) {
        this.apiCd = apiCd;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataRequest{" +
                "apiCd='" + apiCd + '\'' +
                ", data=" + data +
                '}';
    }
}
