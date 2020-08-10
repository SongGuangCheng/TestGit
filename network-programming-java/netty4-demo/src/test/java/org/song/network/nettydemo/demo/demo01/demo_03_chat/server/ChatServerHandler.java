package org.song.network.nettydemo.demo.demo01.demo_03_chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * netty 提供的 用于保存 channel工具
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(cg -> {
            if (cg != channel) {
                // 给别人发送, 别人看到的
                cg.writeAndFlush(channel.remoteAddress() + "发送消息: " + msg + "\r\n");
            } else {
                // 给自己发送, 自己看到的
                cg.writeAndFlush("[自己]: " + msg + "\r\n");
            }
        });

    }

    /***************************回调处理**************************/


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 客户端已和服务端 建立连接
        // 获取已建立连接的通道
        Channel channel = ctx.channel();

        // 广播给其他客户端
        // ChannelGroup 会自动遍历所有的channel同时执行此方法
        channelGroup.writeAndFlush("[服务器]" + channel.remoteAddress() + "加入\r\n");

        // 添加通道到全局 集合中
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 连接已断开
        // 获取已建立连接的通道
        Channel channel = ctx.channel();

        // 广播给其他客户端
        channelGroup.writeAndFlush("[服务器]" + channel.remoteAddress() + "离开\r\n");

        // netty 会自动移除断开的channel, 可以不用手动调用
        // channelGroup.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 获取已建立连接的通道
        Channel channel = ctx.channel();

        System.out.println("[服务器]" + channel.remoteAddress() + "已上线\r\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 获取已建立连接的通道
        Channel channel = ctx.channel();

        System.out.println("[服务器]" + channel.remoteAddress() + "已下线\r\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
