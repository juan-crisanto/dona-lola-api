package com.donalola.util.logging.helper;

import com.donalola.util.logging.LoggingConstants;
import com.donalola.util.serializer.JsonSerializer;
import com.donalola.util.tracking.TrackingIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoggerHelperImpl implements LoggerHelper {

    private final JsonSerializer jsonSerializer;

    public LoggerHelperImpl(JsonSerializer jsonSerializer) {
        this.jsonSerializer = jsonSerializer;
    }

    @Override
    public void initTracking() {
        initTracking(TrackingIdGenerator.getInstance().generateLongId());
    }

    @Override
    public void initTracking(String trackingNumber) {
        String t = MDC.get(LoggingConstants.REQUEST_TRACKING_ID);
        if (StringUtils.isEmpty(t)) {
            MDC.put(LoggingConstants.REQUEST_TRACKING_ID, trackingNumber);
        }
    }

    @Override
    public void sendBeforeMethodToLog(JoinPoint joinPoint) {
        clearMdcData();

        String signature = joinPoint.getSignature().toString();

        if (joinPoint.getArgs().length > 0) {
            processArgs(joinPoint.getArgs());
        }

        MDC.put(LoggingConstants.EVENT_SIGNATURE, signature);

        Logger log = this.getLogger(joinPoint.getTarget().getClass());
        log.info("Before Method");

        clearMdcData();
    }

    @Override
    public void sendBeforeMethodToLog(String methodName, String message) {
        clearMdcData();

        MDC.put(LoggingConstants.EVENT_SIGNATURE, methodName);
        MDC.put(LoggingConstants.EVENT_INPUT, message);

        Logger log = this.getLogger();
        log.info("Before Method");

        clearMdcData();
    }

    private void processArgs(Object[] allArgs) {
        List<Object> args = new ArrayList<>(allArgs.length);

        for (Object o : allArgs) {
            try {
                if (o.getClass().getName().contains("spring")) {
                    continue;
                }
                jsonSerializer.objectToJson(o);
                args.add(o);
            } finally {
                continue;
            }
        }

        MDC.put(LoggingConstants.EVENT_INPUT, jsonSerializer.objectToJson(args));
    }

    @Override
    public void sendAfterMethodToLog(JoinPoint joinPoint, String result) {
        clearMdcData();
        MDC.put(LoggingConstants.EVENT_OUTPUT, result);

        Logger log = this.getLogger(joinPoint.getTarget().getClass());
        log.info("After Method");
        clearMdcData();
    }

    @Override
    public void sendAfterMethodToLog(String methodName, String result) {
        clearMdcData();

        MDC.put(LoggingConstants.EVENT_SIGNATURE, methodName);
        MDC.put(LoggingConstants.EVENT_OUTPUT, result);

        Logger log = this.getLogger();
        log.info("After Method");
        clearMdcData();
    }

    @Override
    public void sendAfterMethodErrorToLog(JoinPoint joinPoint, Throwable throwable) {
        clearMdcData();
        Logger log = this.getLogger(joinPoint.getTarget().getClass());
        log.error("After Method Error", throwable);
        clearMdcData();
    }

    @Override
    public void sendAfterMethodErrorToLog(String methodName, String error) {
        clearMdcData();

        MDC.put(LoggingConstants.EVENT_SIGNATURE, methodName);
        MDC.put(LoggingConstants.EVENT_OUTPUT, error);

        Logger log = this.getLogger();
        log.error("After Method Error");

        clearMdcData();
    }

    @Override
    public void setTrackingId(String trackingId) {
        MDC.put(LoggingConstants.REQUEST_TRACKING_ID, trackingId);
    }

    private Logger getLogger() {
        return getLogger(this.getClass());
    }

    private Logger getLogger(Class loggerClass) {
        Logger log = LoggerFactory.getLogger(loggerClass);
        if (MDC.get("uniqueCode") == null) {
            setInfoTrace();
        }
        return log;
    }

    private void setInfoTrace() {
//        Optional<Authentication> optionalAuthentication = BpiSecurityUtil.getOptionalAuthentication();
//
//        if ( optionalAuthentication.isPresent() ) {
//            Object obj = optionalAuthentication.get().getPrincipal();
//            String fullName = StringUtil.EMPTY;
//            String uniqueCode = StringUtil.EMPTY;
//            String cardNumber = StringUtil.EMPTY;
//
//            if ( obj instanceof JweCustomer ) {
//                JweCustomer jweCustomer = (JweCustomer) obj;
//                fullName = jweCustomer.getFullName();
//                uniqueCode = jweCustomer.getUniqueCode();
//                cardNumber = jweCustomer.getCardNumber();
//            }
//            if ( obj instanceof BpiCustomer ) {
//                BpiCustomer bpiCustomer = (BpiCustomer) obj;
//                fullName = bpiCustomer.getFullName();
//                uniqueCode = bpiCustomer.getUniqueCode();
//                cardNumber = bpiCustomer.getCardNumber();
//            }
//
//            MDC.put("name", fullName);
//            MDC.put("uniqueCode", uniqueCode);
//            MDC.put("cardNumber", cardNumber);
//        }
//        else {
//            MDC.put("uniqueCode", "UNLOGGED USER");
//        }
    }

    private void clearMdcData() {
        MDC.remove(LoggingConstants.EVENT_SIGNATURE);
        MDC.remove(LoggingConstants.EVENT_INPUT);
        MDC.remove(LoggingConstants.EVENT_OUTPUT);
    }
}