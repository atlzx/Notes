package com.example.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class RMQProducerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(RMQProducerApplication.class);
        ConnectionFactory bean = ioc.getBean(ConnectionFactory.class);
        log.info("{}",bean);

    }
}
