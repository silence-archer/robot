/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SilenceConfigurationProperties
 * Author:   silence
 * Date:     2019/12/4 14:32
 * Description: 属性文件配置
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈属性文件配置〉
 *
 * @author silence
 * @create 2019/12/4
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "silence.interceptor")
public class SilenceConfigurationProperties {

    /**
     * 不需要进行session拦截的路径
     */
    private List<String> paths;

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "SilenceConfigurationProperties{" +
                "paths=" + paths +
                '}';
    }
}