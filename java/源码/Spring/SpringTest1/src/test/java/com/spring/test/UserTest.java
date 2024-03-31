package com.spring.test;

import com.spring.sample.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
    private Logger logger = LoggerFactory.getLogger(User.class);

    @Test
    public void test1() {
        // 创建一个ApplicationContext对象，传入的参数是对应xml文件的名称
        ApplicationContext context = new ClassPathXmlApplicationContext("user.xml");
        // 使用getBean得到对应xml文件配置的指定类对象，即传入xml文件内标签的id属性.
        // 该方法返回的是Object类型对象，因此强转成User类型对象
        User user = (User) context.getBean("user");
        System.out.println("输出得到的user:" + user);  // 输出，检验是否为该类对象
        user.sayHello();  // 尝试调用该类对象内声明的方法
    }

    @Test
    public void test2() {
        logger.debug("调试");
        logger.info("输出信息");
        logger.error("出现错误");
        logger.trace("跟踪");
        logger.warn("警告");
    }
}
