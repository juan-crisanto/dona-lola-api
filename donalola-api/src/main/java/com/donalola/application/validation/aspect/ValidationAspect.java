package com.donalola.application.validation.aspect;

import com.donalola.application.PreventCorruption;
import com.donalola.application.ValidableParam;
import com.donalola.application.validation.ValidationManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ValidationAspect {

    private final ValidationManager validationManager;

    public ValidationAspect(ValidationManager validationManager) {
        System.out.println("Initializing aspect....");
        this.validationManager = validationManager;
    }

    @Pointcut("@within(preventionCorruptionType)")
    private void withinPreventionCorruptionType(PreventCorruption preventionCorruptionType) {
    }

    @Pointcut("execution(* *.*(@com.donalola.application.ValidableParam (*), ..))")
    private void withValidableParam() {
    }

    @Around(value = "(withinPreventionCorruptionType(preventionCorruptionType) && withValidableParam())")
    public Object validate(ProceedingJoinPoint proceedingJoinPoint, PreventCorruption preventionCorruptionType) throws Throwable {
        List<Object> validableParams = getValidableParams(proceedingJoinPoint);
        validateParams(validableParams);
        return proceedingJoinPoint.proceed();
    }

    private void validateParams(List<Object> validableParams) {
        if (CollectionUtils.isEmpty(validableParams)) {
            return;
        }
        for (Object param : validableParams) {
            this.validationManager.validateJsonRequest(param);
        }
    }

    private List<Object> getValidableParams(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] args = proceedingJoinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        List<Object> validableParams = new ArrayList<>(ArrayUtils.getLength(args));

        for (int argIndex = 0; argIndex < args.length; argIndex++) {
            for (Annotation annotation : parameterAnnotations[argIndex]) {
                if (!(annotation instanceof ValidableParam)) {
                    continue;
                }
                validableParams.add(args[argIndex]);
            }
        }
        return validableParams;
    }
}
