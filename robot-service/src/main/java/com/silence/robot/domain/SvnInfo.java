/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SvnInfo
 * Author:   silence
 * Date:     2019/10/28 10:59
 * Description: svn info
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain;

/**
 * 〈一句话功能简述〉<br> 
 * 〈svn info〉
 *
 * @author silence
 * @create 2019/10/28
 * @since 1.0.0
 */
public class SvnInfo {

    /**
     * svn url地址
     */
    private String url;

    /**
     * svn 远程文件名
     */
    private String name;

    /**
     * svn 检出文件夹名称
     */
    private String svnName;

    /**
     * svn 检出本地地址
     */
    private String localUrl;

    /**
     * svn 用户名
     */
    private String username;

    /**
     * svn 密码
     */
    private String password;

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSvnName() {
        return svnName;
    }

    public void setSvnName(String svnName) {
        this.svnName = svnName;
    }

    @Override
    public String toString() {
        return "SvnInfo{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", svnName='" + svnName + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}