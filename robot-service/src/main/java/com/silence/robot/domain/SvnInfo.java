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

    private String url;

    private String name;

    private String localUrl;

    private String username;

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

    @Override
    public String toString() {
        return "SvnInfo{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}