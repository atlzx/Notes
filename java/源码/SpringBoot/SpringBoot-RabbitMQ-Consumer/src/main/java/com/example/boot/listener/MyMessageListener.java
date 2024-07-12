package com.example.boot.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MyMessageListener {
    private final String EXCHANGE_DIRECT = "exchange.direct.order";
    private final String ACK_CONSUMER_EXCHANGE="ack.consumer.exchange";
    private final String BACKUP_EXCHANGE_NAME="exchange.backup";
    private final String ALTERNATE_EXCHANGE="alternate-exchange";
    private  final String ROUTING_KEY = "order";
    private final String ACK_CONSUMER_ROUTING_KEY="ack-consumer";
    private  final String QUEUE_NAME  = "queue.order";
    private final String ACK_MESSAGE_QUEUE_NAME="ack_consumer_queue";
    // @RabbitListener注解用于使指定方法能够消费消息队列中的消息
    // 写法一:详细写法，如果不存在对应的交换机、队列和路由映射会自动创建
    @RabbitListener(
        bindings = @QueueBinding(
            // value属性用于绑定队列，如果指定的队列不存在，那么会自动创建
            value = @Queue(
                value = QUEUE_NAME,  // 要绑定的队列的名称
                durable = "true"  // 是否将数据持久化处理
            ),
            exchange = @Exchange(
                value = EXCHANGE_DIRECT,
                arguments = @Argument(
                    name=ALTERNATE_EXCHANGE,
                    value = BACKUP_EXCHANGE_NAME
                )
            ),  // 绑定交换机，不存在会自动创建
            key = {ROUTING_KEY}  // 设置路由键，会自动建立交换机与队列之间的路由映射关系
        )
    )
    // 写法二:简单写法，方法只会监听对应队列的消息并进行消费
//    @RabbitListener(queues = {QUEUE_NAME})  // queues属性用来配置方法要绑定的队列名，是一个数组
    public void processMessage(String data, Message message, Channel channel){
        log.info("{}", "消费端接收消息:" + data);
    }

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(
                value = ACK_MESSAGE_QUEUE_NAME,
                durable = "true"
            ),
            exchange = @Exchange(value = ACK_CONSUMER_EXCHANGE),
            key = {ACK_CONSUMER_ROUTING_KEY}
        )
    )
    public void ackMessageTest(String data, Message message, Channel channel) throws IOException {
        //  1、获取当前消息的 deliveryTag 值备用
        long deliveryTag = message.getMessageProperties().getDeliveryTag();


        try {
            // 2、正常业务操作
            log.info("消费端接收到消息内容：" + data);

             System.out.println(10 / 0);

            // 3、给 RabbitMQ 服务器返回 ACK 确认信息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {

            // 4、获取信息，看当前消息是否曾经被投递过
            Boolean redelivered = message.getMessageProperties().getRedelivered();

            if (!redelivered) {
                // 5、如果没有被投递过，那就重新放回队列，重新投递，再试一次
                channel.basicNack(deliveryTag, false, true);
            } else {
                // 6、如果已经被投递过，且这一次仍然进入了 catch 块，那么返回拒绝且不再放回队列
                channel.basicReject(deliveryTag, false);
            }

        }
    }



}
