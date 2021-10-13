/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SpringContextConfiguration
 * Author:   silence
 * Date:     2019/11/6 11:25
 * Description: configuration
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.configuration;

import com.silence.robot.utils.SpringContextHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉<br> 
 * 〈configuration〉
 *
 * @author silence
 * @create 2019/11/6
 * @since 1.0.0
 */
@Configuration
public class SpringContextConfiguration {

    @Bean
    public SpringContextHelper getSpringContextHelper(){
        return new SpringContextHelper();
    }

}