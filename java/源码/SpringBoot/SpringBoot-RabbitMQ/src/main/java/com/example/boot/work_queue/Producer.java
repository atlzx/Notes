package com.example.boot.work_queue;

import com.example.boot.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {

    public static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        for (int i = 1; i <= 10; i++) {

            String body = i+"hello rabbitmq~~~";

            channel.basicPublish("",QUEUE_NAME,null,body.getBytes());

        }

        channel.close();

        connection.close();

    }

}
