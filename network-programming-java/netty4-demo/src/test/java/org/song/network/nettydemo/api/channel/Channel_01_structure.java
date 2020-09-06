package org.song.network.nettydemo.api.channel;

public class Channel_01_structure {

    /**
     * Channel, netty中的通道, 完全独立于 java 中的 通道
     * 和java中的通道类似, 用于表示一个连接, netty中的通道专门用做网络通信连接
     * <p>
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
     * <p>
     * -    -   -   - AbstractNioChannel 抽象类
     * -    -   -   -   - AbstractNioByteChannel 抽象类
     * -    -   -   -   -   - NioSocketChannel 类
     * -    - DuplexChannel 接口
     * -    -   - SocketChannel 接口, netty包下的
     * -    -   -   -   -   - NioSocketChannel 类
     * -    -   -   -   -   - EpollSocketChannel 类
     * -    -   -   -   -   - KQueueSocketChannel 类
     */
    public void test_01_Channel() {

    }

    /**
     * ChannelHandler 接口, 通道处理器
     * -    - ChannelInboundHandler 接口
     * -    -   - ChannelInboundHandlerAdapter 类
     * -    - ChannelOutboundHandler 接口
     * -    -   - ChannelOutboundHandlerAdapter 类
     */
    public void test_01_ChannelHandler() {

    }

    /**
     * ChannelPipeline ChannelHandler 处理流水线
     * <p>
     * ChannelInboundInvoker 接口
     * ChannelOutboundInvoker 接口
     * -    - ChannelPipeline 接口
     * -    -   - DefaultChannelPipeline 类
     */
    public void test_01_ChannelPipeline() {

    }

    /**
     * ChannelFuture 接口, 首先他是一个 future
     * <p>
     * <p>
     * Future 接口, java中的
     * -    - Future 接口, netty 重写了
     * -    -   - ChannelFuture 接口
     */
    public void test_01_ChannelFuture() {

    }
}
