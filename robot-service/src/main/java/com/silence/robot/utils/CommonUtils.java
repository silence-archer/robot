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
import com.silence.robot.exception.ExceptionCode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 〈一句话功能简述〉<br> 
 * 〈工具类〉
 *
 * @author silence
 * @create 2019/10/10
 * @since 1.0.0
 */
public class CommonUtils {

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static final String slat = "&%5123***&&%%$$#@";

    public static final String JOB_PACKAGE_NAME = "com.silence.robot.job.";

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

    public static String strToMD5(String str){
        str = str+"/"+slat;
        return DigestUtils.md5Hex(str.getBytes());
    }

    /**
     * sha1加密
     * @param data
     * @return
     */
    public static String sha1(String data){
        return DigestUtils.sha1Hex(data);
    }

    /**
     * 集合去重
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static String checkCronExpr(String cronExpr){
        try {
            CronExpression cronExpression = new CronExpression(cronExpr);
            return cronExpression.getCronExpression();
        } catch (ParseException e) {
            logger.error("cron表达式输入有误",e);
            throw new BusinessException(ExceptionCode.CRON_ERROR);
        }

    }





    public static void main(String[] args){
        List<String> list = Arrays.asList("p67845251", "1577156609", "705061741");

        //对三个入参进行字典序排序
        Collections.sort(list);
        String s = sha1(list.get(0) + list.get(1) + list.get(2));
        System.out.println(s.equals("237c18c4c48641bfc84ab38eb29533c2a069af05"));
    }



}