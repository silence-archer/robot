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
package com.silence.robot.domain.instant.messaging;

/**
 * 〈一句话功能简述〉<br>
 * 我的信息
 *
 * @author silence
 * @since 2019/11/7
 * 
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

    /**
     * 是否我发送的消息，如果为true，则会显示在右方
     */
    private Boolean mine;

    /**
     * 消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
     */
    private String fromid;

    /**
     * 服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
     */
    private Long timestamp;

    /**
     * 消息id，可不传。除非你要对消息进行一些操作（如撤回）
     */
    private Integer cid;

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

    public Boolean isMine() {
        return mine;
    }

    public void setMine(Boolean mine) {
        this.mine = mine;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
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
                ", mine=" + mine +
                ", fromid='" + fromid + '\'' +
                ", timestamp=" + timestamp +
                ", cid=" + cid +
                '}';
    }
}