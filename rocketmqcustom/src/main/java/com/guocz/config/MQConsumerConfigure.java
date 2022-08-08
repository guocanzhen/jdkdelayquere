package com.guocz.config;

import com.guocz.consum.MQConsumeMsgListenerProcessor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guocz
 * @date 2022/8/8 13:57
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "rocketmq.consumer")
@Log4j2
public class MQConsumerConfigure {

    private String nameSrvAdd;

    private String groupName;

    private String topics;

    private Integer consumeThreadMin;

    private Integer consumeThreadMax;

    private Integer consumeMessageBatchMaxSize;

    @Autowired
    private MQConsumeMsgListenerProcessor processor;

    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.consumer", value = "isOnOff", havingValue = "on")
    public DefaultMQPushConsumer consumer() throws MQClientException {
        log.info("defaultConsumer 正在创建---------------------------------------");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameSrvAdd);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

        // 设置监听
        consumer.registerMessageListener(processor);
        // 设置consumer第一次启动是从队列头部开始还是队列尾部开始
        // 如果不是第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        // 设置消费模型，集群还是广播，默认为集群
        consumer.setMessageModel(MessageModel.CLUSTERING);

        // 设置该消费者订阅的主题和tag，如果订阅该主题下的所有tag，则使用*
        try {
            String[] topicArr = topics.split(";");
            for (String topic :
                    topicArr) {
                String[] tarArr = topic.split("~");
                consumer.subscribe(tarArr[0], tarArr[1]);
            }
            consumer.start();
            log.info("consumer 创建成功 groupName={}, topics={}, namesrvAddr={}", groupName, topics, nameSrvAdd);
        }catch (MQClientException e) {
            log.error("consumer 创建失败!{}", e.getMessage());
        }
        return consumer;
    }
}
