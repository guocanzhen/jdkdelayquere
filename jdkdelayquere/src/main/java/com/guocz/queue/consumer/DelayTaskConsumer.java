package com.guocz.queue.consumer;

import com.guocz.queue.delayqueue.DelayTaskQueue;
import com.guocz.queue.task.DelayTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;

/**
 * @author guocz
 * @date 2022/7/29 10:50
 *
 * 消息消费者（规定自己相应的业务操作）
 */
@Log4j2
@Component
public class DelayTaskConsumer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //获取同一个put进去任务的队列
        DelayQueue<DelayTask> delayQueue = DelayTaskQueue.getInstance();
        new Thread(() -> {
            while (true) {
                try {
                    // 从延迟队列的头部获取已经过期的消息
                    // 如果暂时没有过期消息或者队列为空，则take()方法会被阻塞，直到有过期的消息为止
                    DelayTask delayTask = delayQueue.take();
                    //判断业务类型，执行对应的业务操作
                    switch (delayTask.getType()) {
                        case 1:
                            log.info("====================会议消费,{}-{}",delayTask.getType(), delayTask.getBody());
                            break;
                        case 2:
                            log.info("====================活动报名消费,{}-{}",delayTask.getType(), delayTask.getBody());
                            break;
                        case 3:
                            log.info("====================活动开始消费,{}-{}",delayTask.getType(), delayTask.getBody());
                            break;
                        default:
                            log.info("====================未找到消费类型,{}-{}",delayTask.getType(), delayTask.getBody());
                    }
                } catch (InterruptedException e) {
                    log.error(e);
                }
            }
        }).start();

    }
}
