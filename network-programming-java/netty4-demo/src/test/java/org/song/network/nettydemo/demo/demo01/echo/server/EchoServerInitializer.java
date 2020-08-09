package org.song.network.nettydemo.demo.demo01.echo.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 定义自定义初始化器, 继承 ChannelInitializer, 其中泛型SocketChannel指的是, 通信传输使用TCP的NIO是SocketChannel, 使用HTTP 是 HttpObject
 * 并重写 方法 initChannel,
 */
public class EchoServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 需要重写初始化方法, 并制定初始化流程
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // EchoServerHandler被标注为@Shareable，所以我们可以总是使用同样的实例
        // 添加自定义初始化处理器
        pipeline.addLast(new EchoServerHandler());
    }
}
