package org.song.network.nettydemo.demo.demo01.demo_01_http.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 实现自定义 handler, 继承 SimpleChannelInboundHandler, 泛型 HttpObject 处理 HTTP 信息
 * Inbound: 处理请求
 */
public class FirstServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /****************************************数据处理方法, 必须重写*******************************************/

    /**
     * 必须: 重写方法 channelRead0, netty4中的 读取请求方法
     * 在5.0中会重命名为messageReceived消息接收(5.0虽然已经弃用)
     * <p>
     * netty实现的Http服务器比较底层, 并没有实现servlet, 很多功能没有实现, 需要自己手动实现,
     * 比如:参数动态绑定, 请求路由, 请求方法区分等
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("具体请求: " + msg.getClass());
        System.out.println("远程地址(客户端): " + ctx.channel().remoteAddress());
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("请求方法名: " + httpRequest.getMethod().name());
            if ("/favicon.ico".equals(httpRequest.getUri())) {
                return;
            }

            // 自定义响应信息
            ByteBuf content = Unpooled.copiedBuffer("Hello world!", CharsetUtil.UTF_8);
            // HTTP 响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            // 设置HTTP响应header信息, 需要设置
            response.headers().set("content-type", "text/plain");
            response.headers().set("content-length", content.readableBytes());
            // 写入并刷入到客户端
            ctx.writeAndFlush(response);
        }
    }

    /*********************************回调方法, 可以不重写************************************/

    /**
     * 顺序1 处理器已添加 回调
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }

    /**
     * 顺序2 通道注册 回调
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    /**
     * 顺序3 通道连接 回调
     * 接下来是 自定义handler业务处理
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    /**
     * 顺序4 通道不活动 回调
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    /**
     * 顺序5 通道取消注册 回调
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    /**
     * 连接断开 回调
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved");
        super.handlerRemoved(ctx);
    }

    /**
     * 出现异常 回调
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }
}
