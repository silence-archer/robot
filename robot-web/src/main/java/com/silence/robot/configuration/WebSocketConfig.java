/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: WebSocketConfig
 * Author:   silence
 * Date:     2019/11/8 22:13
 * Description: websocket配置
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;

/**
 * 〈一句话功能简述〉<br> 
 * 〈websocket配置〉
 *
 * @author silence
 * @since 2019/11/8
 * 
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter getServerEndpointExporter(){
        return new ServerEndpointExporter();
    }
}