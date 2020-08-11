package org.song.network.nettydemo.demo.beginner.beginner01.demo_02_socket.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端的 handler 和服务端的 handler 继承一样的类, 泛型
 */
public class SecondClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ": " + msg);
        System.out.println("client out: " + msg);
    }

    /******************************可重写回调**************************/


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 建立连接成功后, 向服务端发送信息
        ctx.writeAndFlush("来自客户端的问候");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        // 关闭连接
        ctx.close();
    }
}
