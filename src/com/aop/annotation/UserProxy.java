package com.aop.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// 增强类
@Component
@Aspect // 生成代理对象
public class UserProxy {

    // 相同切入点抽取
    @Pointcut(value = "execution(* com.aop.annotation.User.add(..))")
    public void pointdemo() {

    }

    // 前置通知
    @Before(value = "pointdemo()")
    public void before() {
        System.out.println("before......");
    }

    // 最终通知
    @After(value = "execution(* com.aop.annotation.User.add(..))")
    public void after() {
        System.out.println("after......");
    }

    // 后置通知
    @AfterReturning(value = "execution(* com.aop.annotation.User.add(..))")
    public void afterReturning() {
        System.out.println("AfterReturning......");
    }

    // 异常通知
    @AfterThrowing(value = "execution(* com.aop.annotation.User.add(..))")
    public void afterThrowing() {
        System.out.println("afterThrowing......");
    }

    // 环绕通知
    @Around(value = "execution(* com.aop.annotation.User.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("around before......");

        proceedingJoinPoint.proceed();

        System.out.println("around after......");
    }
}
