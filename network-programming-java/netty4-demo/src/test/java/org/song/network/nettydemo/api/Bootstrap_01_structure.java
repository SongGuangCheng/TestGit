package org.song.network.nettydemo.api;

import org.junit.jupiter.api.Test;

public class Bootstrap_01_structure {

    /**
     * Bootstrap netty 的引导程序工具类
     * 用于方便的创建 服务端程序 和 客户端程序
     *
     * Channel
     * -    - AbstractBootstrap
     * -    -   - ServerBootstrap
     * -    -   - Bootstrap
     */
    @Test
    public void test_01_bootstrap(){

    }

    /**
     *
     * EventLoopGroup 事件循环组, 是一个任务执行器, 执行的任务是: IO 事件, 执行的方式是: 在执行器中循环执行
     *
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
    public void test_02_EventLoopGroup(){

    }

    /**
     *
     * Channel, netty中的通道, 完全独立于 java 中的 通道
     * 和java中的通道类似, 用于表示一个连接, netty中的通道专门用做网络通信连接
     *
     * Channel 接口, netty包下的, 继承 ChannelOutboundInvoker 接口
     * -    - DefaultAttributeMap 抽象类
     * -    -   - AbstractChannel 抽象类
     * -    -   -   - AbstractNioChannel 抽象类
     * -    -   -   -   - AbstractNioMessageChannel 抽象类
     * -    -   -   -   -   - NioServerSocketChannel 类
     * -    - ServerChannel 接口 (空实现)
     * -    -   - ServerSocketChannel 接口, netty包下的
     * -    -   -   -   -   - NioServerSocketChannel 类
     * -    -   -   -   -   - EpollServerSocketChannel 类
     * -    -   -   -   -   - KQueueServerSocketChannel 类
     *
     * -    -   -   - AbstractNioChannel 抽象类
     * -    -   -   -   - AbstractNioByteChannel 抽象类
     * -    -   -   -   -   - NioSocketChannel 类
     * -    - DuplexChannel 接口
     * -    -   - SocketChannel 接口, netty包下的
     * -    -   -   -   -   - NioSocketChannel 类
     * -    -   -   -   -   - EpollSocketChannel 类
     * -    -   -   -   -   - KQueueSocketChannel 类
     */
    public void test_03_Channel(){

    }

    /**
     * ChannelHandler 接口, 通道处理器
     * -    - ChannelInboundHandler 接口
     * -    -   - ChannelInboundHandlerAdapter 类
     * -    - ChannelOutboundHandler 接口
     * -    -   - ChannelOutboundHandlerAdapter 类
     */
    public void test_03_ChannelHandler(){

    }

    /**
     * ChannelPipeline ChannelHandler 处理流水线
     *
     * ChannelInboundInvoker 接口
     * ChannelOutboundInvoker 接口
     * -    - ChannelPipeline 接口
     * -    -   - DefaultChannelPipeline 类
     */
    public void test_03_ChannelPipeline(){

    }

    /**
     * ChannelFuture 接口, 首先他是一个 future
     *
     *
     * Future 接口, java中的
     * -    - Future 接口, netty 重写了
     * -    -   - ChannelFuture 接口
     *
     */
    public void test_03_ChannelFuture(){

    }
}
