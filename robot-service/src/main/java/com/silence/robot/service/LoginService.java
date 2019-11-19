package com.silence.robot.service;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TUserMapper;
import com.silence.robot.model.TUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LoginService {

    @Resource
    private TUserMapper userMapper;

    public void login(UserInfo userInfo){

        TUser user = userMapper.selectByUsername(userInfo.getUsername());
        if(user != null && user.getPassword().equals(userInfo.getPassword())){
            userInfo.setNickname(user.getNickname());
            userInfo.setId(user.getId());
        }else{
            throw new BusinessException(ExceptionCode.LOGIN_ERROR);
        }

    }
}
