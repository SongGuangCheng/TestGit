package org.song.network.nettydemo.demo.beginner.beginner_01_communication.demo_04_heartbeat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 继承 SimpleChannelInboundHandler
 * 是 SimpleChannelInboundHandler 的父类 SimpleChannelInboundHandler
 * 相当于自己写一个 SimpleChannelInboundHandler, 功能也是自定义的handler
 */
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            // 空下线状态事件
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            String eventType = "";
            switch (stateEvent.state()) {
                case READER_IDLE:
                    // 读空闲
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    // 写空闲
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    // 读写空闲
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + ": " + eventType);
        }

    }
}
