package com.silence.robot.controller;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.service.LoginService;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author silence
 */
@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public DataResponse<UserInfo> login(@RequestBody UserInfo userInfo) {
        String imageCode = HttpUtils.getImageCode();
        if (CommonUtils.isEmpty(imageCode)) {
            throw new BusinessException(ExceptionCode.VERIFY_ERROR);
        }
        String[] split = imageCode.split("-");
        if (userInfo.getImageCode().equalsIgnoreCase(split[0]) && (System.currentTimeMillis() - Long.parseLong(split[1]) < 60*1000)) {
            HttpUtils.removeImageCode();
        } else {
            throw new BusinessException(ExceptionCode.VERIFY_ERROR);
        }

        AuthenticationToken authenticationToken = new UsernamePasswordToken(userInfo.getUsername(), userInfo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(authenticationToken);
        loginService.login(userInfo);
        userInfo.setPassword(null);
        userInfo.setImageCode(null);
        userInfo.setImageWithVerifyCode(null);
        DataResponse<UserInfo> response = new DataResponse<>();
        String token = subject.getSession().getId().toString();
        response.setToken(token);
        HttpUtils.putUserInfo(userInfo);
        response.setData(userInfo);
        return response;
    }

    @GetMapping("/logout")
    public DataResponse<?> logout() {
        HttpUtils.removeUserInfo();
        return new DataResponse<>();
    }
}

