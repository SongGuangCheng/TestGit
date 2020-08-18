package org.song.network.nettydemo.demo.beginner.beginner_02_serialize.demo_01_protobuf.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.song.network.nettydemo.demo.beginner.beginner_02_serialize.demo_01_protobuf.protobuf.Data;

import java.util.UUID;

public class ProtobufClientHandler extends SimpleChannelInboundHandler<Data.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Data.Student msg) throws Exception {
        System.out.println("客户端收到消息: " + msg.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("已连接: " + ctx.channel().remoteAddress());
//        Data.Student student = Data.Student.newBuilder()
//                .setEmail("email")
//                .setName("小六" + UUID.randomUUID().toString().substring(0, 4))
//                .setId(Math.round(10))
//                .build();
//        System.out.println("向服务端发送消息: " + student.toString());
//        ctx.channel().writeAndFlush(student);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常: "+ ctx.channel().remoteAddress());
        cause.printStackTrace();
        ctx.channel().close();
    }
}
