package com.example.boot;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
public class RMQTest {
    private final String EXCHANGE_NAME="exchange.direct.order";
    private final String ACK_CONSUMER_EXCHANGE_NAME="ack.consumer.exchange";
    private final String ACK_CONSUMER_ROUTING_KEY="ack-consumer";
    private final String PREFETCH_ROUTING_KEY="prefetch";
    private final String PREFETCH_EXCHANGE_NAME="prefetch.exchange";
    private final String TIMEOUT_EXCHANGE_NAME="timeout.exchange";

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    public void test1() throws InterruptedException {
        // 正常发送消息的情况
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY,"Hello RabbitMQ!");
        // 交换机名字错误的情况
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME+"~",ROUTING_KEY,"Hello RabbitMQ!");
        // 路由键不匹配的情况
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"~","Hello RabbitMQ!");
        Thread.sleep(100);
    }

    @Test  // ack-consumer测试
    public void test2() throws InterruptedException {
        rabbitTemplate.convertAndSend(ACK_CONSUMER_EXCHANGE_NAME,ACK_CONSUMER_ROUTING_KEY,"This is a ack consumer message!");
        Thread.sleep(100);
    }

    @Test  // 测试消息限流
    public void test3() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend(PREFETCH_EXCHANGE_NAME,PREFETCH_ROUTING_KEY,"This is a common message!");
            Thread.sleep(100);  // 避免出现异常
        }
    }

    @Test
    public void test4() throws InterruptedException {
//        rabbitTemplate.convertAndSend(TIMEOUT_EXCHANGE_NAME,"timeout","This is a timeout message!");


        // -------------------------- 从消息层面进行超时限制 -------------------------------------

        // 需要一个MessagePostProcessor接口对象，它是一个函数式接口，回调函数接收一个Message类型的参数，可以对该Message对象进行一些操作
        MessagePostProcessor messagePostProcessor=(message)->{
            message.getMessageProperties().setExpiration("10000"); // 设置过期时间为10s后
            return message;
        };
        // 在发送时将messagePostProcessor作为第四个参数传入，以在Message对象生成后执行
        rabbitTemplate.convertAndSend(PREFETCH_EXCHANGE_NAME,PREFETCH_ROUTING_KEY,"This is a timeout message!",messagePostProcessor);
        Thread.sleep(100);  // 避免出现异常
    }

    @Test  // 测试死信出现的第一种情况，即消费者主动拒绝消费并阻止消息重新加入队列
    public void test5() throws InterruptedException {
        String COMMON_EXCHANGE_NAME="common.exchange";
        String COMMON_ROUTING_KEY="common";
        rabbitTemplate.convertAndSend(COMMON_EXCHANGE_NAME,COMMON_ROUTING_KEY,"This is a common message");
        Thread.sleep(100);
    }

    @Test  // 测试死信出现的后两种情况，超时和消息溢出
    public void test6() throws InterruptedException {

        String COMMON_EXCHANGE_NAME="common.exchange";
        String COMMON_ROUTING_KEY="common";
        // 默认设置的最大容量是10，同时设置了队列的消息保存时间是10s，循环20次，前10次的消息都被后10次的消息顶掉了，后10次的消息由于超时最后也会加入死信队列
        // 最终20条消息全部加入死信队列
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend(COMMON_EXCHANGE_NAME,COMMON_ROUTING_KEY,"This is a common message");
        }
        Thread.sleep(100);  // 避免出现异常
    }


    @Test  // 测试延迟队列插件
    public void test7() throws InterruptedException {
        String DELAYED_EXCHANGE_NAME="delayed.plugin.exchange";
        String DELAYED_ROUTING_KEY="delayed";
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME,DELAYED_ROUTING_KEY,"This is a delayed message,the time is"+new Date(),
            (message)->{
                message.getMessageProperties().setHeader("x-delay",10000);
                return message;
            }
        );
        Thread.sleep(100);
    }

    // 本方法用于测试事务消息，但是执行失败了，不知道为什么
    @Test
    @Transactional(transactionManager = "rabbitTransactionManager",rollbackFor = {Exception.class})  //  开启事务
//    @Rollback(value = false)  // junit的单元测试测试事务默认是进行回滚的，这里设置为false
    public void test8() throws InterruptedException {

        String TRANSACTION_EXCHANGE_NAME="transaction.exchange";
        String TRANSACTION_ROUTING_KEY="transaction";
        rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE_NAME,TRANSACTION_ROUTING_KEY,"This is a transaction message-before!");
        int a= 1/0;
        rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE_NAME,TRANSACTION_ROUTING_KEY,"This is a transaction message-after!");
        Thread.sleep(100);
    }


    @Test
    public void test9() throws InterruptedException {
        String PRIORITY_EXCHANGE_NAME="priority.exchange";
        String PRIORITY_ROUTING_KEY="priority";
        for(int i=0;i<20;i++){
            int a=i;
            rabbitTemplate.convertAndSend(PRIORITY_EXCHANGE_NAME,PRIORITY_ROUTING_KEY,"This is a priority,the priority is "+a,
                (message)->{
                    message.getMessageProperties().setPriority(a);
                    return message;
                }
            );
        }

        Thread.sleep(100);
    }
}
