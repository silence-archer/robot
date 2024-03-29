/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: HttpUtils
 * Author:   silence
 * Date:     2020/1/7 13:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.utils;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.UserInfo;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.listener.JwtSessionListener;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author silence
 * @since 2020/1/7
 * 
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static JSONObject doPost(String uri, Map<String, String> headers, String bodys, Map<String, String> cookies) {
        logger.info("post请求路径{}, 请求头{}, 请求体{}",uri, headers, bodys);
        HttpPost request = new HttpPost(uri);
        headers.forEach(request::setHeader);
        HttpEntity httpEntity = new StringEntity(bodys, ContentType.APPLICATION_JSON);
        request.setEntity(httpEntity);
        return httpClientExecute(request, cookies);
    }

    public static JSONObject doPost(String uri, String bodys) {
        return doPost(uri, new HashMap<>(0), bodys, new HashMap<>(0));
    }

    public static JSONObject doPost(String uri, Map<String, String> headers, String bodys) {
        return doPost(uri, headers, bodys, new HashMap<>(0));
    }

    public static JSONObject doPost(String uri, Map<String, String> cookies) {
        return doPost(uri, new HashMap<>(0), "", cookies);
    }

    public static JSONObject doGet(String uri) {
        logger.info("get请求路径{}",uri);
        return httpClientExecute(new HttpGet(uri));
    }


    public static JSONObject httpClientExecute(HttpRequestBase request) {

        return httpClientExecute(request, new HashMap<>(0));
    }

    public static JSONObject httpClientExecute(HttpRequestBase request, Map<String, String> cookies) {
        InputStream content = null;
        try {
            content = httpClientExecuteByStream(request, cookies).getEntity().getContent();
        } catch (Exception e) {
            logger.error("使用流失败",e);
            throw new BusinessException(ExceptionCode.HTTP_REQUEST_ERROR);
        }
        JSONObject jsonMap = FileUtils.getJsonMap(content);
        logger.info("请求响应信息{}", jsonMap);
        return jsonMap;
    }

    public static CloseableHttpResponse httpClientExecuteByStream(HttpRequestBase request) {

        return httpClientExecuteByStream(request, new HashMap<>(0));
    }
    public static CloseableHttpResponse httpClientExecuteByStream(HttpRequestBase request, Map<String, String> cookies) {
        InputStream content = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            BasicCookieStore cookieStore = new BasicCookieStore();
            cookies.forEach((s, s2) -> {
                BasicClientCookie cookie = new BasicClientCookie(s, s2);
                cookie.setPath("/");
                cookie.setVersion(0);
                cookie.setDomain(request.getURI().getHost());
                cookieStore.addCookie(cookie);
            });
            httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
            httpResponse = httpClient.execute(request);
            int stat = httpResponse.getStatusLine().getStatusCode();
            if (stat != 200) {
                logger.error("请求失败 Http code: {}", stat);
                logger.error("http header error msg: {}", httpResponse.getFirstHeader("X-Ca-Error-Message"));
                logger.error("Http body error msg: {}", EntityUtils.toString(httpResponse.getEntity()));
                throw new BusinessException(ExceptionCode.HTTP_REQUEST_ERROR);
            }
        } catch (IOException e) {
            logger.error("使用流失败", e);

        }
        return httpResponse;
    }
    
    /**
     * @description: 获取当前session的信息
     * @param:
     * @return:
     * @auther: oe_machaohui
     * @since: 2020/4/7 18:39
     */
    public static String getLoginUserName(){

        return getUserInfo() == null ? "admin" : getUserInfo().getUsername();
    }

    public static InputStream getStreamByHttp(String uri) {
        HttpGet request = new HttpGet(uri);
        try {
            return httpClientExecuteByStream(request).getEntity().getContent();
        } catch (IOException e) {
            logger.error("使用流失败", e);
        }
        return null;

    }

    public static String getIpAddress() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new BusinessException(ExceptionCode.AUTH_ERROR);
        }
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getRemoteAddr();
    }

    public static UserInfo getUserInfo(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        UserInfo userInfo = null;
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        }
        return userInfo;
    }

    public static void removeUserInfo(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
            JwtSessionListener.removeUserInfo(userInfo.getId());
            SecurityUtils.getSubject().logout();
        }

    }

    public static void putUserInfo(UserInfo userInfo, String token){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            request.getSession().setAttribute("userInfo", userInfo);
        }
        JwtSessionListener.putToken(userInfo.getId(), token);
    }

    public static void putImageCode(String imageCode){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            request.getSession().setAttribute("imageCode", imageCode+"-"+System.currentTimeMillis());
        }
        JwtSessionListener.putImageCode(imageCode);
    }

    public static String getImageCode(){
        String imageCode = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            imageCode = (String) request.getSession().getAttribute("imageCode");
        }
        if (CommonUtils.isEmpty(imageCode)) {
            imageCode = JwtSessionListener.getImageCode();
        }

        return imageCode;
    }

    public static void removeImageCode(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            request.getSession().removeAttribute("imageCode");
        }
        JwtSessionListener.removeImageCode();

    }



}
