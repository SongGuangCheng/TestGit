package org.song.network.nettydemo.api.eventloop;

import org.junit.jupiter.api.Test;

public class EventLoop_01_structure {

    /**
     * EventLoopGroup 事件循环组, 是一个任务执行器, 执行的任务是: IO 事件, 执行的方式是: 在执行器中循环执行
     * <p>
     * EventLoopGroup 接口 继承 ScheduledExecutorService
     * -    - EventExecutorGroup 接口
     * -    -   - AbstractEventExecutorGroup 抽象类, 实现了任务执行器的任务调度逻辑, 主要是交给 next() 执行
     * -    -   -   - MultithreadEventExecutorGroup 抽象类
     * -    -   -   -   - MultithreadEventLoopGroup 抽象类
     * -    -   -   - EventLoopGroup 接口
     * -    -   -   -   -   - NioEventLoopGroup 类
     * -    -   -   -   -   - EpollEventLoopGroup 类
     * -    -   -   -   -   - KQueueEventLoopGroup 类
     * -    -   -   -   -   - DefaultEventLoopGroup 类
     */
    @Test
    public void test_01_EventLoopGroup() {

    }
}
