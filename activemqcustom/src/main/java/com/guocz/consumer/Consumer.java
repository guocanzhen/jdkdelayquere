package com.guocz.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author guocz
 * @date 2022/8/2 10:40
 *
 * 消息消费者
 */
@Component
@Log4j2
public class Consumer {

    @JmsListener(destination = "sms.queue", containerFactory = "queueListenerFactory")
    public void receiveMsg(String text) {
        log.info("接受到的消息是：{}", text);
    }


    @JmsListener(destination = "sms.topic", containerFactory = "topicListenerFactory")
    public void receiveMsg1(String text) {
        log.info("r1接受到的消息是：{}", text);
    }

    @JmsListener(destination = "sms.topic", containerFactory = "topicListenerFactory")
    public void receiveMsg2(String text) {
        log.info("r2接受到的消息是：{}", text);
    }

}
