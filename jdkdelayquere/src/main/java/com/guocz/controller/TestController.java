package com.guocz.controller;

import com.guocz.queue.BusiTypeDefiner;
import com.guocz.queue.provider.DelayTaskProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guocz
 * @date 2022/7/29 10:17
 *
 * jdk延时队列测试
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private DelayTaskProducer<Object> delayTaskProducer;

    @ResponseBody
    @GetMapping("task01")
    public void task01() {
        delayTaskProducer.delayTask("task01", 5L, BusiTypeDefiner.HY_CONSUMER, null);
    }

    @ResponseBody
    @GetMapping("task011")
    public void task011() {
        delayTaskProducer.delayTask("task011", 5L, BusiTypeDefiner.HY_CONSUMER, "ghjk");
    }

    @ResponseBody
    @GetMapping("task02")
    public void task02() {
        delayTaskProducer.delayTask("task02", 5L, BusiTypeDefiner.HD_BM_CONSUMER, 3);
    }

    @ResponseBody
    @GetMapping("task03")
    public void task03() {
        delayTaskProducer.delayTask("task03", 5L, BusiTypeDefiner.HD_KS_CONSUMER, 3L);
    }

    @ResponseBody
    @GetMapping("task04")
    public void task04() {
        delayTaskProducer.delayTask("task04", 5L, 5, 5.5);
    }


}
