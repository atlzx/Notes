package com.spring.sample.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

// 单纯的实现该接口没什么用，该接口内的两个方法是默认方法，我们需要自己在类内部写上这两个方法
public class PostProcessorImpl implements BeanPostProcessor {
    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第三步:初始化前的后置处理器执行");
        return bean;
    }

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第五步:初始化后的后置处理器执行");
        return bean;
    }
}
