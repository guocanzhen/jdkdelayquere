package com.guocz.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author guocz
 * @date 2022/8/4 10:41
 *
 * 为消费者测试消息接收确认提供测试条件
 */
@RestController
@RequestMapping("testConsumer")
@Log4j2
public class TestConsumerAckController {

    @Autowired
    private RabbitTemplate template;

    @GetMapping("consumerAck")
    public void consumerAck() {
        String msgId = String.valueOf(UUID.randomUUID());
        String msg = "consumerAck";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Object> map = new HashMap<>(16);
        map.put("msgId", msgId);
        map.put("msg", msg);
        map.put("createTime", createTime);
        template.convertAndSend("exchangeCustomAck", "customAckRouting", map);
        log.info("消息已发送：{}", map.toString());
    }
}
