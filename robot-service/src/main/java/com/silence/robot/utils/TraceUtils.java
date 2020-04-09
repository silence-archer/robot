/**
 * @title TraceUtils.java
 * @author oe_machaohui
 * @date 2020/4/8 17:33
 * @copyright 2020 XXX有限公司版权所有
 */
package com.silence.robot.utils;

import org.slf4j.MDC;

/**
 * @author oe_machaohui
 * @description 日志流水赋值
 * @date 2020/4/8 17:33
 */
public class TraceUtils {

    private static final String TRACE_ID = "TRACE_ID";
    private static final String LOCAL_ID = "LOCAL_ID";

    public static void begin(){
        MDC.put(TRACE_ID, CommonUtils.getUuid());
    }

    public static void begin(String traceId, String localId){
        MDC.put(TRACE_ID, traceId);
        MDC.put(LOCAL_ID, localId);
    }

    public static void end(){
        MDC.clear();
    }

    public static String getTraceId(){
        return MDC.get(TRACE_ID);
    }

    public static String getLocalId(){
        return MDC.get(LOCAL_ID);
    }
}
