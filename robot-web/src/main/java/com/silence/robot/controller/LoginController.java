package com.silence.robot.controller;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.service.LoginService;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public DataResponse<?> login(@RequestBody UserInfo userInfo) {
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
        loginService.login(userInfo);
        userInfo.setPassword(null);
        userInfo.setImageCode(null);
        userInfo.setImageWithVerifyCode(null);
        HttpUtils.putUserInfo(userInfo);

        return new DataResponse<>();
    }
}

