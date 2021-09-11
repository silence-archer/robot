package com.silence.robot.controller;

import com.alibaba.fastjson.JSONException;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
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
        return new DataResponse<>(99999,"未知错误");
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
