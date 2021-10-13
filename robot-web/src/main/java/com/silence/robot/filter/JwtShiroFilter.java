package com.silence.robot.filter;

import com.silence.robot.token.JwtShiroToken;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @author silence
 * @date 2021/10/13
 */
public class JwtShiroFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return new JwtShiroToken(httpRequest.getHeader("token"));
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        if(httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader("token");
    }
}
