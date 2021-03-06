/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMessagingDto
 * Author:   silence
 * Date:     2019/11/7 14:49
 * Description: 即时聊天dto
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain;

import com.silence.robot.domain.InstantMessaging.InstantMsgFriendDto;
import com.silence.robot.domain.InstantMessaging.InstantMsgGroupDto;
import com.silence.robot.domain.InstantMessaging.InstantMsgMineDto;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈即时聊天dto〉
 *
 * @author silence
 * @create 2019/11/7
 * @since 1.0.0
 */
public class InstantMessagingDto {

    /**
     * 我的信息
     */
    private InstantMsgMineDto mine;

    /**
     * 好友列表
     */
    private List<InstantMsgFriendDto> friend;

    /**
     * 群组列表
     */
    private List<InstantMsgGroupDto> group;

    public InstantMsgMineDto getMine() {
        return mine;
    }

    public void setMine(InstantMsgMineDto mine) {
        this.mine = mine;
    }

    public List<InstantMsgFriendDto> getFriend() {
        return friend;
    }

    public void setFriend(List<InstantMsgFriendDto> friend) {
        this.friend = friend;
    }

    public List<InstantMsgGroupDto> getGroup() {
        return group;
    }

    public void setGroup(List<InstantMsgGroupDto> group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "InstantMessagingDto{" +
                "mine=" + mine +
                ", friend=" + friend +
                ", group=" + group +
                '}';
    }
}