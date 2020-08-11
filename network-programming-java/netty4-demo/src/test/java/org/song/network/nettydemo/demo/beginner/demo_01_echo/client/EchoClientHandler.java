package org.song.network.nettydemo.demo.beginner.demo_01_echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * ChannelHandler 实现客户端逻辑
 * 客户端将拥有一个用来处理数据的 ChannelInboundHandler。在这个场景
 * 下，你将扩展 SimpleChannelInboundHandler 类以处理所有必须的任务，如代码清单 2-3
 * 所示。这要求重写下面的方法：
 * channelActive()——在到服务器的连接已经建立之后将被调用；
 * channelRead0() ——当从服务器接收到一条消息时被调用；
 * exceptionCaught()——在处理过程中引发异常时被调用。
 */

/**
 * 标记该类的实例可以被多个 Channel 共享
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 当被通知 Channel 是活跃的时候，发送一条消息
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        System.out.println("client channelActive 发送消息");
        ctx.writeAndFlush(Unpooled.copiedBuffer("client Netty rocks!", CharsetUtil.UTF_8));
    }

    /**
     * 记录已接收消息的转储
     *
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("client channelRead0 方法, 记录已接收消息的转储 : " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("client Client received: " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在发生异常时，记录错误并关闭
     * Channel
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("client 异常");
        cause.printStackTrace();
        ctx.close();
    }
}
