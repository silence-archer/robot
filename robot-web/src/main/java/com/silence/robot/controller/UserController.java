package com.silence.robot.controller;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/getUser")
    public DataResponse<UserInfo> getUser(HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("userInfo");
        if(userInfo == null){
            throw new BusinessException(ExceptionCode.AUTH_ERROR);
        }
        DataResponse<UserInfo> userDataResponse = new DataResponse<>();
        userDataResponse.setData(userInfo);
        return userDataResponse;
    }

    @GetMapping("/getUserInfo")
    public DataResponse<List<UserInfo>> getUserInfo(@RequestParam Integer page, @RequestParam Integer limit){
        //分页
        List<UserInfo> userInfo = userService.getUserInfo();
        return new DataResponse<>(userInfo);
    }

    @PostMapping("/getUserInfoByCondition")
    public DataResponse<List<UserInfo>> getUserInfoByCondition(@RequestBody UserInfo userInfo){
        List<UserInfo> userInfos = userService.getUserInfoByCondition(userInfo);
        return new DataResponse<>(userInfos);
    }

    @PostMapping("/addUser")
    public DataResponse<UserInfo> addUser(@RequestBody UserInfo userInfo){
        userService.addUser(userInfo);
        return new DataResponse<>();
    }

    @PostMapping("/updateUser")
    public DataResponse<UserInfo> updateUser(@RequestBody UserInfo userInfo){
        userService.updateUser(userInfo);
        return new DataResponse<>();
    }

    @PostMapping("/deleteUser")
    public DataResponse<UserInfo> deleteUser(@RequestBody UserInfo userInfo){
        userService.deleteUser(userInfo);
        return new DataResponse<>();
    }
}
