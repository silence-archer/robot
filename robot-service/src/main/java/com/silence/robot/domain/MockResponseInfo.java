package com.silence.robot.domain;

/**
 * 挡板返回
 *
 * @author silence
 * @date 2022/4/12
 */
public class MockResponseInfo {
    private Boolean mockSuccess;

    private String responseXml;

    public Boolean getMockSuccess() {
        return mockSuccess;
    }

    public void setMockSuccess(Boolean mockSuccess) {
        this.mockSuccess = mockSuccess;
    }

    public String getResponseXml() {
        return responseXml;
    }

    public void setResponseXml(String responseXml) {
        this.responseXml = responseXml;
    }
}
