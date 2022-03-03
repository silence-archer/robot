/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMsgTalkDto
 * Author:   silence
 * Date:     2019/11/15 18:05
 * Description: 会话DTO
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain.instant.messaging;

/**
 * 〈一句话功能简述〉<br> 
 * 〈会话DTO〉
 *
 * @author silence
 * @create 2019/11/15
 * @since 1.0.0
 */
public class InstantMsgTalkDto {

    /**
     * 我发送的聊天信息
     */
    private InstantMsgMineDto mine;

    /**
     * 需要接受我消息的聊天对象
     */
    private InstantMsgMineDto to;

    public InstantMsgMineDto getMine() {
        return mine;
    }

    public void setMine(InstantMsgMineDto mine) {
        this.mine = mine;
    }

    public InstantMsgMineDto getTo() {
        return to;
    }

    public void setTo(InstantMsgMineDto to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "InstantMsgTalkDto{" +
                "mine=" + mine +
                ", to=" + to +
                '}';
    }
}