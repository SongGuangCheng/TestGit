package org.song.network.nettydemo.demo.intermediate.demo_01_source_ode.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 自定义一个简单的 netty实现的 TCP socket通信服务器
 */
public class SecondServer {

    public static void main(String[] args) throws InterruptedException {
        // 接受客户端连接 (死循环的处理)
        EventLoopGroup boss = new NioEventLoopGroup();
        // 处理业务逻辑 (死循环的处理)
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    // handler 是 boss线程组执行的
//                    .handler()
                    // childHandler 是 worker 线程组执行的
                    .childHandler(new SecondServerInitializer());

            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
