package com.springboot.example.springbootconfiguration.test;

import org.junit.jupiter.api.*;

import java.util.EmptyStackException;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NestedTest")
public class NestedTest {

    @BeforeAll
    public static void beforeAllMethod(){
        System.out.println("在所有测试方法执行前执行");
    }

    @AfterAll
    public static void afterAllMethod(){
        System.out.println("在所有测试方法执行后执行");
    }

    @Test
    public void test1(){
        System.out.println("测试1");
    }

    @BeforeEach
    public void beforeEachMethod(){
        System.out.println("每个测试方法前执行");
    }

    @AfterEach
    public void afterEachMethod(){
        System.out.println("每个测试方法后执行");
    }

    @Nested
    class NestedClass{
        @BeforeEach
        public void beforeEachMethod(){
            System.out.println("内嵌——每个测试方法前执行");
        }

        @AfterEach
        public void afterEachMethod(){
            System.out.println("内嵌——每个测试方法后执行");
        }

        @Test
        public void test1(){
            System.out.println("测试1");
        }

        @BeforeAll
        public static void beforeAllMethod(){
            System.out.println("内嵌——在所有测试方法执行前执行");
        }

        @AfterAll
        public static void afterAllMethod(){
            System.out.println("内嵌——在所有测试方法执行后执行");
        }
    }


}
