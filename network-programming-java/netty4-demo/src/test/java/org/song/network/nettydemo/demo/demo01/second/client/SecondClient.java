package org.song.network.nettydemo.demo.demo01.second.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SecondClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    // handler 是主线程组使用的
                    .handler(new SecondClientInitializer());

            // 使用connect建立连接
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            worker.shutdownGracefully();
        }

    }
}
