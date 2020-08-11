package org.song.network.nettydemo.demo.beginner.demo_01_echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义处理器,
 * Inbound: 指的是数据请求处理
 *
 * 所有的 Netty 服务器都需要以下两部分。
 * 1. 至少一个 ChannelHandler — 该组件实现了服务器对从客户端接收的数据的处理，即
 * 它的业务逻辑。
 * 2. 引导 — 这是配置服务器的启动代码。至少，它会将服务器绑定到它要监听连接请求的
 * 端口上。
 */

/**
 * 标示一个Channel-Handler 可以被多个 Channel 安全地共享
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * channelRead() — 对于每个传入的消息都要调用；
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        //将消息记录到控制台
        System.out.println("server channelRead 方法, 传入消息: " + in.toString(CharsetUtil.UTF_8));
        System.out.println("server Server received: " + in.toString(CharsetUtil.UTF_8));
        //将接收到的消息写给发送者，而不冲刷出站消息
        ctx.write(in);
    }

    /**
     * channelReadComplete() — 通知ChannelInboundHandler最后一次对channel-
     * Read()的调用是当前批量读取中的最后一条消息；
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                //将未决消息冲刷到远程节点，并且关闭该 Channel
                .addListener(ChannelFutureListener.CLOSE);
        System.out.println("server channelReadComplete 方法");
    }

    /**
     * exceptionCaught() — 在读取操作期间，有异常抛出时会调用。
     *
     * 如果不捕获异常，会发生什么呢
     * 每个 Channel 都拥有一个与之相关联的 ChannelPipeline，其持有一个 ChannelHandler 的
     * 实例链。在默认的情况下，ChannelHandler 会把对它的方法的调用转发给链中的下一个 Channel-
     * Handler。因此，如果 exceptionCaught()方法没有被该链中的某处实现，那么所接收的异常将会被
     * 传递到 ChannelPipeline 的尾端并被记录。为此，你的应用程序应该提供至少有一个实现了
     * exceptionCaught()方法的 ChannelHandler。（6.4 节详细地讨论了异常处理）。
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("server 异常");
        //打印异常栈跟踪
        cause.printStackTrace();
        //关闭该Channel
        ctx.close();
    }
}
