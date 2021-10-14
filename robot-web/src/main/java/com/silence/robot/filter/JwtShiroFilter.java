package com.silence.robot.filter;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.token.JwtShiroToken;
import com.silence.robot.utils.TraceUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO
 *
 * @author silence
 * @date 2021/10/13
 */
public class JwtShiroFilter extends BasicHttpAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtShiroFilter.class);
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return new JwtShiroToken(httpRequest.getHeader("token"));
    }

    @Override
    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader("token");
    }

    @Override
    protected boolean isLoginAttempt(String authzHeader) {
        return true;
    }

    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        try {
            DataResponse<?> dataResponse = new DataResponse<>(ExceptionCode.AUTH_ERROR.getCode(), ExceptionCode.AUTH_ERROR.getMsg());
            response.getWriter().print(JSONObject.toJSONString(dataResponse));
        } catch (IOException e) {
            logger.error("写入响应信息失败", e);
        }
        return false;
    }
}
