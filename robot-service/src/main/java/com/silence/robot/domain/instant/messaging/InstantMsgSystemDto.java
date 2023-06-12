/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMsgSystemDto
 * Author:   silence
 * Date:     2019/11/15 18:36
 * Description: system dto
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain.instant.messaging;

/**
 * 〈一句话功能简述〉<br> 
 * 〈system dto〉
 *
 * @author silence
 * @since 2019/11/15
 * 
 */
public class InstantMsgSystemDto {

    /**
     * 系统消息
     */
    private boolean system;

    /**
     * 聊天窗口ID
     */
    private int id;

    /**
     * 聊天窗口类型
     */
    private String type;

    /**
     * 消息内容
     */
    private String content;


    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "InstantMsgSystemDto{" +
                "system=" + system +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}