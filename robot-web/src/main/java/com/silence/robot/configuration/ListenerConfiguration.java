/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ListenerConfiguration
 * Author:   silence
 * Date:     2019/12/3 16:13
 * Description: listener configuration
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.configuration;

import com.silence.robot.listener.SessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉<br> 
 * 〈listener configuration〉
 *
 * @author silence
 * @create 2019/12/3
 * @since 1.0.0
 */
@Configuration
public class ListenerConfiguration {

    @Bean
    public ServletListenerRegistrationBean<SessionListener> getSessionListener(){
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }
}