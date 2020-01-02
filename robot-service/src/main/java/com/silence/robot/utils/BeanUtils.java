/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: BeanUtils
 * Author:   silence
 * Date:     2020/1/2 16:29
 * Description: bean 操作工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.nio.CharBuffer;

/**
 * 〈一句话功能简述〉<br> 
 * 〈bean 操作工具类〉
 *
 * @author silence
 * @create 2020/1/2
 * @since 1.0.0
 */
public class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static void setProperty(Object bean, String name, Object value){
        try {
            Method[] methods = bean.getClass().getMethods();

            Method method = findMethodByName(methods, "set" + toUpperCaseFirstOne(name));
            method.invoke(bean, value);
        } catch (Exception e) {
            logger.error("对象{}的属性{}，塞入属性值{}失败",bean.getClass().getName(), name, value, e);
        }
    }

    private static String toUpperCaseFirstOne(String str){
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private static Method findMethodByName(Method[] methods, String name){
        for (int i = 0; i < methods.length; i++) {
            if(methods[i].getName().equals(name)){
                return methods[i];
            }
        }
        return null;
    }


}