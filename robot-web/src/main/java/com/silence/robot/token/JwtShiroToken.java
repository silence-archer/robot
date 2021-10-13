package com.silence.robot.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * TODO
 *
 * @author silence
 * @date 2021/10/13
 */
public class JwtShiroToken implements AuthenticationToken {

    private String token;

    public JwtShiroToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
