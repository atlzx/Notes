package com.spring.sample;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Component
public class AOPXmlSample {

    public void beforeMethod(JoinPoint joinPoint){
        System.out.println("前置通知执行");
    }


    public void afterReturningMethod(JoinPoint joinPoint,Object aa){
        System.out.println("返回通知执行,返回值是:"+aa);
    }



    public void afterThrowing(JoinPoint joinPoint,Throwable e){
        System.out.println("异常通知执行,异常为:"+e);
    }


    public void afterMethod(JoinPoint joinPoint){
        System.out.println("后置通知执行");
    }

    public Object aroundMethod(ProceedingJoinPoint joinPoint){
        try{
            System.out.println("环绕通知——前置");  // 方法执行的上面用来写前置通知的代码
            Object res = joinPoint.proceed();  // 调用proceed方法来使方法执行
            System.out.println("环绕通知——返回");  // 方法执行的下面用来写返回通知的代码
            return res;
        }catch(Throwable e){
            // catch语句用来写异常通知的代码
            System.out.println("环绕通知——异常"+e);
            return "exception";
        }finally{
            // finally语句内用来写后置通知的代码
            System.out.println("环绕通知——后置");
        }
    }
}
