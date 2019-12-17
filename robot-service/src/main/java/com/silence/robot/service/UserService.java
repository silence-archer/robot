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
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TUserMapper;
import com.silence.robot.mapper.TUserTalkFriendMapper;
import com.silence.robot.mapper.TUserTalkInfoMapper;
import com.silence.robot.mapper.TUserTalkMembersMapper;
import com.silence.robot.model.TUser;
import com.silence.robot.model.TUserTalkInfo;
import com.silence.robot.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.RoleUnresolved;
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

    @Resource
    private TUserTalkInfoMapper userTalkInfoMapper;

    @Resource
    private InstantMessagingService instantMessagingService;

    @Resource
    private TUserTalkFriendMapper userTalkFriendMapper;

    @Resource
    private TUserTalkMembersMapper userTalkMembersMapper;

    @Resource
    private RoleService roleService;

    public List<UserInfo> getUserInfo(){
        List<TUser> userList = userMapper.selectAll();

        return appendUserInfo(userList);

    }

    public List<UserInfo> getUserInfoByCondition(UserInfo userInfo){
        TUser user = new TUser();
        BeanUtils.copyProperties(userInfo, user);
        List<TUser> userList = userMapper.selectByCondition(user);
        return appendUserInfo(userList);
    }

    public void addUser(UserInfo userInfo){
        TUser user = new TUser();
        BeanUtils.copyProperties(userInfo, user);
        TUser userFlag = userMapper.selectByUsername(user.getUsername());
        if(CommonUtils.isNotEmpty(userFlag)){
            throw new BusinessException(ExceptionCode.EXIST_ERROR);
        }
        user.setPassword(CommonUtils.strToMD5(user.getUsername()));
        user.setCreateTime(new Date());
        user.setId(CommonUtils.getUuid());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        //添加到聊天面板
        instantMessagingService.registerUser(userInfo);

    }

    public void updateUser(UserInfo userInfo){
        TUser user = userMapper.selectByUsername(userInfo.getUsername());
        if(CommonUtils.isEmpty(user)){
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        user.setNickname(userInfo.getNickname());
        user.setRoleNo(userInfo.getRoleNo());
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);
        TUserTalkInfo userTalkInfo = userTalkInfoMapper.selectByPrimaryKey(userInfo.getUsername());
        userTalkInfo.setSign(userInfo.getSign());
        userTalkInfo.setAvatar(userInfo.getAvatar());
        userTalkInfo.setUsername(userInfo.getNickname());
        userTalkInfoMapper.updateByPrimaryKey(userTalkInfo);
    }

    public void deleteUser(String id){
        TUser user = userMapper.selectByPrimaryKey(id);
        if(CommonUtils.isEmpty(user)){
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        userMapper.deleteByPrimaryKey(id);
        userTalkInfoMapper.deleteByPrimaryKey(user.getUsername());
        //删除改用户在群组和其他人好友列表的记录
        userTalkFriendMapper.deleteByFriendId(user.getUsername());
        userTalkFriendMapper.deleteByMineId(user.getUsername());
        userTalkMembersMapper.deleteByMemberId(user.getUsername());
    }

    public void modifyPassword(UserInfo userInfo){
        //暂时用id代替原密码
        TUser user = userMapper.selectByUsername(userInfo.getUsername());
        String md5 = CommonUtils.strToMD5(userInfo.getId());
        if(!user.getPassword().equals(md5)){
            throw new BusinessException(ExceptionCode.PASSWORD_ERROR);
        }

        String password = userInfo.getPassword();
        password = CommonUtils.strToMD5(password);
        if(user.getPassword().equals(password)){
            throw new BusinessException(ExceptionCode.PASSWORD_SAME_ERROR);
        }
        user.setPassword(password);
        userMapper.updateByPrimaryKey(user);
    }

    public void resetPassword(List<UserInfo> list){
        list.forEach(userInfo -> {
            String username = userInfo.getUsername();
            TUser user = userMapper.selectByUsername(username);
            user.setPassword(CommonUtils.strToMD5(username));
            userMapper.updateByPrimaryKey(user);
        });
    }


    private List<UserInfo> appendUserInfo(List<TUser> userList){
        List<UserInfo> list = new ArrayList<>();
        userList.forEach(user -> {
            UserInfo userInfo = new UserInfo();
            TUserTalkInfo userTalkInfo = userTalkInfoMapper.selectByPrimaryKey(user.getUsername());
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            userInfo.setNickname(user.getNickname());
            userInfo.setCreateTime(CommonUtils.getStringDate(user.getCreateTime()));
            userInfo.setSign(userTalkInfo.getSign());
            userInfo.setAvatar(userTalkInfo.getAvatar());
            userInfo.setRoleNo(user.getRoleNo());
            String roleName = roleService.getRoleName(user.getRoleNo());
            userInfo.setRoleName(roleName);
            list.add(userInfo);
        });
        return list;
    }


}