package org.song.network.nettydemo.demo.beginner.beginner_02_serialize.demo_01_protobuf.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ProtobufClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(boss)
                    .channel(NioSocketChannel.class)
                    .handler(new ProtobufClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
            ChannelFuture sync = channelFuture.channel().closeFuture().sync();

        } finally {
            boss.shutdownGracefully();
        }

    }
}
