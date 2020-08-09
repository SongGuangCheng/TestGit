package org.song.network.nettydemo.demo.demo01.first.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 实现自定义 handler, 继承 SimpleChannelInboundHandler, 泛型 HttpObject 处理 HTTP 信息
 */
public class FirstServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 重写方法 channelRead0, netty4中的 读取请求方法
     * 在5.0中会重命名为messageReceived消息接收(5.0虽然已经弃用)
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest){
            // 自定义响应信息
            ByteBuf content = Unpooled.copiedBuffer("Hello world!", CharsetUtil.UTF_8);
            // HTTP 响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            // 设置HTTP响应header信息
            response.headers().set("content-type", "text/plain");
            // 写入并刷入到客户端
            ctx.writeAndFlush(response);
        }
    }
}
