package com.donalola.util.logging.aspect;

import com.donalola.util.logging.NeedLog;
import com.donalola.util.logging.helper.LoggerHelper;
import com.donalola.util.serializer.JsonSerializer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Aspect
@Component
public class RequestAspectLogger {

    private final LoggerHelper loggerHelper;
    private final JsonSerializer jsonSerializer;

    public RequestAspectLogger(LoggerHelper loggerHelper, JsonSerializer jsonSerializer) {
        this.loggerHelper = loggerHelper;
        this.jsonSerializer = jsonSerializer;
    }

    @Before(value = "@within(needLog) && @annotation(getMapping)")
    public void before(JoinPoint joinPoint, GetMapping getMapping, NeedLog needLog) {
        loggerHelper.sendBeforeMethodToLog(joinPoint);
    }

    @AfterReturning(value = "@within(needLog) && @annotation(getMapping) ", returning = "result")
    public void afterReturning(JoinPoint joinPoint, GetMapping getMapping, NeedLog needLog, Object result) {
        loggerHelper.sendAfterMethodToLog(joinPoint, jsonSerializer.objectToJson(result));
    }

    @AfterThrowing(value = "@within(needLog) && @annotation(getMapping)", throwing = "throwable")
    public void afterErrorThrowing(JoinPoint joinPoint, GetMapping getMapping, NeedLog needLog, Throwable throwable) {
        loggerHelper.sendAfterMethodErrorToLog(joinPoint, throwable);
    }
}