package org.song.network.nettydemo.demo.beginner.beginner01.demo_05_websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * netty 实现简单的 websocket
 * 可以通过浏览器的控制台等, 查看ws协议升级过程, 以及消息发送帧信息
 */
public class WebSocketServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup master = new NioEventLoopGroup();
        EventLoopGroup slaver = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(master, slaver)
                    // 使用反射的方式 初始化 channel,
                    .channel(NioServerSocketChannel.class)
                    // 主线程master处理使用 handler
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketInitializer());

            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            master.shutdownGracefully();
            slaver.shutdownGracefully();
        }
    }

}
