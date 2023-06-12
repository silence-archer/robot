/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LoginInterceptor
 * Author:   silence
 * Date:     2019/12/4 9:45
 * Description: 登录拦截
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.interceptor;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 〈一句话功能简述〉<br> 
 * 〈登录拦截〉
 *
 * @author silence
 * @since 2019/12/4
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("当前session id 为：{}，url为：{}",request.getSession().getId(), request.getRequestURI());
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if(userInfo == null){
            throw new BusinessException(ExceptionCode.SESSION_ERROR);
        }
        return true;
    }
}