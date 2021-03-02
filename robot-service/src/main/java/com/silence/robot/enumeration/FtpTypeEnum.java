package com.silence.robot.enumeration;

/**
 * silence
 *
 * @author silence
 * @date 2021/1/3
 */
public enum FtpTypeEnum {

    //ftp文件传输
    FTP("ftp","ftp文件传输"),
    //sftp文件传输
    SFTP("sftp","sftp文件传输"),
    ;

    private String code;
    private String desc;

    FtpTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
