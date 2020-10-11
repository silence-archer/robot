package com.silence.robot.domain;

import com.alibaba.fastjson.JSONObject;

/**
 * 挡板请求
 *
 * @author silence
 * @date 2020/9/27
 */
public class MockRequestInfo {
    private String uri;
    private String module;

    private JSONObject request;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public JSONObject getRequest() {
        return request;
    }

    public void setRequest(JSONObject request) {
        this.request = request;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "MockRequestInfo{" +
                "uri='" + uri + '\'' +
                ", module='" + module + '\'' +
                ", request=" + request +
                '}';
    }
}
