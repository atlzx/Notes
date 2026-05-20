package com.springboot.example.mybatisplus;

import com.springboot.example.mybatisplus.service.MasterUserService;
import com.springboot.example.mybatisplus.service.SlaveUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DynamicDatasourceTest {
    @Resource
    private MasterUserService masterUserService;
    @Resource
    private SlaveUserService slaveUserService;
    @Test
    public void test1(){
        long count = masterUserService.count();
        long count1 = slaveUserService.count();
        log.info("{},{}",count,count1);
    }
}
