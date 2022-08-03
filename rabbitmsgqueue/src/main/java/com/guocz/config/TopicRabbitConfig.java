package com.guocz.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guocz
 * @date 2022/8/3 11:39
 *
 * 主题
 * 交换机绑定队列
 */
@Configuration
public class TopicRabbitConfig {

    public static final String MAN = "topic.man";

    public static final String WOMAN = "topic.woman";

    @Bean
    public Queue manQueue() {
        return new Queue(TopicRabbitConfig.MAN);
    }

    @Bean
    public Queue womanQueue() {
        return new Queue(TopicRabbitConfig.WOMAN);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * 将manQueue和topicExchange绑定,而且绑定的键值为topic.man
     * 这样只有是消息携带的路由键是topic.man,才会分发到该队列
     * @return
     */
    @Bean
    public Binding bindingMan() {
        return BindingBuilder.bind(manQueue()).to(topicExchange()).with(MAN);
    }

    /**
     * 将womanQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
     * 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
     * @return
     */
    @Bean
    public Binding bindingWoman() {
        return BindingBuilder.bind(womanQueue()).to(topicExchange()).with("topic.#");
    }

}
