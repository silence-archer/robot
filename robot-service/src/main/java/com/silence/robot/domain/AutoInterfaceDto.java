package com.silence.robot.domain;

/**
 * TODO
 *
 * @author silence
 * @since 2021/9/6
 */
public class AutoInterfaceDto {
    private String tranCode;
    private String tranName;
    private String uri;
    private Integer port;
    private String interfaceInput;
    private String interfaceOutput;

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getInterfaceInput() {
        return interfaceInput;
    }

    public void setInterfaceInput(String interfaceInput) {
        this.interfaceInput = interfaceInput;
    }

    public String getInterfaceOutput() {
        return interfaceOutput;
    }

    public void setInterfaceOutput(String interfaceOutput) {
        this.interfaceOutput = interfaceOutput;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
