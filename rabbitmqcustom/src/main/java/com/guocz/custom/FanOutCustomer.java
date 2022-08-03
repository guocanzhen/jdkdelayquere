package com.guocz.custom;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author guocz
 * @date 2022/8/3 16:43
 *
 * 扇形
 * 消费监听
 */
@Component
@Log4j2
public class FanOutCustomer {

    @RabbitListener(queues = "queue.A")
    @RabbitHandler
    public void processA(Map map) {
        log.info("FanOutA接受到的消息是：{}", map.toString());
    }

    @RabbitListener(queues = "queue.B")
    @RabbitHandler
    public void processB(Map map) {
        log.info("FanOutB接受到的消息是：{}", map.toString());
    }

    @RabbitListener(queues = "queue.C")
    @RabbitHandler
    public void processC(Map map) {
        log.info("FanOutC接受到的消息是：{}", map.toString());
    }

}
