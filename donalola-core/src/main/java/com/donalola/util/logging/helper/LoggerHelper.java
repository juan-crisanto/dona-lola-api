package com.donalola.util.logging.helper;


import org.aspectj.lang.JoinPoint;

public interface LoggerHelper {

    void initTracking();

    void initTracking(String trackingNumber);

    void sendBeforeMethodToLog(JoinPoint joinPoint);

    void sendBeforeMethodToLog(String methodName, String message);

    void sendAfterMethodToLog(JoinPoint joinPoint, String result);

    void sendAfterMethodToLog(String methodName, String result);

    void sendAfterMethodErrorToLog(JoinPoint joinPoint, Throwable throwable);

    void sendAfterMethodErrorToLog(String methodName, String error);

    void setTrackingId(String trackingId);
}