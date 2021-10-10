package com.silence.robot.service;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.mapper.TUserMapper;
import com.silence.robot.mapper.TUserTalkInfoMapper;
import com.silence.robot.model.TUser;
import com.silence.robot.model.TUserTalkInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LoginService {

    @Resource
    private TUserMapper userMapper;

    @Resource
    private TUserTalkInfoMapper userTalkInfoMapper;

    public void login(UserInfo userInfo){

        TUser user = userMapper.selectByUsername(userInfo.getUsername());
        TUserTalkInfo userTalkInfo = userTalkInfoMapper.selectByPrimaryKey(userInfo.getUsername());
        userInfo.setNickname(user.getNickname());
        userInfo.setId(user.getId());
        userInfo.setRoleNo(user.getRoleNo());
        String avatar = userTalkInfo == null ? null : userTalkInfo.getAvatar();
        userInfo.setAvatar(avatar);
        userInfo.setIpAddr(user.getIpAddr());

    }
}
