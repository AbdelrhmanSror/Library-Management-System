package com.example.librarymanagementsystem.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class BorrowingRecordLoggingAspect {


    @Before("execution(* com.example.librarymanagementsystem.services.BorrowingRecordService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Method execution started: {}", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "execution(* com.example.librarymanagementsystem.services.BorrowingRecordService.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method execution completed: {}", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "execution(* com.example.librarymanagementsystem.services.BorrowingRecordService.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception thrown in method {}: {}", joinPoint.getSignature(), exception.getMessage());
    }
}
