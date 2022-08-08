package com.guocz.consum;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author guocz
 * @date 2022/8/8 14:06
 *
 * 消费者监听
 */
@Log4j2
@Component
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {

    /**
     * 默认msg里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
     * 不要抛异常，如果没有return CONSUME_SUCCESS ，consumer会重新消费该消息，直到return CONSUME_SUCCESS
     * @param list
     * @param context
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        if (CollectionUtils.isEmpty(list)) {
            log.info("MQ接收消息为空，直接返回成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        MessageExt messageExt = list.get(0);
        log.info("MQ接收到的消息为：{}", messageExt.toString());

        String topic = messageExt.getTopic();
        String tags = messageExt.getTags();
        String body = new String(messageExt.getBody());
        log.info("MQ消息topic={}, tags={}, 消息内容={}", topic, tags, body);

        // TODO: 2022/8/8 业务处理

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
