package com.silence.robot.exception;

import com.silence.robot.dto.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class LoanControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(LoanControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public DataResponse<?> errorHandle(Exception e){
        log.error("未知错误",e);
        return new DataResponse<>("99999","未知错误");
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public DataResponse<?> errorBusinessHandle(BusinessException e){
        log.error("业务错误",e);
        return new DataResponse<>(e.getCode(),e.getMessage());
    }


}
