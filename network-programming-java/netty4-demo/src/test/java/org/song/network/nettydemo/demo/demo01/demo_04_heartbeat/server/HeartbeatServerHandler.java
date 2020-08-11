package org.song.network.nettydemo.demo.demo01.demo_04_heartbeat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 继承 SimpleChannelInboundHandler
 * 是 SimpleChannelInboundHandler 的父类 SimpleChannelInboundHandler
 * 相当于自己写一个 SimpleChannelInboundHandler, 功能也是自定义的handler
 */
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        super.userEventTriggered(ctx, evt);
    }
}
