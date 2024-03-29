/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SessionListener
 * Author:   silence
 * Date:     2019/12/3 16:06
 * Description: session listener
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.listener;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.silence.robot.domain.UserInfo;

/**
 * 〈一句话功能简述〉<br> 
 * 〈session listener〉
 *
 * @author silence
 * @since 2019/12/3
 * 
 */
public class SessionListener implements HttpSessionListener {
    private final Logger logger = LoggerFactory.getLogger(SessionListener.class);

    public static ConcurrentHashMap<String,HttpSession> map = new ConcurrentHashMap<>();


    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        UserInfo userInfo = (UserInfo) se.getSession().getAttribute("userInfo");
        if(userInfo != null){
            map.remove(userInfo.getUsername());
        }
    }
}