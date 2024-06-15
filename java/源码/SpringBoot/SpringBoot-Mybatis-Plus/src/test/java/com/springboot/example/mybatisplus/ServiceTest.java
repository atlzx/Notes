package com.springboot.example.mybatisplus;

import com.springboot.example.mybatisplus.entity.User;
import com.springboot.example.mybatisplus.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class ServiceTest {
    @Resource
    private UserServiceImpl userService;
    @Test
    public void countTest(){
        // count方法用来统计表中的行数量
        long count = userService.count();
        log.info(String.valueOf(count));
    }

    @Test
    public void batchInsertTest(){
        List<User> list=new ArrayList<>();
        for(int i=1;i<=10;i++){
            User user=new User();
            user.setEmail("lzx"+i+"@qq.com");
            user.setAge(18+i);
            user.setName("lzx"+i);
            list.add(user);
        }
        boolean isSuccess = userService.saveBatch(list);
        log.info(String.valueOf(isSuccess));

    }
}
