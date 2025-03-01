package com.springboot.example.springboottest.bean;

import com.springboot.example.springboottest.BeanUtil;
import com.springboot.example.springboottest.service.TestService;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Proxy;
import java.util.Map;

public class BeanTest implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition(
            "testService",
            BeanDefinitionBuilder.genericBeanDefinition(
                TestService.class,
                () -> {
                    return (TestService) Proxy.newProxyInstance(
                        Thread.currentThread().getContextClassLoader(),
                        new Class[]{TestService.class},
                        (proxy, method, args) -> {
                            Class<?> serivceClass = method.getDeclaringClass();
                            Map<String,TestService> beanMap = BeanUtil.getBeans(TestService.class);
                            ApplicationContext ioc = BeanUtil.getIoc();
                            String[] beanNamesForType = ioc.getBeanNamesForType(serivceClass);
                            System.out.println(beanMap.get("aaa"));
                            return null;
                        }
                    );
                }
            ).getBeanDefinition()
        );
    }
}
