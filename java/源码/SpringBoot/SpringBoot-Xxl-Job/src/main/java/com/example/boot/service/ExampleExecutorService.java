package com.example.boot.service;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExampleExecutorService {

    // 使用XxlJob注解即可将该方法标记为待执行的作业，在注解内写入的字符串作为调用该作业的唯一标识存在
    @XxlJob("testExecutor")
    public void testExecutor(){
        for (int i = 0; i < 5; i++) {
            log.info("循环的值:{}",i);
        }
    }
}
