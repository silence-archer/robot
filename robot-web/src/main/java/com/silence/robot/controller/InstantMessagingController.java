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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.InstantMessagingDto;
import com.silence.robot.domain.InstantMsgMembersDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.InstantMessagingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @since 2019/11/7
 * 
 */
@RestController
public class InstantMessagingController {


    @Resource
    private InstantMessagingService instantMessagingService;

    @GetMapping("/getInitData/{id}")
    public DataResponse<JSONObject> getInitData(@PathVariable String id){
        InstantMessagingDto initData = instantMessagingService.getInitData(id);
        JSONObject parse = JSONObject.parseObject(JSON.toJSONString(initData));
        return new DataResponse<>(parse);
    }

    @GetMapping("/getMembers")
    public DataResponse<InstantMsgMembersDto> getMembers(@RequestParam String id) {
        InstantMsgMembersDto members = instantMessagingService.getMembers(id);
        return new DataResponse<>(members);
    }
}