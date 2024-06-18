package com.springboot.example.mybatisplus;

import com.springboot.example.mybatisplus.entity.User;
import com.springboot.example.mybatisplus.enums.SexEnum;
import com.springboot.example.mybatisplus.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnumTest {
    @Resource
    private UserMapper userMapper;
    @Test
    public void test1(){
        User user=new User();
        user.setName("lzx");
        user.setAge(18);
        user.setSex(SexEnum.MALE);
        userMapper.insert(user);
    }
}
