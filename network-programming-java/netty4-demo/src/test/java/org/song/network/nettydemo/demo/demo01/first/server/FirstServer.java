package org.song.network.nettydemo.demo.demo01.first.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class FirstServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        // 使用主从多线程线程模型
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .localAddress("localhost", 9999)
                .childHandler(null);

        ChannelFuture channelFuture = bootstrap.bind().sync();

    }
}
