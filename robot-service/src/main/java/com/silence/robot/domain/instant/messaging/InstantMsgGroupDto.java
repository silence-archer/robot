/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMsgGroupDto
 * Author:   silence
 * Date:     2019/11/7 14:51
 * Description: dto
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain.instant.messaging;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 群组列表
 *
 * @author silence
 * @create 2019/11/7
 * @since 1.0.0
 */
public class InstantMsgGroupDto {

    /**
     * 群组ID
     */
    private String id;

    /**
     * 群组名
     */
    private String groupname;

    /**
     * 群组头像
     */
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "InstantMsgGroupDto{" +
                "id='" + id + '\'' +
                ", groupname='" + groupname + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}