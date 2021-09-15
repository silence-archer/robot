package com.silence.robot.filter;

import com.silence.robot.utils.TraceUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author silence
 */
public class MyFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(MyFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //请求开始，放置日志trace
        TraceUtils.begin();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("请求{}开始执行>>>>>>>>>>>>>>>>>", request.getRequestURI());
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //跨域请求，*代表允许全部类型
        //配置了allow-credentials之后，如果allow-origin设为*，跨域时会报错说因为允许credentials，origin不能设为通配*，那么就设置为当前domain。
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //允许请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        //用来指定本次预检请求的有效期，单位为秒，在此期间不用发出另一条预检请求
        response.setHeader("Access-Control-Max-Age", "3600");
        //请求包含的字段内容，如有多个可用哪个逗号分隔如下
        response.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,Authorization, x-ui-request,lang,Content-Length,token");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //访问控制允许凭据，true为允许 可以公用一个session
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 浏览器是会先发一次options请求，如果请求通过，则继续发送正式的post请求
        // 配置options的请求返回
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpStatus.SC_OK);
            response.getWriter().write("OPTIONS returns OK");
            return;
        }
        // 传递业务请求处理
        filterChain.doFilter(servletRequest, servletResponse);
        //请求完毕清空日志trace
        logger.info("请求{}执行完毕<<<<<<<<<<<<<<<<<<<<<<", request.getRequestURI());
        TraceUtils.end();
    }
}
