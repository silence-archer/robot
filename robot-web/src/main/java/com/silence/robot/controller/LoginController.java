package com.silence.robot.controller;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.listener.SessionListener;
import com.silence.robot.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author silence
 */
@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public DataResponse<?> login(@RequestBody UserInfo userInfo, HttpSession httpSession) {
        if (userInfo.getImageCode().equalsIgnoreCase((String) httpSession.getAttribute("imageCode"))) {
            //TODO 验证码过期 及时刷新
            httpSession.removeAttribute("imageCode");
        } else {
            throw new BusinessException(ExceptionCode.VERIFY_ERROR);
        }
        loginService.login(userInfo);
        httpSession.setAttribute("userInfo", userInfo);
        //防止并发
        HttpSession session = SessionListener.map.get(userInfo.getUsername());
        if (session != null && !session.getId().equals(httpSession.getId())) {
            logger.info("开始销毁session：{}",userInfo.getUsername());
            try {
                session.invalidate();
            } catch (IllegalStateException e) {
                logger.error("session已失效", e);
            }

        }
        SessionListener.map.put(userInfo.getUsername(), httpSession);


        return new DataResponse<>();
    }
}

