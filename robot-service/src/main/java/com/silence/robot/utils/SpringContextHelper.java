/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SpringContextHelper
 * Author:   silence
 * Date:     2019/11/6 10:51
 * Description: 获取spring bean
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 〈一句话功能简述〉<br> 
 * 〈获取spring bean〉
 *
 * @author silence
 * @create 2019/11/6
 * @since 1.0.0
 */
public class SpringContextHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }
    public static <T> T getBean(String name, Class<T> clazz){
        return context.getBean(name, clazz);
    }

    public static Object getBean(String name){
        return context.getBean(name);
    }
}