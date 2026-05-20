package com.springboot.example.springbootconfiguration.test;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JunitTest {
    @Test
    @DisplayName("\uD83D\uDE31")
    public void test1(){
        Assertions.assertAll();
        System.out.println("测试方法1");
    }

    @Test
    @DisplayName("测试方法2")
    public void test2(){
        System.out.println("测试方法2");
    }

    @Test
    @DisplayName("Timeout注解测试")
    @Timeout(value = 1)  // 单位是秒
    public void test3(){
        try{
            Thread.sleep(1200);
        }catch (Exception e){

        }

    }

    @BeforeAll
    public static void prevAllTest(){
        System.out.println("在所有方法执行前执行");
    }

    @AfterAll
    public static void afterAllTest(){
        System.out.println("在所有方法执行后执行");
    }

    @BeforeEach
    public void prevEachTest(){
        System.out.println("在每个测试方法执行前执行");
    }

    @AfterEach
    public void afterEachTest(){
        System.out.println("在每个测试方法执行后执行");
    }
}
