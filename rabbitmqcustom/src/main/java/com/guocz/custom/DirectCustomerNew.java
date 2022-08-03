package com.guocz.custom;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author guocz
 * @date 2022/8/3 11:11
 *
 * 直连模式
 * 消费监听
 */
@Component
@RabbitListener(queues = "TestDirectQueue")
@Log4j2
public class DirectCustomerNew {

    @RabbitHandler
    public void process(Map testMsg) {
        log.info("TestDirectQueueNew接受到的消息：{}", testMsg.toString());
    }

}
