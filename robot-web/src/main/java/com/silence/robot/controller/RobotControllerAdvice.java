package com.silence.robot.controller;

import com.silence.robot.exception.BusinessException;
import com.silence.robot.dto.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RobotControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(RobotControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public DataResponse<?> errorHandle(Exception e){
        log.error("未知错误",e);
        return new DataResponse<>("99999","未知错误");
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public DataResponse<?> errorBusinessHandle(BusinessException e){
        if(e.getThrowable() != null){
            log.error("科技异常",e.getThrowable());
        }else{
            log.error("业务错误",e);
        }
        return new DataResponse<>(e.getCode(),e.getMessage());
    }


}
