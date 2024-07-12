package com.example.boot.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    @Resource
    private RabbitTemplate rabbitTemplate;


    // 为了使配置生效，需要在rabbitTemplate对象中设置两个属性的值
    // 该方法使用@PostConstruct注解进行标注，说明此方法在bean生命周期的初始化阶段执行
    @PostConstruct
    public void init(){
        // 设置时，由于要传入的需要是实现了对应接口的对象，而当前配置类正好实现了本接口，因此直接传入this
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    // 三个参数分别表示发送的数据对象、是否成功发送消息（布尔值）、如果出现问题导致问题的原因（字符串）
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.info("data:{}",correlationData);
        log.info("ack:{}",b);
        log.info("cause:{}",s);
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消息主体: " + new String(returned.getMessage().getBody()));
        log.info("应答码: " + returned.getReplyCode());
        log.info("描述：" + returned.getReplyText());
        log.info("消息使用的交换器 exchange : " + returned.getExchange());
        log.info("消息使用的路由键 routing : " + returned.getRoutingKey());
    }

    @Bean
    @Resource
    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory){
        RabbitTransactionManager rabbitTransactionManager = new RabbitTransactionManager();
//        rabbitTransactionManager.setRollbackOnCommitFailure(true);
//        rabbitTransactionManager.setGlobalRollbackOnParticipationFailure(true);
        rabbitTransactionManager.setConnectionFactory(connectionFactory);

        return rabbitTransactionManager;
    }
}
