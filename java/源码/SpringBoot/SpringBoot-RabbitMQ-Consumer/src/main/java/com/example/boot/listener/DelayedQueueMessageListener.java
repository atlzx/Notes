package com.example.boot.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class DelayedQueueMessageListener {
    @RabbitListener(queues = {"delayed.plugin.queue"})
    public void processMessageHandler(String data, Message message, Channel channel){
        log.info("收到消息:{}，时间:{}",data,new Date());
    }
}
