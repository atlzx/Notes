package com.example.boot;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RMQTest {

    public static final String EXCHANGE_DIRECT = "exchange.direct.order";
    public static final String ROUTING_KEY = "order";

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    public void test1(){
        rabbitTemplate.convertAndSend(EXCHANGE_DIRECT,ROUTING_KEY,"Hello RabbitMQ!");
    }
}
