package com.example.cloud.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.cloud.mapper.AccountMapper;
import com.example.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Override
    public void decrease(Long userId, Long money) {
        log.info("seata-account-service开始扣减用户余额");
        accountMapper.decrease(userId, money);
        sleep();  // 利用OpenFeign默认处理请求超过1分钟会超时自动进行降级处理的原理，测试不用注解的情况下的分布式事务
        log.info("seata-account-service扣减用户余额结束");
    }

    private void sleep(){
        try{
            log.info("休眠开始时间:{}", DateUtil.now());
          TimeUnit.SECONDS.sleep(65);  // 单位是秒
//            TimeUnit.SECONDS.sleep(50);  // 单位是秒
            log.info("休眠结束时间:{}", DateUtil.now());
        }catch(Exception e){
            log.error("{}",e,e);
        }
    }
}
