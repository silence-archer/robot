/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMsgTalkInJsonDto
 * Author:   silence
 * Date:     2019/11/15 18:17
 * Description: DTO
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain;

import com.silence.robot.domain.instant.messaging.InstantMsgTalkDto;

/**
 * 〈一句话功能简述〉<br> 
 * 〈DTO〉
 *
 * @author silence
 * @since 2019/11/15
 * 
 */
public class InstantMsgTalkInJsonDto {

    private String type;

    private InstantMsgTalkDto data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InstantMsgTalkDto getData() {
        return data;
    }

    public void setData(InstantMsgTalkDto data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InstantMsgTalkInJsonDto{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}