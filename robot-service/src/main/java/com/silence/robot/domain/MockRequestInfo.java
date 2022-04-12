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
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
