package com.guocz.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.nio.charset.StandardCharsets;

/**
 * @author guocz
 * @date 2022/8/8 14:30
 *
 * 消息生产者
 */
@RestController
@RequestMapping("pro")
@Log4j2
public class MQProducerController {

    @Autowired
    private DefaultMQProducer producer;

    @GetMapping("send")
    public void send(@PathParam("msg") String msg, @PathParam("topic") String topic, @PathParam("tag") String tag) throws Exception {
        if (StringUtils.isBlank(msg)){
            return;
        }
        log.info("发送MQ消息内容：{}", msg);
        Message message = new Message(topic, tag, msg.getBytes(StandardCharsets.UTF_8));
        // 默认3秒超时
        SendResult result = producer.send(message);
        log.info("消息发送响应：{}", result.toString());
    }
}
