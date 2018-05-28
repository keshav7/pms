package com.ekart.transport.core.utils.logging;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class LogExecutionTimeAspect {
    private static final String LOG_MESSAGE_FORMAT = "%s.%s execution time: %dms";

    @Pointcut("execution(@com.ekart.transport.core.utils.logging.LogExecutionTime  * *(..))")
    public void isAnnotated() {}

    @Around("isAnnotated()")
    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = joinPoint.proceed();
        stopWatch.stop();
        logExecutionTime(joinPoint, stopWatch);
        return retVal;
    }

    private void logExecutionTime(ProceedingJoinPoint joinPoint, StopWatch stopWatch) {
        String logMessage = String.format(LOG_MESSAGE_FORMAT, joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), stopWatch.getTime());
        log.info(logMessage);
    }
}