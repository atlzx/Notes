package com.springboot.example.eventandlistener.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

@Slf4j
public class MyListener implements SpringApplicationRunListener {
    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        // 这里的log还没有加载，需要用System.out.println打印
        System.out.println("--------------------------项目开始启动----------------------------------------");
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        log.info("\n--------------------------环境变量准备完成----------------------------------------");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("\n--------------------------IOC容器已经准备完成----------------------------------------");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("\n--------------------------IOC容器已经加载完毕----------------------------------------");
    }

    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        log.info("\n--------------------------项目已经启动----------------------------------------");
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        log.info("\n--------------------------项目准备就绪----------------------------------------");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("\n--------------------------项目启动失败----------------------------------------");
    }
}
