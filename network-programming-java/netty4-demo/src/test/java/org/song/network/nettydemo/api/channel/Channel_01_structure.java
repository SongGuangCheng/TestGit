package org.song.network.nettydemo.api.channel;

public class Channel_01_structure {

    /**
     * Channel, netty中的通道, 完全独立于 java 中的 通道
     * 和java中的通道类似, 用于表示一个连接, netty中的通道专门用做网络通信连接
     *
     * Channel 的主要结构
     *
     * Channel 接口, netty包下的, 继承 ChannelOutboundInvoker 接口
     * -    - AbstractChannel 抽象类
     * -    -   - AbstractServerChannel 抽象类
     * -    -   - AbstractEpollChannel 抽象类
     * -    -   - AbstractKQueueChannel 抽象类
     * -    -   - AbstractNIOChannel 抽象类
     * -    -   - EmbeddedChannel 类
     * -    - ServerChannel 接口
     * -    -   -   - AbstractEpollServerChannel 抽象类
     * -    -   -   - AbstractKQueueServerChannel 抽象类
     * -    -   -   - ServerDomainSocketChannel 接口
     * -    -   - AbstractServerChannel 抽象类
     * -    -   - ServerSocketChannel 接口, netty包下的
     * -    -   -   -   -   - NioServerSocketChannel 类
     * -    -   -   -   -   - EpollServerSocketChannel 类
     * -    -   -   -   -   - KQueueServerSocketChannel 类
     * -    - DuplexChannel 接口
     * -    -   - SocketChannel 接口, netty包下的
     * -    -   -   -   -   - NioSocketChannel 类
     * -    -   -   -   -   - EpollSocketChannel 类
     * -    -   -   -   -   - KQueueSocketChannel 类
     * -    - UnixChannel 接口
     * -    - Http2StreamChannel 接口
     * -    - DatagramChannel 接口
     * -    - SctpChannel 接口
     *
     */
    public void test_01_Channel() {

    }

    /**
     * ChannelHandler 接口, 通道处理器
     * -    - ChannelHandlerAdapter 抽象类
     * -    - ChannelInboundHandler 接口
     * -    -   - ChannelInitializer 抽象类
     * -    -   - ChannelInboundHandlerAdapter 类
     * -    -   -   - ByteToMessageDecoder 抽象类
     * -    -   -   -   - ReplayingDecoder 抽象类
     * -    -   -   - MessageToMessageDecoder 抽象类
     * -    -   -   -   - MessageAggregator 抽象类
     * -    -   -   -   -   - HttpObjectAggregator 抽象类
     * -    -   -   - SimpleChannelInboundHandler 类
     * -    -   -   - ChannelDuplexHandler 类
     * -    -   -   -   - IdleStateHandler 类
     * -    -   -   -   - MessageToMessageCodec 类
     * -    - ChannelOutboundHandler 接口
     * -    -   - ChannelOutboundHandlerAdapter 类
     * -    -   -   - ChannelDuplexHandler 类
     * -    -   -   -   - IdleStateHandler 类
     * -    -   -   -   - MessageToMessageCodec 类
     * -    -   -   - MessageToMessageEncoder 抽象类
     * -    -   -   - MessageToByteEncoder 抽象类
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
     * -    -   -   - ChannelPromise 接口
     * -    -   -   - CompleteChannelFuture 抽象类
     */
    public void test_01_ChannelFuture() {

    }
}
