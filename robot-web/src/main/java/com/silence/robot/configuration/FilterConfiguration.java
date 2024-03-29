package com.silence.robot.configuration;

import com.silence.robot.filter.MyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author silence
 */
@ConditionalOnProperty(prefix = "silence",name = "filter.cors",havingValue = "true")
@Configuration
public class FilterConfiguration {

    private final Logger logger = LoggerFactory.getLogger(FilterConfiguration.class);

    @Bean
    public FilterRegistrationBean<Filter> myFilter(){
        logger.info("FilterRegistrationBean >>>>>>>>>>>>>> myFilter");
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new MyFilter());
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        return filterRegistrationBean;
    }
}
