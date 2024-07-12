package com.example.boot.controller;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class TransactionMessageController {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/send/message")
    @Transactional(transactionManager = "rabbitTransactionManager",rollbackFor = {Exception.class})
    public void send() throws InterruptedException {
        String TRANSACTION_EXCHANGE_NAME="transaction.exchange";
        String TRANSACTION_ROUTING_KEY="transaction";
        rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE_NAME,TRANSACTION_ROUTING_KEY,"This is a transaction message-before!");
        int a= 1/0;
        rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE_NAME,TRANSACTION_ROUTING_KEY,"This is a transaction message-after!");
        Thread.sleep(100);
    }
}
