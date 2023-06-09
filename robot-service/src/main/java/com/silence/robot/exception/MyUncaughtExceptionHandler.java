package com.silence.robot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义符合线程异常处理器规范的异常处理器
 *
 * @author silence
 * @since 2021/4/26
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    private final Logger logger = LoggerFactory.getLogger(MyUncaughtExceptionHandler.class);
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("线程执行失败>>>>>>>>>>>", e);
    }
}
