package com.silence.robot.interceptor;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.listener.JwtSessionListener;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.DateUtils;
import com.silence.robot.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/12
 */
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (CommonUtils.isEmpty(token)) {
            throw new BusinessException(ExceptionCode.SESSION_ERROR);
        }
        String userId = JwtUtils.getAudience(token);

        if (JwtSessionListener.getToken(userId) == null || CommonUtils.isNotEquals(JwtSessionListener.getToken(userId), token)) {
            throw new BusinessException(ExceptionCode.SESSION_ERROR);
        }
        if (JwtUtils.getExpiresAt(token).compareTo(new Date()) < 0) {
            //token超时
            JwtSessionListener.removeUserInfo(userId);
            throw new BusinessException(ExceptionCode.SESSION_ERROR);
        }

        JwtUtils.verifyToken(token, userId);
        request.getSession().setAttribute("userInfo", JwtUtils.getUserInfo(token));
        Date date = DateUtils.addMinutes(new Date(), 5);
        if (JwtUtils.getExpiresAt(token).compareTo(date) < 0) {
            //token还有五分钟超时，自动刷新
            logger.info("token快过期，自动刷新>>>>>>>>>>>>>>>>");
            UserInfo userInfo = JwtUtils.getUserInfo(token);
            token = JwtUtils.createToken(userInfo);
            JwtSessionListener.putToken(userInfo.getId(), token);
        }

        response.setHeader("token", token);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        Date date = DateUtils.addMinutes(new Date(), 29);
//        String token = request.getHeader("token");
//        if (JwtUtils.getExpiresAt(token).compareTo(date) < 0) {
//            //token还有五分钟超时，自动刷新
//            logger.info("token快过期，自动刷新>>>>>>>>>>>>>>>>");
//            UserInfo userInfo = JwtUtils.getUserInfo(token);
//            token = JwtUtils.createToken(userInfo);
//            JwtSessionListener.putToken(userInfo.getId(), token);
//        }
//
//        response.setHeader("token", token);
    }
}
