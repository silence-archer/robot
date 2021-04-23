package com.silence.robot.domain;

/**
 * eureka注册中心Dto
 *
 * @author silence
 * @date 2020/12/12
 */
public class EurekaManageDto {
    private String name;
    private String ipAddr;
    private String instanceId;
    private String hostName;
    private String status;
    private String owner;
    private Integer port;
    private String homePageUrl;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    @Override
    public String toString() {
        return "EurekaManageDto{" +
                "name='" + name + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", hostName='" + hostName + '\'' +
                ", status='" + status + '\'' +
                ", owner='" + owner + '\'' +
                ", port='" + port + '\'' +
                ", homePageUrl='" + homePageUrl + '\'' +
                '}';
    }
}
