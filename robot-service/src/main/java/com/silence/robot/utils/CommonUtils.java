/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CommonUtils
 * Author:   silence
 * Date:     2019/10/10 11:22
 * Description: 工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.utils;

import com.silence.robot.exception.BusinessException;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br> 
 * 〈工具类〉
 *
 * @author silence
 * @create 2019/10/10
 * @since 1.0.0
 */
public class CommonUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static boolean isEmpty(Object o){
        if(o instanceof String){
            return o == null || ((String) o).isEmpty();
        }else if(o instanceof List){
            return o == null || ((List) o).size() == 0;
        }else{
            return o == null;
        }
    }

    public static boolean isNotEmpty(Object o){
        return !isEmpty(o);
    }

    public static String getOsName(){
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().contains("windows")){
            osName =  "windows";
        }else if(osName.toLowerCase().contains("mac")){
            osName = "mac";
        }else if(osName.toLowerCase().contains("linux")){
            osName = "linux";
        }
        return osName;

    }

    public static String getCmdCharset(){
        if(getOsName().equals("windows")){
            return "GBK";
        }else{
            return "UTF-8";
        }
    }

    public static <O, T> List<T> copyList(Class<T> destClazz, List<O> sourceObjList) {
        List<T> list = new ArrayList<>();
        if(isEmpty(sourceObjList)){
            return list;
        }
        sourceObjList.forEach(source->{

            T target = null;
            try {
                target = destClazz.newInstance();
            } catch (InstantiationException e) {
                throw new BusinessException("创建对象失败",e);
            } catch (IllegalAccessException e) {
                throw new BusinessException("创建对象失败",e);
            }
            BeanUtils.copyProperties(source, target);
            list.add(target);
        });
        return list;
    }

    public static String getStringDate(Date date){
        return DATE_FORMAT.format(date);
    }

}