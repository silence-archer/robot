package com.silence.robot.configuration;

import com.silence.robot.shiro.UserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * TODO
 *
 * @author silence
 * @date 2021/9/22
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // logged in users with the 'admin' role
        //比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，
        // 当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。

        // logged in users with the 'document:read' permission
        //比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，
        // 并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。

        // all other pPathMatchingFilteraths require a logged in user
        //比如/admins/user/**=authc表示需要认证才能使用，没有参数
        //比如/admins/**=anon 没有参数，表示可以匿名使用。
        chainDefinition.addPathDefinition("/verifyCode", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        return chainDefinition;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new PasswordMatcher();
    }


    @Bean
    public Realm realm(@Autowired CredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        //解决shiro 与spring-aop冲突问题
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public SecurityManager securityManager(@Autowired Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(realm);
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) defaultWebSecurityManager.getSubjectDAO();
        //禁用shiro session
        ((DefaultWebSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator()).setSessionStorageEnabled(false);
        return defaultWebSecurityManager;
    }


}
