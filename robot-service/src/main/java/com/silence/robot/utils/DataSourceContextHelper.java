package com.silence.robot.utils;

import com.silence.robot.enumeration.DataSourceTypeEnum;

/**
 * 动态数据源工具类
 * @author silence
 * @date 2020/5/29 22:59
 */
public class DataSourceContextHelper {

    private static final ThreadLocal<DataSourceTypeEnum> LOCAL = new ThreadLocal<>();
    /**
     * 设置当前线程的数据库类型为读库
     * @date 2020/5/29 23:10
     */
    public static void read() {
        LOCAL.set(DataSourceTypeEnum.READ);
    }
    /**
     * 设置当前线程的数据库类型为写库
     * @date 2020/5/29 23:15
     */
    public static void write() {
        LOCAL.set(DataSourceTypeEnum.WRITE);
    }
    /**
     * 获取当前线程的数据库类型
     * @return 数据库类型
     * @date 2020/5/29 23:16
     */
    public static DataSourceTypeEnum get() {
        return LOCAL.get();
    }
    /**
     * 清除当前线程的局部变量
     * @date 2020/5/29 23:18
     */
    public static void clear() {
        LOCAL.remove();
    }
}
