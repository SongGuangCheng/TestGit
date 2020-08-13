package org.song.network.nettydemo.demo.beginner.beginner_01_communication.demo_04_heartbeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HeartBeatClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup master = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(master)
                    // 使用反射的方式 创建 channel
                    .channel(NioSocketChannel.class)
                    .handler(new HeartbeatClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            master.shutdownGracefully();
        }
    }

}
