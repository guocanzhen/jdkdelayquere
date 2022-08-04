package com.guocz.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author guocz
 * @date 2022/8/3 11:11
 *
 * 直连模式 - 轮询的方式对消息进行消费，而且不存在重复消费
 * 消费监听
 */
@Component
@Log4j2
public class DirectCustomer {

    @RabbitHandler
    @RabbitListener(queues = "TestDirectQueue")
    public void process(Map testMsg) {
        log.info("TestDirectQueue接受到的消息：{}", testMsg.toString());
    }

    @RabbitHandler
    @RabbitListener(queues = "TestDirectQueue")
    public void process1(Map testMsg) {
        log.info("TestDirectQueue1接受到的消息：{}", testMsg.toString());
    }

    @RabbitHandler
    @RabbitListener(queues = "TestDirectQueue")
    public void process2(Map testMsg) {
        log.info("TestDirectQueue2接受到的消息：{}", testMsg.toString());
    }

}
