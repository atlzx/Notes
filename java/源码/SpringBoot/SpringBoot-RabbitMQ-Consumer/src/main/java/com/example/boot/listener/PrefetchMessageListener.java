package com.example.boot.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PrefetchMessageListener {
    private final String PREFETCH_QUEUE_NAME="prefetch.queue";
    private final String PREFETCH_EXCHANGE_NAME="prefetch.exchange";

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(
                name = PREFETCH_QUEUE_NAME,
                durable = "true"
            ),
            exchange = @Exchange(value = PREFETCH_EXCHANGE_NAME),
            key = {"prefetch"}
        )
    )
    public void processMessageHandler(String data, Message message, Channel channel) throws InterruptedException, IOException {
        log.info("数据:{}",data);
        Thread.sleep(1000);  // 每处理一次消息就让进程待机1s
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
