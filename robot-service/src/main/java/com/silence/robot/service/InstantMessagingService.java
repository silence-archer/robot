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
import com.silence.robot.domain.instant.messaging.InstantMsgFriendDto;
import com.silence.robot.domain.instant.messaging.InstantMsgGroupDto;
import com.silence.robot.domain.instant.messaging.InstantMsgMineDto;
import com.silence.robot.domain.instant.messaging.InstantMsgTalkDto;
import com.silence.robot.domain.*;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.*;
import com.silence.robot.model.*;
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
 * @since 2019/11/7
 * 
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

    @Resource
    private TUserTalkMembersMapper userTalkMembersMapper;

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
        List<TUserTalkMembers> userTalkMembers = userTalkMembersMapper.selectByGroupId(id);
        InstantMsgMembersDto instantMsgMembersDto = new InstantMsgMembersDto();
        List<InstantMsgMineDto> list = new ArrayList<>(userTalkMembers.size());
        userTalkMembers.forEach(userTalkMember ->{
            InstantMsgMineDto instantMsgMineDto = new InstantMsgMineDto();
            TUserTalkInfo userTalkInfo = userTalkInfoMapper.selectByPrimaryKey(userTalkMember.getMemberId());
            instantMsgMineDto.setId(userTalkInfo.getId());
            instantMsgMineDto.setUsername(userTalkInfo.getUsername());
            instantMsgMineDto.setSign(userTalkInfo.getSign());
            instantMsgMineDto.setAvatar(userTalkInfo.getAvatar());
            list.add(instantMsgMineDto);
        });
        instantMsgMembersDto.setList(list);
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
        if("admin".equals(to.getId())){
            TulingRequestInfo requestInfo = new TulingRequestInfo();
            requestInfo.setTxt(mine.getContent());
            TulingResponseInfo hello = helloService.hello(requestInfo);
            out.setContent(hello.getText());
            String s = JSONObject.toJSONString(instantMsgTalkOutJsonDto);
            return s;
        }else{
            out.setContent(mine.getContent());
            out.setUsername(mine.getUsername());
            out.setAvatar(mine.getAvatar());
            String s = JSONObject.toJSONString(instantMsgTalkOutJsonDto);
            //将消息发送给对方
            WebSocketServer.sendInfo(s, to.getId(), mine.getId());
            return null;


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
        userTalkFriend.setMineId(userTalkInfo.getId());
        userTalkFriend.setFriendId("admin");
        userTalkFriend.setGroupId(0);
        userTalkFriendMapper.insert(userTalkFriend);
        //添加分组信息
        TUserTalkFriendGroup userTalkFriendGroup = new TUserTalkFriendGroup();
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
        userTalkGroupMapper.insert(userTalkGroup);
        //添加到群成员
        TUserTalkMembers userTalkMembers = new TUserTalkMembers();
        userTalkMembers.setGroupId("0");
        userTalkMembers.setMemberId(userTalkInfo.getId());
        userTalkMembersMapper.insert(userTalkMembers);

    }

    public void addFriendOrGroup(InstantMsgMineDto instantMsgMineDto){
        if("friend".equals(instantMsgMineDto.getType())){

        }
    }




}