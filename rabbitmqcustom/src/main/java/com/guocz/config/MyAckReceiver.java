package com.guocz.config;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @author guocz
 * @date 2022/8/4 10:12
 *
 * 手动确认消息监听类
 * 手动确认模式需要实现 ChannelAwareMessageListener
 */
@Component
@Log4j2
public class MyAckReceiver implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
            Map<String, String> map = (Map<String, String>) ois.readObject();
            String msgId = map.get("msgId");
            String msg = map.get("msg");
            String createTime = map.get("createTime");
            ois.close();
            log.info("MyAckReceiver msgId:{},msg:{},createTime:{}", msgId, msg, createTime);
            log.info("消费的主题消息来自：{}", message.getMessageProperties().getConsumerQueue());
            // 第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            channel.basicAck(deliveryTag, true);
            // 第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
//        channel.basicReject(deliveryTag, true);
        }catch (Exception e) {
            channel.basicReject(deliveryTag, true);
            log.error(e.getMessage());
        }
    }
}
