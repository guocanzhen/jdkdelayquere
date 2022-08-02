package com.guocz.queue.provider;

import com.guocz.queue.delayqueue.DelayTaskQueue;
import com.guocz.queue.task.DelayTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author guocz
 * @date 2022/7/29 10:35
 *
 * 消息生产者（根据业务需要投递消息进入队列）
 * 在需要使用到延时队列的业务进行投递任务(消息)
 */
@Log4j2
@Component
public class DelayTaskProducer<T> {

    /**
     *
     * @param id 业务id
     * @param time 消费时间  单位：毫秒
     * @param type 业务类型
     */
    public void delayTask(String id, Long time, Integer type, T body) {
        //创建队列 1
        DelayQueue<DelayTask> delayQueue = DelayTaskQueue.getInstance();
        //创建任务
        DelayTask delayTask = new DelayTask(id, time, type, TimeUnit.SECONDS, body);
        log.info("=============入延时队列,{}",delayTask);
        //任务入队
        boolean offer = delayQueue.offer(delayTask);
        if(offer){
            log.info("=============入延时队列成功,{}",delayQueue);
        }else{
            log.info("=============入延时队列失败");
        }

    }

}
