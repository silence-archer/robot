package com.silence.robot.controller;

import com.silence.robot.dto.DataRequest;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.dto.LoanDto;
import com.silence.robot.dto.User;
import com.silence.robot.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public DataResponse<?> login(@RequestBody User user, HttpSession httpSession) {
        loginService.login(user,httpSession);
        return new DataResponse<>();
    }
}

