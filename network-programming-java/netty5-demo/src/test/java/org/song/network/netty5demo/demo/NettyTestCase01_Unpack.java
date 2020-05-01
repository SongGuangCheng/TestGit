package org.song.network.netty5demo.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.jupiter.api.Test;

public class NettyTestCase01_Unpack {


    private static int port = 10001;

    @Test
    public void server() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup mGroup = new NioEventLoopGroup();
        EventLoopGroup sGroup = new NioEventLoopGroup();

        bootstrap.group(mGroup, sGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {

                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        super.channelRead(ctx, msg);
                    }



                });

        ChannelFuture channelFuture = bootstrap.bind("127.0.0.1", port).sync();
        channelFuture.channel().closeFuture().sync();
        mGroup.shutdownGracefully();
        sGroup.shutdownGracefully();
    }


    @Test
    public void client() throws InterruptedException {
    }
}
