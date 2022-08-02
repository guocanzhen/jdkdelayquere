package com.guocz.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;
import javax.websocket.server.PathParam;

/**
 * @author guocz
 * @date 2022/8/2 10:33
 *
 * 消息生产者
 */
@RestController
@RequestMapping("pro")
@Log4j2
public class ProductorController {

    @Autowired
    private JmsMessagingTemplate template;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @GetMapping("queue")
    public void sendMsg(@PathParam("msg") String msg) {
        log.info("发送的消息内容是：{}", msg);
        template.convertAndSend(queue, msg);
    }

    @GetMapping("topic")
    public void sendTopic(@PathParam("msg") String msg){
        log.info("发送的topic是：{}", msg);
        template.convertAndSend(topic, msg);
    }
}
