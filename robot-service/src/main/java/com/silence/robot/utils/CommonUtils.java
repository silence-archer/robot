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

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.constants.RobotConstants;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.thread.HandlerThreadFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static final String SLAT = "&%5123***&&%%$$#@";

    public static final String JOB_PACKAGE_NAME = "com.silence.robot.job.";

    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(4, 8, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<>(300), new HandlerThreadFactory());

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static boolean isEmpty(Object o){
        if(o instanceof String){
            return ((String) o).isEmpty();
        }else if(o instanceof List){
            return ((List) o).size() == 0;
        }else{
            return o == null;
        }
    }

    public static boolean isAllEmpty(Object... objects) {
        if(objects != null) {
            for (Object object : objects) {
                if(isNotEmpty(object)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean existEmpty(Object... objects) {
        if(objects != null) {
            for (Object object : objects) {
                if(isEmpty(object)) {
                    return true;
                }
            }
        }
        return false;
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
        if("windows".equals(getOsName())){
            return "GBK";
        }else{
            return "UTF-8";
        }
    }



    public static String getStringDate(Date date){
        return DATE_FORMAT.format(date);
    }

    public static String strToMD5(String str){
        str = str+"/"+SLAT;
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

    public static String arrayToString(String[] args){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[0]).append("|");
        }

        return sb.toString();
    }

    public static Integer toInteger(Object o){
        if(o != null){
            return Integer.valueOf(o.toString());
        }
        return null;
    }

    public static Long toLong(Object o){
        if(o != null){
            return Long.valueOf(o.toString());
        }
        return null;
    }

    public static String toString(Object o){
        if(o != null){
            return o.toString();
        }
        return null;
    }

    public static List<JSONObject> getResultSetByDataBase(String type, String sql, String url, String user, String password) {
        List<JSONObject> list = new ArrayList<>();
        try {
            String databaseUrl = "";
            if (RobotConstants.DATABASE_TYPE_ORACLE.equals(type)) {
                databaseUrl = "jdbc:oracle:thin:@"+url;
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } else if (RobotConstants.DATABASE_TYPE_MYSQL.equals(type)) {
                databaseUrl = "jdbc:mysql://"+url;
                Class.forName("com.mysql.cj.jdbc.Driver");
            } else if (RobotConstants.DATABASE_TYPE_SQLITE.equals(type)) {
                databaseUrl = "jdbc:sqlite://"+url;
                Class.forName("org.sqlite.JDBC");
            }

            logger.debug("开始执行自定义sql>>>>>>>[{}]", sql);
            Connection conn = DriverManager.getConnection(databaseUrl, user, password);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i+1);
                    String columnValue = resultSet.getString(columnName);
                    if (CommonUtils.isNotEmpty(columnValue)) {
                        jsonObject.put(columnName, columnValue);
                    }
                }
                list.add(jsonObject);
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            throw new BusinessException("数据库连接失败", e);
        }
        logger.debug("sql语句执行结果>>>>>>>[{}]", list);
        return list;
    }

    public static String getPkQuerySql(String type, String tableName, String owner) {
        String pkQuerySql = "";
        if (RobotConstants.DATABASE_TYPE_ORACLE.equals(type)) {
            tableName = "and a.table_name = '"+ tableName+"'";
            owner = "and a.owner = '"+ owner+"'";
            pkQuerySql = "select a.* from dba_cons_columns a left join dba_constraints b on a.constraint_name = b.constraint_name and a.owner = b.owner where b.constraint_type = 'P'"+tableName+owner;

        } else if (RobotConstants.DATABASE_TYPE_MYSQL.equals(type)) {
            pkQuerySql = "SELECT column_name FROM INFORMATION_SCHEMA.`KEY_COLUMN_USAGE` WHERE table_name='"+tableName+"' AND constraint_name='PRIMARY'";
        }
        return pkQuerySql;
    }

    public static <T> RobotPage<T> getSubList(List<T> list, Integer page, Integer limit) {
        if (isEmpty(list)) {
            return new RobotPage<>(0L, new ArrayList<>(0));
        }
        if (existEmpty(page, limit)) {
            return new RobotPage<>((long)list.size(), list);
        }
        int fromIndex = (page - 1) * limit;
        int toIndex = fromIndex + limit;
        if (list.size() < fromIndex) {
            return new RobotPage<>(0L, new ArrayList<>(0));
        }
        if (list.size() < toIndex) {
            toIndex = list.size();
        }
        List<T> subList = list.subList(fromIndex, toIndex);
        return new RobotPage<>((long)list.size(), subList);
    }



}