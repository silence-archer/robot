/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMessagingController
 * Author:   silence
 * Date:     2019/11/7 14:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.controller;

import com.silence.robot.domain.InstantMessagingDto;
import com.silence.robot.domain.InstantMsgMembersDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.InstantMessagingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @create 2019/11/7
 * @since 1.0.0
 */
@RestController
public class InstantMessagingController {


    @Resource
    private InstantMessagingService instantMessagingService;

    @GetMapping("/getInitData")
    public DataResponse<InstantMessagingDto> getInitData(){
        InstantMessagingDto initData = instantMessagingService.getInitData();
        return new DataResponse<>(initData);
    }

    @GetMapping("/getMembers")
    public DataResponse<InstantMsgMembersDto> getMembers() {
        InstantMsgMembersDto members = instantMessagingService.getMembers();
        return new DataResponse<>(members);
    }
}