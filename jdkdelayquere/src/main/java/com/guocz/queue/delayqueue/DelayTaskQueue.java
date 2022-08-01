package com.guocz.queue.delayqueue;

import com.guocz.queue.task.DelayTask;

import java.util.concurrent.DelayQueue;

/**
 * @author guocz
 * @date 2022/7/29 10:30
 *
 * 创建延时队列
 *
 * * 延时队列
 * * 需要保证队列单例
 */
public class DelayTaskQueue {

    private static class Holder{
        static DelayQueue<DelayTask> instance = new DelayQueue();
    }

    public static DelayQueue<DelayTask> getInstance() {
        return Holder.instance;
    }
}
