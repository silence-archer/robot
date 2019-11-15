/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMsgFriendDto
 * Author:   silence
 * Date:     2019/11/7 14:51
 * Description: dto
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain.InstantMessaging;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 好友列表
 *
 * @author silence
 * @create 2019/11/7
 * @since 1.0.0
 */
public class InstantMsgFriendDto {
    /**
     * 分组ID
     */
    private String id;

    /**
     * 好友分组名
     */
    private String groupname;

    /**
     * 分组下的好友列表
     */
    private List<InstantMsgMineDto> list;

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

    public List<InstantMsgMineDto> getList() {
        return list;
    }

    public void setList(List<InstantMsgMineDto> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "InstantMsgGroupDto{" +
                "id='" + id + '\'' +
                ", groupname='" + groupname + '\'' +
                ", list=" + list +
                '}';
    }
}