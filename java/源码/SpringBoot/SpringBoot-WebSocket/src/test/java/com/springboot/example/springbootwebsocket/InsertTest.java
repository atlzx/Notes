package com.springboot.example.springbootwebsocket;

import com.springboot.example.springbootwebsocket.pojo.User;
import com.springboot.example.springbootwebsocket.mapper.UserDao;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InsertTest {

    @Resource
    private UserDao userDao;
}
