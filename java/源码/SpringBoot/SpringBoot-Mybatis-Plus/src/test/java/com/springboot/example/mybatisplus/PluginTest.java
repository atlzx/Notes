package com.springboot.example.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.mybatisplus.entity.User;
import com.springboot.example.mybatisplus.mapper.UserMapper;
import com.springboot.example.mybatisplus.utils.SpringUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PluginTest {
    @Resource
    private UserMapper userMapper;
    @Test
    public void pagePluginTest(){
        Page<User> userPage = userMapper.selectPage(new Page<>(2, 3), null);
        log.info(userPage.getRecords().toString());

    }

    @Test
    public void test1(){
        MybatisPlusInterceptor bean = SpringUtil.getBean(MybatisPlusInterceptor.class);
        log.info(bean.toString());
    }

}
