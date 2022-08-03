package com.guocz.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guocz
 * @date 2022/8/3 16:16
 *
 * 扇形
 * 交换机
 */
@Configuration
public class FanoutRabbitConfig {

    @Bean
    public Queue queueA() {
        return new Queue("queue.A");
    }

    @Bean
    public Queue queueB() {
        return new Queue("queue.B");
    }

    @Bean
    public Queue queueC() {
        return new Queue("queue.C");
    }

    @Bean(name = "fanOutExchange")
    public FanoutExchange exchange() {
        return new FanoutExchange("fanOutExchange");
    }

    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(queueA()).to(exchange());
    }

    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(exchange());
    }

    @Bean
    public Binding bindingC() {
        return BindingBuilder.bind(queueC()).to(exchange());
    }

}
