package com.harwex213.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(public * com.harwex213.controllers.api..*.*(..))")
    public void callAtApiController() {
    }

    @Pointcut("execution(public * com.harwex213.controllers.AdviceController.*(..))")
    public void callAtAdviceController() {
    }

    @Before("callAtApiController()")
    public void beforeCallMethod(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        log.info("Invoked: " + jp + ", args=[" + args + "]");
    }

    @Before("callAtAdviceController()")
    public void beforeCallAdviceControllerMethod(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        log.info("Resolved: " + jp + ", args=[" + args + "]");
    }

    @AfterReturning("callAtApiController()")
    public void afterReturningCallAt(JoinPoint jp) {
        log.info("Finished: " + jp.toString());
    }

    @AfterThrowing("callAtApiController()")
    public void afterThrowingCallAt(JoinPoint jp) {
        log.warn("Throwing: " + jp.toString());
    }
}
