package com.silence.robot.controller;

import com.alibaba.fastjson.JSONException;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author silence
 */
@RestControllerAdvice
public class RobotControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(RobotControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public DataResponse<?> errorHandle(Exception e){
        log.error("未知错误",e);
        String msg = e.getMessage() == null ? "未知错误" : e.getMessage();
        return new DataResponse<>(99999,msg);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public DataResponse<?> errorBusinessHandle(SQLIntegrityConstraintViolationException e){
        log.error("插入重复",e);
        return new DataResponse<>(99998,"插入重复");
    }

    @ExceptionHandler(JSONException.class)
    public DataResponse<?> errorBusinessHandle(JSONException e){
        log.error("JSON转化失败",e);
        return new DataResponse<>(99997,"JSON转化失败");
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public DataResponse<?> errorBusinessHandle(IncorrectCredentialsException e){
        log.error("用户名密码错误",e);
        return new DataResponse<>(99996,"用户名密码错误");
    }

    @ExceptionHandler(AuthorizationException.class)
    public DataResponse<?> errorBusinessHandle(AuthorizationException e){
        log.error("未授权不能进行此操作",e);
        return new DataResponse<>(99995,"未授权不能进行此操作");
    }

    @ExceptionHandler(BusinessException.class)
    public DataResponse<?> errorBusinessHandle(BusinessException e){
        if(e.getThrowable() != null){
            log.error("科技异常",e.getThrowable());
        }else{
            log.error("业务错误",e);
        }
        return new DataResponse<>(e.getCode(),e.getMsg());
    }


}
