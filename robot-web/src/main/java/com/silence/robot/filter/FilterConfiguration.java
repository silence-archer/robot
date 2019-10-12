package com.silence.robot.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "silence",name = "filter.cors",havingValue = "true")
@Configuration
public class FilterConfiguration {

    private Logger logger = LoggerFactory.getLogger(FilterConfiguration.class);

    @Bean
    public FilterRegistrationBean myFilter(){
        logger.info("FilterRegistrationBean >>>>>>>>>>>>>> myFilter");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilter());
        return filterRegistrationBean;
    }
}
