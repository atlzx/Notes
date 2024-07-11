package com.example.boot.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyMessageListener {
    public static final String EXCHANGE_DIRECT = "exchange.direct.order";
    public static final String ROUTING_KEY = "order";
    public static final String QUEUE_NAME  = "queue.order";
    // @RabbitListener注解用于使指定方法能够消费消息队列中的消息
    // 写法一:详细写法，如果不存在对应的交换机、队列和路由映射会自动创建
    @RabbitListener(
        // bindings属性用来配置方法能够消费哪些队列中的消息，它接收QueueBinding注解数组对象
        bindings = @QueueBinding(
            // value属性用于绑定队列，如果指定的队列不存在，那么会自动创建
            value = @Queue(
                value = QUEUE_NAME,  // 要绑定的队列的名称
                durable = "true"  // 是否将数据持久化处理
            ),
            exchange = @Exchange(value = EXCHANGE_DIRECT),  // 绑定交换机，不存在会自动创建
            key = {ROUTING_KEY}  // 设置路由键，会自动建立交换机与队列之间的路由映射关系
        )
    )
    // 写法二:简单写法，方法只会监听对应队列的消息并进行消费
    @RabbitListener(queues = {QUEUE_NAME})  // queues属性用来配置方法要绑定的队列名，是一个数组
    public void processMessage(String data, Message message, Channel channel){
        log.info("{}", "消费端接收消息:" + data);
    }

}
