package org.song.network.nettydemo.demo.demo01.demo_02_socket.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class SecondServerHandler extends SimpleChannelInboundHandler<String> {

    /***********************************必须重写************************************/

    /**
     * @param ctx 上下文
     * @param msg 请求的类型, 同时也是类的泛型
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ": " + msg);
        System.out.println("server out: " + msg);
        // 写+冲刷缓冲区 = write+flush
        ctx.channel().writeAndFlush("from server" + UUID.randomUUID().toString());
    }


    /***********************************可选重写回调方法***********************************/
    /**
     * 出现异常的时候回调
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        // 关闭连接
        ctx.close();
    }
}
