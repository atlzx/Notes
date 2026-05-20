package com.example.boot.publish_subscribe;

import com.example.boot.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        String queue2Name = "test_fanout_queue2";

        channel.queueDeclare(queue2Name,true,false,false,null);

        Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("body："+new String(body));
                System.out.println("队列 2 消费者 2 将日志信息打印到控制台.....");

            }

        };

        channel.basicConsume(queue2Name,true,consumer);

    }

}
