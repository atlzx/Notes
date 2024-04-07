package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


//  该注解能让junit在测试时直接就能找到Spring的对应bean对象，而不需要再创建IoC容器对象获取了
@SpringJUnitConfig(classes = Config.class)
@Component
public class JunitTest {
    @Value("12")
    private int age;

    @Autowired
    private User user;

    @Test
    public void printTest(){
        System.out.println(age);
        System.out.println(user);
    }
}
