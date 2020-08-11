package org.song.network.nettydemo.demo.beginner.demo_01_http.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 自定义初始化器
 */
public class FirstServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 初始化管道
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * 1. 在整个链式处理中, 在最后添加 HttpServerCodec 处理器, 这是个HTTP协议处理器
         * 如果使用addLast
         * 请求数据的处理顺序就是从上到下的handler依次处理,
         * 响应数据的处理顺序反过来从下到上依次处理
         * 一般业务handler都在在最后
         * 2. 参数 name 可以不用指定(netty会默认指定), 名称可以自定义
         *
         * HttpServerCodec 组合了, HttpRequestDecoder  HttpResponseEncoder
         * HTTP请求响应的编解码处理器
         */
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("firstServerHandler", new FirstServerHandler());

    }
}
