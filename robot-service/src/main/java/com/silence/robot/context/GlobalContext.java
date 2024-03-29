package com.silence.robot.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.silence.robot.utils.TraceUtils;

/**
 * TODO
 *
 * @author silence
 * @since 2021/9/29
 */
public class GlobalContext {
    private final static Logger logger = LoggerFactory.getLogger(GlobalContext.class);
    private static final Map<String, AtomicInteger> USER_OPERATION_LOCK = new ConcurrentHashMap<>();
    private static final Map<String, AtomicBoolean> USER_PROGRESS_LOCK = new ConcurrentHashMap<>();
    private static final ThreadLocal<Map<String, Object>> HTTP_CLIENT_CONTEXT = new ThreadLocal<>();

    public static void getUserOperationLock(String username) {
        AtomicBoolean atomicBoolean = USER_PROGRESS_LOCK.get(username);
        AtomicInteger lockFlag = getUserOperation(username);
        lockFlag.decrementAndGet();
        atomicBoolean.set(false);
    }

    public static void getFirstUserOperationLock(String username) {
        getUserOperation(username);
        AtomicBoolean atomicBoolean = USER_PROGRESS_LOCK.get(username);
        atomicBoolean.set(true);
    }

    public static AtomicInteger getUserOperation(String username) {
        AtomicInteger lockFlag = USER_OPERATION_LOCK.get(username);
        while (lockFlag == null) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                logger.error("线程被打断", e);
            }
            lockFlag = USER_OPERATION_LOCK.get(username);
        }
        logger.info("{}开始拿到锁>>>>>>", username);
        return lockFlag;
    }

    public static AtomicInteger getRemainSize(String username) {
        AtomicBoolean atomicBoolean = USER_PROGRESS_LOCK.get(username);
        while (atomicBoolean == null || atomicBoolean.get()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                logger.error("线程被打断", e);
            }
            atomicBoolean = USER_PROGRESS_LOCK.get(username);
        }
        if (USER_OPERATION_LOCK.get(username) != null) {
            atomicBoolean.set(true);
        }
        return USER_OPERATION_LOCK.get(username);

    }

    public static void setUserOperationLock(String username, Integer fileSize) {
        logger.info("{}开始加锁>>>>>>{}", username, fileSize);
        USER_OPERATION_LOCK.put(username, new AtomicInteger(fileSize));
        USER_PROGRESS_LOCK.put(username, new AtomicBoolean(true));
    }

    public static void releaseUserOperationLock(String username) {
        AtomicInteger lockFlag = USER_OPERATION_LOCK.get(username);
        while (lockFlag == null || lockFlag.get() != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                logger.error("线程被打断", e);
            }
            lockFlag = USER_OPERATION_LOCK.get(username);
        }
        logger.info("{}开始释放锁>>>>>>", username);
        USER_OPERATION_LOCK.remove(username);
    }

    public static void initHttpClientContext() {
        HTTP_CLIENT_CONTEXT.set(new HashMap<>());
        setHttpClientContext("createUser", TraceUtils.getParentLoginUsername());
    }
    public static void setHttpClientContext(String key, String value) {
        Map<String, Object> map = HTTP_CLIENT_CONTEXT.get();
        map.put(key, value);
    }

    public static Object getHttpClientContext(String key) {
        return HTTP_CLIENT_CONTEXT.get().get(key);
    }


}
