package com.silence.robot.aop;

import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import com.silence.robot.utils.TraceUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 异步方法拦截
 *
 * @author silence
 * @date 2021/10/9
 */
@Aspect
@Component
public class AsyncMethodAdvice {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
    public void pointcutMethod() {}

    @Around("pointcutMethod()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        TraceUtils.begin(TraceUtils.getParentTraceId(), CommonUtils.getUuid());
        return proceedingJoinPoint.proceed();
    }
}
