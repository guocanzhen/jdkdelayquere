package com.guocz;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author guocz
 * @date 2022/8/2 16:59
 */
@SpringBootApplication
@EnableRabbit
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
