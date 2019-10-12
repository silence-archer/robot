/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserService
 * Author:   silence
 * Date:     2019/10/11 14:38
 * Description: 用户服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.service;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.mapper.TUserMapper;
import com.silence.robot.model.TUser;
import com.silence.robot.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户服务〉
 *
 * @author silence
 * @create 2019/10/11
 * @since 1.0.0
 */
@Service
public class UserService {

    @Resource
    private TUserMapper userMapper;

    public List<UserInfo> getUserInfo(){
        List<TUser> userList = userMapper.selectAll();
        List<UserInfo> list = CommonUtils.copyList(UserInfo.class, userList);
        return list;

    }

    public List<UserInfo> getUserInfoByCondition(UserInfo userInfo){
        TUser user = new TUser();
        BeanUtils.copyProperties(userInfo, user);
        List<TUser> userList = userMapper.selectByCondition(user);
        List<UserInfo> list = CommonUtils.copyList(UserInfo.class, userList);
        return list;
    }

    public void addUser(UserInfo userInfo){
        TUser user = new TUser();
        BeanUtils.copyProperties(userInfo, user);
        user.setCreateTime(new Date());
        user.setId(CommonUtils.getUuid());
        user.setUpdateTime(new Date());
        userMapper.insert(user);

    }

    public void updateUser(UserInfo userInfo){
        TUser user = new TUser();
        BeanUtils.copyProperties(userInfo, user);
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);
    }

    public void deleteUser(UserInfo userInfo){
        userMapper.deleteByPrimaryKey(userInfo.getId());
    }
}