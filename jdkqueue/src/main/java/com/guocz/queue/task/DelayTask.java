package com.guocz.queue.task;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author guocz
 * @date 2022/7/29 10:32
 *
 * 创建消息任务(可根据自己业务来)
 *
 * * 延时任务
 *  * 需要实现 Delayed 接口 重写getDelay和compareTo指定相应的规则
 */
@Data
@Accessors(chain = true)
public class DelayTask<T> implements Delayed, Serializable {

    /**
     * 业务ID
     */
    private String id;

    /**
     * 消费时间  单位：秒
     */
    private Long time;

    /**
     * 业务类型
     */
    private Integer type;

    /**
     * 任务参数
     */
    private T body;

    public DelayTask(String id, Long time, Integer type, TimeUnit unit, T body) {
        this.id = id;
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
        this.type = type;
        this.body = body;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        // 计算该任务距离过期还剩多少时间（毫秒）
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        // 比较、排序：对任务的延时大小进行排序，将延时时间最小的任务放到队列头部
        long diff = this.time - ((DelayTask<?>) o).time;
        if (diff <= 0) {
            return -1;
        }else {
            return 1;
        }
    }
}
