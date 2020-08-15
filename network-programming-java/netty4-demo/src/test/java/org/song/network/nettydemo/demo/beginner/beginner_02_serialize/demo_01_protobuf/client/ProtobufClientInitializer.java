package org.song.network.nettydemo.demo.beginner.beginner_02_serialize.demo_01_protobuf.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.song.network.nettydemo.demo.beginner.beginner_02_serialize.demo_01_protobuf.protobuf.Data;

public class ProtobufClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // 传输的对象
        pipeline.addLast(new ProtobufDecoder(Data.Student.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        // 自定义handler
        pipeline.addLast(new ProtobufClientHandler());

    }
}
