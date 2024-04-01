package com.spring.test;

import com.spring.sample.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("user.xml");
        User user1 = (User)context.getBean("user");
        System.out.println("通过id得到user"+user1);
        User user2=context.getBean("user",User.class);
        System.out.println("通过id和Class对象得到user"+user2);
        User user3=context.getBean(User.class);
        System.out.println("通过Class对象得到user"+user3);
    }
}
