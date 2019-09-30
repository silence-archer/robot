package com.silence.robot.service;

import com.silence.robot.dto.User;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginService {
    public void login(User user, HttpSession httpSession){
        if(httpSession.getAttribute("imageCode").equals(user.getImageCode())){
            //TODO 验证码过期
            httpSession.removeAttribute("imageCode");
        }else{
            throw new BusinessException(ExceptionCode.VERIFY_ERROR);
        }
        if("admin".equals(user.getUsername()) && "admin".equals(user.getUsername())){
            user.setNickname("管理员");
            httpSession.setAttribute("user",user);
        }else{
            throw new BusinessException(ExceptionCode.LOGIN_ERROR);
        }
    }
}
