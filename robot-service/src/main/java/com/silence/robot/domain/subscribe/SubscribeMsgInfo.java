/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SubscribeMsgInfo
 * Author:   silence
 * Date:     2020/1/2 16:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain.subscribe;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @create 2020/1/2
 * @since 1.0.0
 */
@XmlRootElement(name = "xml")
@XmlType(propOrder = {"toUserName","fromUserName","createTime","msgType","content","msgId"})
public class SubscribeMsgInfo {

    /**
     * 开发者微信号
     */

    private String toUserName;

    /**
     * 发送方帐号（一个OpenID）
     */

    private String fromUserName;

    /**
     * 消息创建时间 （整型）
     */

    private Long createTime;

    /**
     * 消息类型，文本为text 图片为image
     */

    private String msgType;

    /**
     * 文本消息内容
     */

    private String content;

    /**
     * 消息id，64位整型
     */

    private Long msgId;

    public String getToUserName() {
        return toUserName;
    }
    @XmlElement(name = "ToUserName")
    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }
    @XmlElement(name = "FromUserName")
    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }
    @XmlElement(name = "CreateTime")
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }
    @XmlElement(name = "MsgType")
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }
    @XmlElement(name = "Content")
    public void setContent(String content) {
        this.content = content;
    }

    public Long getMsgId() {
        return msgId;
    }
    @XmlElement(name = "MsgId")
    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "SubscribeMsgInfo{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                ", msgId=" + msgId +
                '}';
    }
}