package com.silence.robot.dto;



public class DataRequest<T> {

    private String apiCd;

    private Integer page;

    private Integer limit;

    private String type;

    private Integer count;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DataRequest{" +
                "apiCd='" + apiCd + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                ", type='" + type + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
