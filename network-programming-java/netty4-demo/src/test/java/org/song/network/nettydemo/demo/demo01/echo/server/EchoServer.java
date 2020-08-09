package org.song.network.nettydemo.demo.demo01.echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 引导服务器
 * <p>
 * 所有的 Netty 服务器都需要以下3部分。
 * 1. 引导: 服务器基础配置, 线程模型/IO模型/日志 并初始化器
 * 2. 初始化器: 用于初始化服务器, 指定handler
 * 3. 自定义handler: 用于自己的业务处理
 */
public class EchoServer {

    private static final int port = 9999;

    public static void main(String[] args) throws Exception {
        start();
    }

    /**
     * ServerBootstrap 创建了一个服务端引导 实例。使用NIO线程组 NioEventLoopGroup 指定线程模型
     * 使用NIO进行传输 SocketChannel .channel(NioServerSocketChannel.class)。
     * 指定初始化器, 你使用了一个特殊的类——ChannelInitializer。这是关键。
     * （对 sync()方法的调用将导致当前 Thread阻塞，一直到绑定操作完成为止）。
     * 在 处，该应用程序将会阻塞等待直到服务器的 Channel
     * 关闭（因为你在 Channel 的 CloseFuture 上调用了 sync()方法）。然后，你将可以关闭
     * EventLoopGroup，并释放所有的资源，包括所有被创建的线程
     *
     * @throws Exception
     */
    public static void start() throws Exception {
        // 创建Event-LoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建Server-Bootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 指定所使用的 NIO 传输 Channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加一个初始化器
                    .childHandler(new EchoServerInitializer());
            // 异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            // 获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            // 关闭 EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
