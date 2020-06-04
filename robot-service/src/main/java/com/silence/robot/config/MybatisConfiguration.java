package com.silence.robot.config;

import com.silence.robot.interceptor.DynamicDataSourceInterceptor;
import com.silence.robot.interceptor.ParameterInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis自定义配置
 *
 * @author silence
 * @date 2020/6/4
 */
@Configuration
public class MybatisConfiguration {

    @Bean
    public Interceptor getParameterInterceptor() {
        ParameterInterceptor interceptor = new ParameterInterceptor();
        return interceptor;
    }

    @Bean
    public Interceptor getDynamicDataSourceInterceptor() {
        DynamicDataSourceInterceptor interceptor = new DynamicDataSourceInterceptor();
        return interceptor;
    }
}
