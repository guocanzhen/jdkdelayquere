package com.guocz.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guocz
 * @date 2022/8/8 13:45
 */
@Configuration
@Log4j2
@Data
@ConfigurationProperties(prefix = "rocketmq.producer")
public class MQProducerConfigure {

    private String nameSrvAdd;

    private String groupName;

    private Integer maxMessageSize;

    private Integer sendMsgTimeOut;

    private Integer retryTimesWhenSendFailed;

    @Bean("defaultMQProducer")
    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "isOnOff", havingValue = "on")
    public DefaultMQProducer defaultMqProducer() throws MQClientException {
        log.info("defaultProducer 正在创建---------------------------------------");
        DefaultMQProducer producer = new DefaultMQProducer( groupName);
        producer.setNamesrvAddr(nameSrvAdd);
        producer.setVipChannelEnabled(false);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setSendMsgTimeout(sendMsgTimeOut);
        producer.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendFailed);
        producer.start();
        log.info("rocketmq producer server 开启成功----------------------------------");
        return producer;
    }
}
