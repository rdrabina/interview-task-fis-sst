package com.task.interview.sst.fis.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;


@Aspect
@Component
public class LoggerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("@annotation(com.task.interview.sst.fis.annotations.Logger)")
    private void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void logEnterMethod(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LOGGER.info(
                "Entering method: "
                        + method.getName()
                        + "() method with arguments: "
                        + Arrays.toString(joinPoint.getArgs())
        );
    }

    @AfterReturning(pointcut = "annotationPointcut()", returning = "result")
    public void logExitMethod(Object result) {
        LOGGER.info("Returning: " + result);
    }

    @AfterThrowing(pointcut = "annotationPointcut()", throwing = "exception")
    public void logException(Exception exception) {
        LOGGER.error("Exception was thrown: " + exception);
    }

}
