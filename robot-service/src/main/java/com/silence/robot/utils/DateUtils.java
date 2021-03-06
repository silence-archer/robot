package com.silence.robot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author silence
 * @date 2021/4/10
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 字符串转日期
     * @author silence
     * @date 2021/4/10 2:51
     * @param date yyyy-MM-dd HH:mm:ss.SSS
     * @return java.util.Date
     */
    public static Date parseDateByString1(String date) {
        if (CommonUtils.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            logger.error("{}日期转化失败", date, e);
        }
        return null;
    }

    /**
     * 字符串转日期
     * @author silence
     * @date 2021/4/10 2:51
     * @param date yyyy-MM-dd HH:mm:ss
     * @return java.util.Date
     */
    public static Date parseDateByString2(String date) {
        if (CommonUtils.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            logger.error("{}日期转化失败", date, e);
        }
        return null;
    }

}
