/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SchedulerJobConfiguration
 * Author:   silence
 * Date:     2019/12/26 11:26
 * Description: 定时任务调度scheduler工厂
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 〈一句话功能简述〉<br> 
 * 〈定时任务调度scheduler工厂〉
 *
 * @author silence
 * @create 2019/12/26
 * @since 1.0.0
 */
@Configuration
public class SchedulerJobConfiguration {

    @Bean("mySchedulerFactoryBean")
    public SchedulerFactoryBean getSchedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }

}