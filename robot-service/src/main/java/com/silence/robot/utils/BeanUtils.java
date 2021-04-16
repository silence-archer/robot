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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.Feature;
import com.silence.robot.enumeration.FileFormatEnum;
import com.silence.robot.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉<br> 
 * 〈bean 操作工具类〉
 *
 * @author silence
 * @create 2020/1/2
 * @since 1.0.0
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static void setProperty(Object bean, String name, Object value){
        try {
            Method[] methods = bean.getClass().getMethods();

            Method method = findMethodByName(methods, "set" + toUpperCaseFirstOne(name));
            if (method != null) {
                method.invoke(bean, value);
            }
        } catch (Exception e) {
            logger.error("对象{}的属性{}，塞入属性值{}失败",bean.getClass().getName(), name, value, e);
        }
    }

    public static Object getProperty(Object bean, String name) {
        try {
            Method[] methods = bean.getClass().getMethods();

            Method method = findMethodByName(methods, "get" + toUpperCaseFirstOne(name));
            if (method != null) {
                return method.invoke(bean);
            }
        } catch (Exception e) {
            logger.error("对象{}的属性{}，获取属性值失败",bean.getClass().getName(), name, e);
        }
        return null;
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

    /**
     * 将Map转化文件格式
     * repayDt:2020-01-23|repayAmt:12345|total:123
     * @param map 转化对象
     * @return 文件格式字符串
     * @author oe_machaohui
     * @date 2020/5/29 17:05
     */
    public static String mapToStringForJDDC(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        map.forEach((s, o) -> {
            stringBuilder.append(s).append(":").append(o).append("|");
        });
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    /**
     * 将Map对象转化文件格式
     * @param map 转化对象
     * @return 文件格式字符串
     * @author oe_jiazaoyang
     * @date 2020/7/9 16:46
     */
    public static String mapToStringForYeh(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        map.forEach((s, o) -> {
            stringBuilder.append(o).append("|");
        });
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }
    /**
     * 将Bean对象转化为文件格式
     * repayDt:2020-01-23|repayAmt:12345|total:123
     * @param o 转化对象
     * @return 文件格式字符串
     * @author oe_machaohui
     * @date 2020/5/29 17:53
     */
    public static String beanToStringForJDDC(Object o) {
        String s = JSONObject.toJSONString(o);
        LinkedHashMap<String, Object> map = JSON.parseObject(s, LinkedHashMap.class, Feature.OrderedField);
        return mapToStringForJDDC(map);
    }

    /**
     * 将Bean对象转化为文件格式
     * @param o 转化对象
     * @return 件格式字符串
     * @author oe_machaohui
     * @date 2020/7/9 16:43
     */
    public static String beanToStringForYeh(Object o) {
        String s = JSONObject.toJSONString(o);
        LinkedHashMap<String, Object> map = JSON.parseObject(s, LinkedHashMap.class, Feature.OrderedField);
        System.out.println(map);
        return mapToStringForYeh(map);
    }
    /**
     * 将文件内容转化为DTO
     * @param clazz 转化类型
     * @param fileFormatEnum 文件类型
     * @param str 文件内容
     * @param <T> 泛型
     * @return 文件内容转换类
     * @author oe_machaohui
     * @date 2020/6/2 15:06
     */
    public static <T> T stringToObject(String str, Class<T> clazz, FileFormatEnum fileFormatEnum) {
        T parseObject = null;
        switch (fileFormatEnum) {
            case JSON:
                parseObject = JSONObject.parseObject(str, clazz);
                break;
            case VERTICAL_SEPARATOR_SKIP:
                parseObject = verticalSeparatorToObjectForSkip(str, clazz, fileFormatEnum.getName());
                break;
            default:
                parseObject = verticalSeparatorToObject(str, clazz, fileFormatEnum.getName());
                break;
        }
        return parseObject;
    }
    /**
     * 竖线分割文件内容转化为DTO
     * @param str 文件内容
     * @param <T> DTO类型
     * @param clazz DTO类型
     * @return DTO对象
     * @author oe_machaohui
     * @date 2020/6/2 17:11
     */
    public static <T> T verticalSeparatorToObject(String str, Class<T> clazz, String splitStr) {
        try {
            T o = clazz.newInstance();
            init(o);
            String s = JSONObject.toJSONString(o);
            LinkedHashMap<String, Object> map = JSON.parseObject(s, LinkedHashMap.class, Feature.OrderedField);
            Set<String> set = map.keySet();
            AtomicInteger i = new AtomicInteger(0);
            String[] strs = str.split(splitStr);
            set.forEach(name -> {
                String value = "";
                if(i.get()<strs.length) {
                    value = strs[i.getAndIncrement()];
                }
                if(CommonUtils.isNotEmpty(value)) {
                    BeanUtils.setProperty(o, name, value);
                }

            });
            return o;
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("{}对象创建失败", clazz.getName(), e);
        }
        return null;

    }

    /**
     * 竖线分割文件内容转化为DTO--跳过某些字段
     * @param str 文件内容
     * @param <T> DTO类型
     * @param clazz DTO类型
     * @return DTO对象
     * @author oe_machaohui
     * @date 2020/6/2 17:11
     */
    public static <T> T verticalSeparatorToObjectForSkip(String str, Class<T> clazz, String splitStr) {
        try {
            T o = clazz.newInstance();
            init(o);
            String[] strs = str.split(splitStr);
            List<Field> fieldList = new ArrayList<>();
            for(Class tempClass = clazz; tempClass != null && !"java.lang.object".equals(tempClass.getName().toLowerCase()); tempClass = tempClass.getSuperclass()) {
                fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            }
            fieldList.forEach(field -> {
                JSONField jsonField = field.getAnnotation(JSONField.class);
                int ordinal = jsonField.ordinal();
                if (strs.length >= ordinal && CommonUtils.isNotEmpty(strs[ordinal-1])) {

                    BeanUtils.setProperty(o, field.getName(), strs[ordinal-1]);
                }
            });
            return o;
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("{}对象创建失败", clazz.getName(), e);
        }
        return null;

    }



    /**
     * 将对象中为空的属性进行赋默认值处理
     * @param bean 对象
     * @param excludeFields 去除某些字段赋默认值
     */
    public static void init(Object bean, String... excludeFields) {
        if (bean == null) {
            return;
        }
        List<String> excludeList = new ArrayList<>();

        if (excludeFields != null) {
            excludeList = Arrays.asList(excludeFields);
        }
        List<Field> fieldList = new ArrayList<>();
        for(Class tempClass = bean.getClass(); tempClass != null && !"java.lang.object".equals(tempClass.getName().toLowerCase()); tempClass = tempClass.getSuperclass()) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
        }
        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                Object o = field.get(bean);
                if (o == null && !excludeList.contains(field.getName())) {
                    Type genericType = field.getGenericType();
                    if (genericType.getTypeName().contains("String")) {
                        field.set(bean, "");
                    } else if (genericType.getTypeName().contains("Integer")) {
                        field.set(bean, 0);
                    } else if (genericType.getTypeName().contains("Long")) {
                        field.set(bean, 0L);
                    } else if (genericType.getTypeName().contains("BigDecimal")) {
                        field.set(bean, BigDecimal.ZERO.setScale(2));
                    }
                }
            } catch (IllegalAccessException e) {
                logger.error("对象{}的属性名{}赋默认值失败", bean.getClass().getName(), field.getName(), e);
            }
        }
    }

    public static <O, T> List<T> copyList(Class<T> destClazz, List<O> sourceObjList) {
        return copyList(destClazz, sourceObjList, null);
    }

    public static <O, T> List<T> copyList(Class<T> destClazz, List<O> sourceObjList, String key) {
        List<T> list = new ArrayList<>();
        if(CommonUtils.isEmpty(sourceObjList)){
            return list;
        }
        sourceObjList.forEach(source->{

            T target = null;
            try {
                target = destClazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BusinessException("创建对象失败",e);
            }
            org.springframework.beans.BeanUtils.copyProperties(source, target);
            if (CommonUtils.isNotEmpty(key)) {
                BeanUtils.setProperty(target, key, CommonUtils.getUuid());
            }
            list.add(target);
        });
        return list;
    }

    public static <O, T> T copy(Class<T> destClazz, O sourceObj) {
        if(CommonUtils.isEmpty(sourceObj)){
            return null;
        }
        T target = null;
        try {
            target = destClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BusinessException("创建对象失败",e);
        }
        org.springframework.beans.BeanUtils.copyProperties(sourceObj, target);
        return target;
    }




}