/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMsgMineDto
 * Author:   silence
 * Date:     2019/11/7 14:50
 * Description: dto
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain.InstantMessaging;

/**
 * 〈一句话功能简述〉<br>
 * 我的信息
 *
 * @author silence
 * @create 2019/11/7
 * @since 1.0.0
 */
public class InstantMsgMineDto {

    /**
     * 我的ID
     */
    private String id;

    /**
     * 我的昵称
     */
    private String username;

    /**
     * 在线状态 online：在线、hide：隐身
     */
    private String status;

    /**
     * 我的签名
     */
    private String sign;

    /**
     * 我的头像
     */
    private String avatar;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 聊天类型 一般分friend和group两种，group即群聊
     */
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InstantMsgMineDto{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", status='" + status + '\'' +
                ", sign='" + sign + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}