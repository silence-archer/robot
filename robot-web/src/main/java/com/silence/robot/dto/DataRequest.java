package com.silence.robot.dto;



public class DataRequest<T> {

    private String apiCd;

    private Integer page;

    private Integer limit;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "DataRequest{" +
                "apiCd='" + apiCd + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                ", data=" + data +
                '}';
    }
}
