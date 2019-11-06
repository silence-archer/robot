package com.silence.robot.controller;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public DataResponse<?> login(@RequestBody UserInfo userInfo, HttpSession httpSession) {
        if(userInfo.getImageCode().equals(httpSession.getAttribute("imageCode"))){
            //TODO 验证码过期 及时刷新
            httpSession.removeAttribute("imageCode");
        }else{
            throw new BusinessException(ExceptionCode.VERIFY_ERROR);
        }
        loginService.login(userInfo);
        httpSession.setAttribute("userInfo",userInfo);
        return new DataResponse<>();
    }
}

