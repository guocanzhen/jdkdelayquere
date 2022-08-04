package com.guocz.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author guocz
 * @date 2022/8/3 13:53
 */
@Component
@Log4j2
public class TopicCustomer {

    /**
     *
     * @param map
     */
    @RabbitHandler
    @RabbitListener(queues = "topic.man")
    public void process1(Map map) {
        log.info("TopicCustomer1接受到的消息是：{}", map.toString());
    }

    /**
     *
     * @param map
     */
    @RabbitHandler
    @RabbitListener(queues = "topic.woman")
    public void process2(Map map) {
        log.info("TopicCustomer2接受到的消息是：{}", map.toString());
    }

}
