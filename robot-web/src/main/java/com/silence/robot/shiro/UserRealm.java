package com.silence.robot.shiro;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TUserMapper;
import com.silence.robot.token.JwtShiroToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/22
 */
public class UserRealm extends AuthorizingRealm {

    private final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Resource
    private TUserMapper userMapper;

    /**
     * 获取授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getSession().getAttribute("userInfo");
        String username = userInfo.getUsername();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = userMapper.selectRole(username);
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        set.add(role);
        //设置该用户拥有的角色
        info.setRoles(set);
        Set<String> permissionSet = new HashSet<>();
        permissionSet.add("*:DELETE");
        permissionSet.add("MENU:*");
        permissionSet.add("*:ADD,DELETE,UPDATE");
        info.setStringPermissions(permissionSet);
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return super.supports(token) || token instanceof JwtShiroToken;
    }

    /**
     * 获取身份验证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            // 从数据库获取对应用户名密码的用户
            String password = userMapper.selectPassword(token.getUsername());
            if (null == password) {
                throw new BusinessException(ExceptionCode.LOGIN_ERROR);
            }
            return new SimpleAuthenticationInfo(token.getUsername(), password, getName());
        }
        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), getName());
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        if (token instanceof UsernamePasswordToken) {
            super.assertCredentialsMatch(token, info);
        }
    }
}
