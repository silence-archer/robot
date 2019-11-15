/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMessagingService
 * Author:   silence
 * Date:     2019/11/7 14:19
 * Description: 即时聊天
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.service;

import com.silence.robot.domain.InstantMessagingDto;
import com.silence.robot.domain.InstantMsgMembersDto;
import com.silence.robot.utils.FileUtils;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br> 
 * 〈即时聊天〉
 *
 * @author silence
 * @create 2019/11/7
 * @since 1.0.0
 */
@Service
public class InstantMessagingService {

    public InstantMessagingDto getInitData(){
        InstantMessagingDto instantMessagingDto = FileUtils.readJsonFile("config/InstantMessaging.json", InstantMessagingDto.class);
        return instantMessagingDto;
    }

    public InstantMsgMembersDto getMembers(){
        InstantMsgMembersDto instantMsgMembersDto = FileUtils.readJsonFile("config/InstantMsgMembers.json", InstantMsgMembersDto.class);
        return instantMsgMembersDto;
    }




}