package org.song.network.nettydemo.demo.beginner.beginner01.demo_05_websocket.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 泛型: 指的是要处理的数据类型
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到消息: " + msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器pong"));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().id().asLongText();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {


    }
}
