package com.silence.robot.controller;

import com.silence.robot.dto.DataResponse;
import com.silence.robot.dto.User;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @GetMapping("/getUser")
    public DataResponse<User> getUser(HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            throw new BusinessException(ExceptionCode.AUTH_ERROR);
        }
        DataResponse<User> userDataResponse = new DataResponse<>();
        userDataResponse.setData(user);
        return userDataResponse;
    }
}
