/**
 * @title TraceUtils.java
 * @author oe_machaohui
 * @since 2020/4/8 17:33
 * @copyright 2020 XXX有限公司版权所有
 */
package com.silence.robot.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.MDC;

/**
 * @author oe_machaohui
 * @description 日志流水赋值
 * @since 2020/4/8 17:33
 */
public class TraceUtils {

    private static final InheritableThreadLocal<Map<String, String>> STRING_INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal<>();
    private static final String TRACE_ID = "TRACE_ID";
    private static final String LOCAL_ID = "LOCAL_ID";
    private static final String USERNAME = "USERNAME";

    public static void begin(){
        String uuid = CommonUtils.getUuid();
        Map<String, String> map = new HashMap<>();
        map.put(TRACE_ID, uuid);
        STRING_INHERITABLE_THREAD_LOCAL.set(map);
        MDC.put(TRACE_ID, uuid);
    }

    public static void begin(String traceId, String localId){
        MDC.put(TRACE_ID, traceId);
        MDC.put(LOCAL_ID, localId);
    }

    public static void end(){
        MDC.clear();
        STRING_INHERITABLE_THREAD_LOCAL.remove();
    }

    public static String getTraceId(){
        return MDC.get(TRACE_ID);
    }

    public static String getParentTraceId(){
        return STRING_INHERITABLE_THREAD_LOCAL.get().get(TRACE_ID);
    }
    public static String getParentLoginUsername() {
        return STRING_INHERITABLE_THREAD_LOCAL.get().get(USERNAME);
    }

    public static void setParentLoginUsername(String username) {
        STRING_INHERITABLE_THREAD_LOCAL.get().put(USERNAME, username);
    }

    public static String getLocalId(){
        return MDC.get(LOCAL_ID);
    }
}
