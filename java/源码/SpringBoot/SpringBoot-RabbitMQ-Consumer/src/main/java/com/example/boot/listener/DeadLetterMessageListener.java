package com.example.boot.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class DeadLetterMessageListener {
    private final String DEAD_LETTER_EXCHANGE_NAME="deadLetter.exchange";
    private final String DEAD_LETTER_QUEUE_NAME="deadLetter.queue";
    private final String COMMON_QUEUE_NAME="common.queue";
    private final String COMMON_EXCHANGE_NAME="common.exchange";
    private final String DEAD_LETTER_ROUTING_KEY="deadLetter";
    private final String COMMON_ROUTING_KEY="common";

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(
                value = DEAD_LETTER_QUEUE_NAME,
                durable = "true"
            ),
            exchange = @Exchange(value = DEAD_LETTER_EXCHANGE_NAME),
            key = {DEAD_LETTER_ROUTING_KEY}
        )
    )
    public void deadMessageHandler(String data, Message message, Channel channel) throws IOException {
        log.info("死信消费方法接收到消息数据:{}",data);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(
                value = COMMON_QUEUE_NAME,
                durable = "true",
                arguments = {
                    @Argument(name = "x-message-ttl",value = "10000",type = "java.lang.Integer"),  // 设置消息过期时间
                    @Argument(name = "x-dead-letter-exchange",value = DEAD_LETTER_EXCHANGE_NAME),  // 设置关联的死信交换机
                    @Argument(name = "x-dead-letter-routing-key",value = DEAD_LETTER_ROUTING_KEY),  // 设置死信的路由键
                    @Argument(name = "x-max-length",value = "10",type = "java.lang.Integer")  // 设置本队列的容量
                }
            ),
            exchange = @Exchange(value = COMMON_EXCHANGE_NAME),
            key = {COMMON_ROUTING_KEY}
        )
    )
    public void defaultMessageHandler(String data, Message message, Channel channel) throws IOException {
        log.info("常规消费方法接收到消息数据:{}",data);
        log.info("本方法不想消费该数据，拒绝接收");
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
    }



}
