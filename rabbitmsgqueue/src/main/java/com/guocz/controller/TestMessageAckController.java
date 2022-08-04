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
 * @date 2022/8/4 9:22
 *
 * 消息的回调（消息确认）
 * 测试接口：
 * 两个回调函数，一个叫 ConfirmCallback ，一个叫 RetrunCallback；
 */
@RestController
@RequestMapping("testAck")
@Log4j2
public class TestMessageAckController {

    @Autowired
    private RabbitTemplate template;

    /**
     * ①消息推送到server，但是在server里找不到交换机
     * ConfirmCallback
     */
    @GetMapping("testAck1")
    public void testMessageAck1() {
        String msgId = String.valueOf(UUID.randomUUID());
        String msg = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("msgId", msgId);
        map.put("msg", msg);
        map.put("createTime", createTime);
        template.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
        log.info("消息已推送：{}", map.toString());
    }

    /**
     * ②消息推送到server，找到交换机了，但是没找到队列
     * ConfirmCallback和ReturnCallback
     */
    @GetMapping("testAck2")
    public void testMessageAck2() {
        String msgId = String.valueOf(UUID.randomUUID());
        String msg = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(16);
        map.put("msgId", msgId);
        map.put("msg", msg);
        map.put("createTime", createTime);
        template.convertAndSend("onlyDirectExchange", "TestDirectRouting", map);
        log.info("消息已推送：{}", map.toString());
    }



}
