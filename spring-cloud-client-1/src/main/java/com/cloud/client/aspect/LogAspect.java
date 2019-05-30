package com.cloud.client.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@Aspect
public class LogAspect {
    
    //.*.*代表所有子目录下的所有方法，最后括号里(..)的两个..代表所有参数
    @Pointcut("execution(public * com.cloud.client.controller.*.*(..))")
    void logPointCut() {
        //
    }
    
    @Before("logPointCut()")
    ////    @Throws(Throwable::class)
    void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        
        // 记录下请求内容
        System.out.println("请求地址 : " + attributes.getAttribute("requestURL", 0));
        System.out.println("HTTP METHOD : " + attributes.getAttribute("method", 0));
        System.out.println("IP : " + attributes.getAttribute("remoteAddr", 0));
        System.out.println("CLASS_METHOD : " + attributes.getAttribute("requestURL", 0));
        System.out.println("请求地址 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("参数 : " + Arrays.toString(joinPoint.getArgs()));
        
    }
    
    @AfterReturning(returning = "ret", pointcut = "logPointCut()") // returning的值和doAfterReturning的参数名一致
    //        @Throws(Throwable::class)
    
    void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        System.out.println("返回值 :" + ret);
    }
    
    @Around("logPointCut()")
    Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Long startTime = System.currentTimeMillis();
        // ob 为方法的返回值
        Object ob = pjp.proceed();
        System.out.println("耗时 : " + (System.currentTimeMillis() - startTime));
        return ob;
    }
    
}
