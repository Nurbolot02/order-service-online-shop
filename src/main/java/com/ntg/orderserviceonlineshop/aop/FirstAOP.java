package com.ntg.orderserviceonlineshop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FirstAOP {
    @Pointcut(value = "@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {

    }

    @Pointcut("within(com.ntg.orderserviceonlineshop.service.*Service)")
    public void isServiceLayer() {
    }
    @Before("isServiceLayer() && args(id)")
    public void loggingServiceLayer(JoinPoint joinPoint, Object id) {
        System.out.println("Called Order service");
    }

    @Pointcut("this(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer() {
    }

    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {

    }

//    @Pointcut("")
//    public void hasOrderRequestParam() {}


    @Before("hasGetMapping() && args(id)")
    public void addLogging(JoinPoint joinPoint, Object id) {
        System.out.println("Called Get mapping in Order service");
        log.error("Called Get mapping in Order service");
    }
}
