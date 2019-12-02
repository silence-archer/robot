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
import com.silence.robot.domain.InstantMessaging.InstantMsgFriendDto;
import com.silence.robot.domain.InstantMessaging.InstantMsgGroupDto;
import com.silence.robot.domain.InstantMessaging.InstantMsgMineDto;
import com.silence.robot.domain.InstantMessaging.InstantMsgTalkDto;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TUserTalkFriendGroupMapper;
import com.silence.robot.mapper.TUserTalkFriendMapper;
import com.silence.robot.mapper.TUserTalkGroupMapper;
import com.silence.robot.mapper.TUserTalkInfoMapper;
import com.silence.robot.model.TUserTalkFriend;
import com.silence.robot.model.TUserTalkFriendGroup;
import com.silence.robot.model.TUserTalkGroup;
import com.silence.robot.model.TUserTalkInfo;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FileUtils;
import com.silence.robot.websocket.WebSocketServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private TUserTalkInfoMapper userTalkInfoMapper;

    @Resource
    private TUserTalkFriendMapper userTalkFriendMapper;

    @Resource
    private TUserTalkFriendGroupMapper userTalkFriendGroupMapper;

    @Resource
    private TUserTalkGroupMapper userTalkGroupMapper;

    public InstantMessagingDto getInitData(String id){
        TUserTalkInfo userTalkInfo = userTalkInfoMapper.selectByPrimaryKey(id);
        if(userTalkInfo == null){
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        InstantMessagingDto instantMessagingDto = new InstantMessagingDto();
        //组装我的信息
        InstantMsgMineDto mine = new InstantMsgMineDto();
        mine.setUsername(userTalkInfo.getUsername());
        mine.setId(userTalkInfo.getId());
        mine.setSign(userTalkInfo.getSign());
        mine.setAvatar(userTalkInfo.getAvatar());
        mine.setStatus("online");
        instantMessagingDto.setMine(mine);
        //组装我的好友列表信息
        List<TUserTalkFriend> userTalkFriends = userTalkFriendMapper.selectByMineId(userTalkInfo.getId());
        List<TUserTalkFriendGroup> userTalkFriendGroups = userTalkFriendGroupMapper.selectByMineId(userTalkInfo.getId());
        //根据组别分别组装
        List<InstantMsgFriendDto> friend = new ArrayList<>();
        userTalkFriendGroups.forEach(userTalkFriendGroup -> {
            InstantMsgFriendDto instantMsgFriendDto = new InstantMsgFriendDto();
            instantMsgFriendDto.setId(userTalkFriendGroup.getGroupId());
            instantMsgFriendDto.setGroupname(userTalkFriendGroup.getGroupname());
            List<InstantMsgMineDto> list = new ArrayList<>();
            instantMsgFriendDto.setList(list);
            List<TUserTalkFriend> collect = userTalkFriends.stream().filter(userTalkFriend -> userTalkFriend.getGroupId().equals(userTalkFriendGroup.getGroupId())).collect(Collectors.toList());
            collect.forEach(userTalkFriend -> {
                InstantMsgMineDto friendDto = new InstantMsgMineDto();
                TUserTalkInfo talkInfo = userTalkInfoMapper.selectByPrimaryKey(userTalkFriend.getFriendId());

                friendDto.setStatus(talkInfo.getStatus());
                friendDto.setAvatar(talkInfo.getAvatar());
                friendDto.setSign(talkInfo.getSign());
                friendDto.setId(talkInfo.getId());
                friendDto.setUsername(talkInfo.getUsername());
                list.add(friendDto);
            });
            friend.add(instantMsgFriendDto);
        });
        instantMessagingDto.setFriend(friend);
        //组装群聊信息
        List<TUserTalkGroup> userTalkGroups = userTalkGroupMapper.selectByMineId(userTalkInfo.getId());
        List<InstantMsgGroupDto> group = new ArrayList<>();
        userTalkGroups.forEach(userTalkGroup -> {
            InstantMsgGroupDto instantMsgGroupDto = new InstantMsgGroupDto();
            instantMsgGroupDto.setAvatar(userTalkGroup.getAvatar());
            instantMsgGroupDto.setGroupname(userTalkGroup.getGroupname());
            instantMsgGroupDto.setId(userTalkGroup.getGroupId());
            group.add(instantMsgGroupDto);
        });
        instantMessagingDto.setGroup(group);
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

    public void registerUser(UserInfo userInfo){
        TUserTalkInfo userTalkInfo = new TUserTalkInfo();
        userTalkInfo.setId(userInfo.getUsername());
        userTalkInfo.setUsername(userInfo.getNickname());
        userTalkInfo.setAvatar(userInfo.getAvatar());
        userTalkInfo.setSign(userInfo.getSign());
        userTalkInfo.setStatus("hide");
        userTalkInfoMapper.insert(userTalkInfo);
        //注册成功后直接添加聊天机器人
        TUserTalkFriend userTalkFriend = new TUserTalkFriend();
        userTalkFriend.setId(CommonUtils.getUuid());
        userTalkFriend.setMineId(userTalkInfo.getId());
        userTalkFriend.setFriendId("admin");
        userTalkFriend.setGroupId(0);
        userTalkFriendMapper.insert(userTalkFriend);
        //添加分组信息
        TUserTalkFriendGroup userTalkFriendGroup = new TUserTalkFriendGroup();
        userTalkFriendGroup.setId(CommonUtils.getUuid());
        userTalkFriendGroup.setGroupId(0);
        userTalkFriendGroup.setGroupname("机器人组");
        userTalkFriendGroup.setMineId(userTalkInfo.getId());
        userTalkFriendGroupMapper.insert(userTalkFriendGroup);
        //添加默认的群组
        TUserTalkGroup userTalkGroup = new TUserTalkGroup();
        userTalkGroup.setAvatar("image/avatar/30.jpg");
        userTalkGroup.setGroupId("0");
        userTalkGroup.setGroupname("大家庭");
        userTalkGroup.setMineId(userTalkInfo.getId());
        userTalkGroup.setId(CommonUtils.getUuid());
        userTalkGroupMapper.insert(userTalkGroup);

    }

    public void addFriendOrGroup(InstantMsgMineDto instantMsgMineDto){
        if(instantMsgMineDto.getType().equals("friend")){

        }
    }




}