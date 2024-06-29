package com.example.cloud.config;

import feign.Logger;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FeignConfig {
    @Bean
    public Retryer retryer(){
        return Retryer.NEVER_RETRY;  // Feign默认的retry策略是不重试
        // 第一个参数是设置默认的重新请求的间隔，即上一次请求失败到下一次重新请求开始的间隔时间，单位毫秒
        // 第二个参数设置最大的重新请求的间隔
        // 第三个参数设置最大的请求次数，该次数是算上最开始那一次请求的，即如果设置成3，那么最多只会重新请求2次，因为最开始那一次请求要占用一次
//        return new Retryer.Default(100,1,3);
    }
    @Bean
    public Logger.Level loggerLevel(){
        return Logger.Level.FULL;
    }
}
