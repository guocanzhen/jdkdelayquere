package com.guocz.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guocz
 * @date 2022/8/4 10:10
 *
 * 消息接收确认配置
 */
@Configuration
@Log4j2
public class MessageListenerConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private MyAckReceiver myAckReceiver;

    @Bean(name = "simpleMessageListenerContainer")
    public SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置一个队列
        container.setQueueNames("queue.customAck");

        // 如果同时设置多个如下： 前提是队列都是必须已经创建存在的
//        container.setQueueNames("queue.customAck1", "queue.customAck2", "queue.customAck3");

        // 另一种设置队列的方法,如果使用这种情况,那么要设置多个,就使用addQueues
//        container.setQueues(new Queue("queue.customAck1",true),
//                new Queue("queue.customAck2",true),
//                new Queue("queue.customAck3",true));

        container.setMessageListener(myAckReceiver);
        return container;
    }
}
