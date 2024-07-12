package com.example.boot.listener;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PriorityMessageListener {
    private final String PRIORITY_EXCHANGE_NAME="priority.exchange";
    private final String PRIORITY_ROUTING_KEY="priority";
    private final String PRIORITY_QUEUE_NAME="priority.queue";
    private final String PRIORITY_ARGUMENT="x-max-priority";
    private final String INTEGER_CLASS_PATH="java.lang.Integer";
    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(
                value = PRIORITY_QUEUE_NAME,
                arguments = @Argument(
                    name = PRIORITY_ARGUMENT,
                    value = "10",
                    type = INTEGER_CLASS_PATH
                )
            ),
            exchange = @Exchange(value = PRIORITY_EXCHANGE_NAME),
            key = PRIORITY_ROUTING_KEY
        )
    )
    public void processMessageHandler(String data, Message message, Channel channel) throws IOException {
        log.info("消费优先级消息:{}",data);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
