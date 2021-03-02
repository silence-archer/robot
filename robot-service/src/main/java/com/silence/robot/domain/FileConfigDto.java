package com.silence.robot.domain;

import java.util.Date;

/**
 * 文件配置DTO
 *
 * @author silence
 * @date 2021/2/4
 */
public class FileConfigDto {
    /**
     * 物理主键
     */
    private String id;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件描述
     */
    private String fileDesc;

    /**
     * 远程文件路径
     */
    private String remoteFilepath;

    /**
     * 本地文件路径
     */
    private String localFilepath;

    /**
     * 远程服务器ip
     */
    private String remoteIp;

    /**
     * 远程服务器端口
     */
    private Integer remotePort;

    /**
     * 远程服务器用户名
     */
    private String remoteUsername;

    /**
     * 远程服务器密码
     */
    private String remotePassword;

    /**
     * 远程服务器密钥
     */
    private String remoteSecretKey;

    /**
     * 传输类型
     */
    private String transferType;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public String getRemoteFilepath() {
        return remoteFilepath;
    }

    public void setRemoteFilepath(String remoteFilepath) {
        this.remoteFilepath = remoteFilepath;
    }

    public String getLocalFilepath() {
        return localFilepath;
    }

    public void setLocalFilepath(String localFilepath) {
        this.localFilepath = localFilepath;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    public String getRemoteUsername() {
        return remoteUsername;
    }

    public void setRemoteUsername(String remoteUsername) {
        this.remoteUsername = remoteUsername;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public void setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
    }

    public String getRemoteSecretKey() {
        return remoteSecretKey;
    }

    public void setRemoteSecretKey(String remoteSecretKey) {
        this.remoteSecretKey = remoteSecretKey;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "FileConfigDto{" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", fileDesc='" + fileDesc + '\'' +
                ", remoteFilepath='" + remoteFilepath + '\'' +
                ", localFilepath='" + localFilepath + '\'' +
                ", remoteIp='" + remoteIp + '\'' +
                ", remotePort=" + remotePort +
                ", remoteUsername=" + remoteUsername +
                ", remotePassword='" + remotePassword + '\'' +
                ", remoteSecretKey='" + remoteSecretKey + '\'' +
                ", transferType='" + transferType + '\'' +
                ", businessType='" + businessType + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
