package com.spring.sample;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect  // Aspect注解声明这个类是一个切面类
@Component  // 标识该类为bean
public class AOPAnnoSample {
    /*
        切片共可以有五种通知
            前置通知:使用@Before注解表示
            返回通知:使用@AfterReturning注解表示
            异常通知:使用@AfterThrowing注解表示
            后置通知:使用@After注解表示
            环绕通知:使用@Around注解表示
    */

    // 注解中应包含规定格式的作用对象，下面的意思是该前置通知作用于任意修饰符和返回值类型的位于com.spring.sample.ProxyInterfaceImpl全类名下的含任意形参列表的方法
    // .. 用来表示形参任意，*在不同的位置下有不同的作用
    // @Before、@Around和@After可以指定切面的方法的参数名，并在切面方法上直接接收到（类似下面的@AfterRetrning）
    @Before(value="execution(* com.spring.sample.ProxyInterfaceImpl.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        System.out.println("前置通知执行");
    }
    // 对于AfterReturning注解，可以指定returning属性来使返回通知方法得到对应名称的返回值，二者的名字必须相同
    @AfterReturning(value = "execution(* com.spring.sample.ProxyInterfaceImpl.*(..))",returning = "aa")
    public void afterReturningMethod(JoinPoint joinPoint,Object aa){
        System.out.println("返回通知执行,返回值是:"+aa);
    }

    // 对于AfterThrowing注解，可以指定throwing属性来使异常通知方法得到对应名称的异常对象，二者的名字必须相同
    @AfterThrowing(value = "execution(* com.spring.sample.ProxyInterfaceImpl.*(..))",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Throwable e){
        System.out.println("异常通知执行,异常为:"+e);
    }

    @After(value = "execution(* com.spring.sample.ProxyInterfaceImpl.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        System.out.println("后置通知执行");
    }
    // 环绕通知需要将代码写在try-catch-finally结构内，方法必须有返回值，且参数需要是ProceedingJoinPoint(JoinPoint子类)
    // 可以使用PointCut注解声明的方法来实现切入点表达式的复用,value对应的值是被PointCut注解声明的方法的调用式
    @Around(value = "pointCut()")
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

    // 可以在一个方法上加上PointCut注解，在注解内实现切入点表达式，这样做，其它通知相关的注解就可以直接调用本类的方法来实现切入点表达式的复用了
    @Pointcut(value = "execution(* com.spring.sample.ProxyInterfaceImpl.*(..))")
    public void pointCut(){

    }

}
