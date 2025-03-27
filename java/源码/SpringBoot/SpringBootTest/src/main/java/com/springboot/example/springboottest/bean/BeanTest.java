package com.springboot.example.springboottest.bean;

import com.springboot.example.springboottest.BeanUtil;
import com.springboot.example.springboottest.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class BeanTest implements ImportBeanDefinitionRegistrar {
    private final List<String> objectMethodNames= Arrays.asList(
        "toString","getClass","wait","hashCode","notify","notifyAll","clone","equals"
    );

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
                            ApplicationContext ioc = BeanUtil.getIoc();
                            Map<String, TestService> beansOfType = ioc.getBeansOfType(TestService.class);
                            System.out.println(beansOfType.size());
                            for(Map.Entry<String, TestService> entry : beansOfType.entrySet()) {
                                log.info(entry.getValue().getClass().toString());
                                if(!entry.getValue().getClass().getSimpleName().contains("$")) {
                                    log.info(entry.getValue().getClass().getSimpleName());
                                }
                            }
                            return null;
                        }
                    );
                }
            ).getBeanDefinition()
        );
    }
}
