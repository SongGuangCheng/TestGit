package org.song.network.nettydemo.demo.beginner.beginner_01_communication.demo_04_heartbeat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class HeartbeatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        /**
         * 空闲检测 handler, 用于心跳
         * 一定时间内 没有 读, 写, 读或写 则触发事件
         */
        pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));

        pipeline.addLast(new HeartbeatServerHandler());
    }
}
