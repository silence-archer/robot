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

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.*;
import com.silence.robot.domain.InstantMessaging.InstantMsgMineDto;
import com.silence.robot.domain.InstantMessaging.InstantMsgTalkDto;
import com.silence.robot.utils.FileUtils;
import com.silence.robot.websocket.WebSocketServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private HelloService helloService;

    public InstantMessagingDto getInitData(String id){
        InstantMessagingDto instantMessagingDto = FileUtils.readJsonFile("config/InstantMessaging-"+id+".json", InstantMessagingDto.class);
        return instantMessagingDto;
    }

    public InstantMsgMembersDto getMembers(String id){
        InstantMsgMembersDto instantMsgMembersDto = FileUtils.readJsonFile("config/InstantMsgMembers.json", InstantMsgMembersDto.class);
        return instantMsgMembersDto;
    }

    public String handleMessage(String message){
        InstantMsgTalkInJsonDto instantMsgTalkJsonDto = JSONObject.parseObject(message, InstantMsgTalkInJsonDto.class);
        InstantMsgTalkDto data = instantMsgTalkJsonDto.getData();
        InstantMsgMineDto mine = data.getMine();
        InstantMsgMineDto to = data.getTo();
        InstantMsgMineDto out = new InstantMsgMineDto();
        out.setUsername(to.getUsername());
        out.setAvatar(to.getAvatar());
        out.setId(to.getId());
        out.setType(to.getType());
        out.setTimestamp(System.currentTimeMillis());
        InstantMsgTalkOutJsonDto instantMsgTalkOutJsonDto = new InstantMsgTalkOutJsonDto();
        instantMsgTalkOutJsonDto.setType("chatMessage");
        instantMsgTalkOutJsonDto.setData(out);
        if(to.getId().equals("100000") || to.getId().equals("100001")){
            out.setContent(mine.getContent());
            out.setUsername(mine.getUsername());
            out.setAvatar(mine.getAvatar());
            out.setId(mine.getId());
            out.setType(mine.getType());
            String s = JSONObject.toJSONString(instantMsgTalkOutJsonDto);
            //将消息发送给对方
            WebSocketServer.sendInfo(s, to.getId());
            return null;
        }else{
            TulingRequestInfo requestInfo = new TulingRequestInfo();
            requestInfo.setTxt(mine.getContent());
            TulingResponseInfo hello = helloService.hello(requestInfo);
            out.setContent(hello.getText());
            String s = JSONObject.toJSONString(instantMsgTalkOutJsonDto);
            return s;

        }

    }




}