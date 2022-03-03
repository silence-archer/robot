/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMsgMembersDto
 * Author:   silence
 * Date:     2019/11/7 15:07
 * Description: 成员列表
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain;

import com.silence.robot.domain.instant.messaging.InstantMsgMineDto;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈成员列表〉
 *
 * @author silence
 * @create 2019/11/7
 * @since 1.0.0
 */
public class InstantMsgMembersDto {

    /**
     * 成员列表
     */
    private List<InstantMsgMineDto> list;

    public List<InstantMsgMineDto> getList() {
        return list;
    }

    public void setList(List<InstantMsgMineDto> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "InstantMsgMembersDto{" +
                "list=" + list +
                '}';
    }
}