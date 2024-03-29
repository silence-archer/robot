/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InterceptorConfiguration
 * Author:   silence
 * Date:     2019/12/4 9:50
 * Description: 拦截器配置
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.configuration;

import com.silence.robot.interceptor.JwtAuthenticationInterceptor;
import com.silence.robot.interceptor.LoginInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈拦截器配置〉
 *
 * @author silence
 * @since 2019/12/4
 * 
 */
@Configuration
@EnableConfigurationProperties(SilenceConfigurationProperties.class)
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Resource
    private SilenceConfigurationProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (properties.getAllowCookie()) {
            registry.addInterceptor(new LoginInterceptor())
                    .excludePathPatterns(properties.getPaths());
        } else {
            List<String> paths = properties.getPaths();
            paths.remove("/getUser");
            registry.addInterceptor(new JwtAuthenticationInterceptor())
                    .excludePathPatterns(paths);
        }

    }
}