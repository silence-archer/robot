package com.silence.robot.controller;

import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.UserInfo;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.service.UserService;
import com.silence.robot.utils.HttpUtils;
import com.silence.robot.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author silence
 */
@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @GetMapping("/getUser")
    public DataResponse<UserInfo> getUser(){
        UserInfo userInfo = HttpUtils.getUserInfo();
        if(userInfo == null){
            throw new BusinessException(ExceptionCode.AUTH_ERROR);
        }
        String token = JwtUtils.createToken(userInfo);
        logger.info("当前用户id 为：{},用户名为：{}",userInfo.getId(), userInfo.getUsername());
        DataResponse<UserInfo> userDataResponse = new DataResponse<>();
        userDataResponse.setData(userInfo);
        userDataResponse.setToken(token);
        return userDataResponse;
    }

    @GetMapping("/getUserInfo")
    public DataResponse<List<UserInfo>> getUserInfo(@RequestParam Integer page, @RequestParam Integer limit){
        //分页
        RobotPage<UserInfo> userInfoByPage = userService.getUserInfoByPage(page, limit);

        DataResponse<List<UserInfo>> dataResponse = new DataResponse<>(userInfoByPage.getList());
        dataResponse.setCount(userInfoByPage.getTotal());
        return dataResponse;
    }
    @GetMapping("/getUserList")
    public DataResponse<List<UserInfo>> getUserList(){
        return new DataResponse<>(userService.getUserInfo());
    }

    @PostMapping("/getUserInfoByCondition")
    public DataResponse<List<UserInfo>> getUserInfoByCondition(@RequestBody UserInfo userInfo){
        List<UserInfo> userInfos = userService.getUserInfoByCondition(userInfo);
        return new DataResponse<>(userInfos);
    }

    @PostMapping("/addUser")
    public DataResponse<?> addUser(@RequestBody UserInfo userInfo){
        userService.addUser(userInfo);
        return new DataResponse<>();
    }

    @PostMapping("/updateUser")
    public DataResponse<?> updateUser(@RequestBody UserInfo userInfo){
        userService.updateUser(userInfo);
        return new DataResponse<>();
    }

    @GetMapping("/deleteUser")
    public DataResponse<?> deleteUser(@RequestParam String id){
        userService.deleteUser(id);
        return new DataResponse<>();
    }

    @PostMapping("/modifyPassword")
    public DataResponse<?> modifyPassword(@RequestBody UserInfo userInfo){
        userService.modifyPassword(userInfo);
        //修改密码成功后需要重新登录
        HttpUtils.removeUserInfo();
        return new DataResponse<>();
    }

    @PostMapping("/resetPassword")
    public DataResponse<?> resetPassword(@RequestBody List<UserInfo> list){
        userService.resetPassword(list);
        return new DataResponse<>();
    }
}
