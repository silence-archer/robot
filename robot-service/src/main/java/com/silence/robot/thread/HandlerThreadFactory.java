package com.silence.robot.thread;

import com.silence.robot.exception.MyUncaughtExceptionHandler;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂用来将任务附着给线程，并给该线程绑定一个异常处理器
 *
 * @author silence
 * @since 2021/4/26
 */
public class HandlerThreadFactory implements ThreadFactory {
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public HandlerThreadFactory() {
        group = Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" +
                POOL_NUMBER.getAndIncrement() +
                "-thread-";
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        //设定线程工厂的异常处理器
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return t;
    }
}
