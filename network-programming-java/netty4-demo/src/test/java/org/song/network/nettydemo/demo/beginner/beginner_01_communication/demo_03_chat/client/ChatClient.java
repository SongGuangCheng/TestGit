package org.song.network.nettydemo.demo.beginner.beginner_01_communication.demo_03_chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();

            // 接受来自键盘的标准输入
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // 循环接收键盘输入, 一次读取并写入一行
                channelFuture.channel().writeAndFlush(br.readLine() + "\r\n");
            }

        } finally {
            worker.shutdownGracefully();
        }

    }
}
