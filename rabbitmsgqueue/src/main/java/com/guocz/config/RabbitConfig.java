package com.guocz.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guocz
 * @date 2022/8/3 17:00
 *
 *
 */
@Configuration
@Log4j2
public class RabbitConfig {

    /**
     * 配置消息回调
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        // 交换机回调情况
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            log.info("ConfirmCallback：相关数据：{}", correlationData);
            log.info("ConfirmCallback：确认情况：{}", b);
            log.info("ConfirmCallback：原因：{}", s);
        });

        // 队列回调情况，若交换机成功，路由不成功，则触发
        rabbitTemplate.setReturnsCallback( returnedMessage -> {
            log.info("ReturnsCallback：消息：{}", returnedMessage.getMessage());
            log.info("ReturnsCallback：回应码：{}", returnedMessage.getReplyCode());
            log.info("ReturnsCallback：回应消息：{}", returnedMessage.getReplyText());
            log.info("ReturnsCallback：交换机：{}", returnedMessage.getExchange());
            log.info("ReturnsCallback：路由键：{}", returnedMessage.getRoutingKey());
        });
        return rabbitTemplate;
    }
}
