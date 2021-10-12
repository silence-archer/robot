package com.silence.robot.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.UUID;

/**
 * TODO
 *
 * @author silence
 * @date 2021/10/12
 */
public class MySessionIdGenerator implements SessionIdGenerator {
    @Override
    public Serializable generateId(Session session) {
        String id = (String) session.getAttribute("token");
        //如果请求头中有 token 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return UUID.randomUUID().toString();
        }
    }
}
