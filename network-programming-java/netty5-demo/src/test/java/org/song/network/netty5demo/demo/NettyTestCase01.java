package org.song.network.netty5demo.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.ReferenceCountUtil;
import org.junit.jupiter.api.Test;
import org.song.network.netty5demo.demo.serial.MarshallingCodeCFactory;

public class NettyTestCase01 {


    private static int port = 10001;

    /**
     * 使用Netty 构建服务端, 相比较NIO 更加简单
     */
    @Test
    public void server() throws InterruptedException {

        // 服务端引导
        ServerBootstrap bootstrap = new ServerBootstrap();

        // 线程组 处理服务器端接受客户端连接的
        EventLoopGroup pGroup = new NioEventLoopGroup();
        // 线程组 进行网络通信/网络读写
        EventLoopGroup cGroup = new NioEventLoopGroup();

        bootstrap.group(pGroup, cGroup);
        bootstrap
                // 设置TCP 协议, 对应NIO中ServerSocketChannel
                .channel(NioServerSocketChannel.class)
                // 设置处理器.类似于Reactor中的handler
                .childHandler(new ChannelInitializer() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {

                        // 添加序列化工具
                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());

                    }
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channelActive");
                    }

                    /**
                     * 读数据
                     *
                     * @param ctx
                     * @param msg
                     * @throws Exception
                     */
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        BodyDTO bodyDTO = (BodyDTO) msg;
                        System.out.println("Server : " + bodyDTO.getId() + ", " + bodyDTO.getName() + ", " + bodyDTO.getMsg());
                        BodyDTO response = new BodyDTO();
                        response.setId(bodyDTO.getId());
                        response.setName("response" + bodyDTO.getId());
                        response.setMsg("响应内容" + bodyDTO.getId());
                        ctx.writeAndFlush(response);//.addListener(ChannelFutureListener.CLOSE);
                    }

                    /**
                     * 数据读取完成
                     *
                     * @param ctx
                     * @throws Exception
                     */
                    @Override
                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channelReadComplete");
                    }

                    /**
                     * 连接失败读数据失败 抛异常
                     *
                     * @param ctx
                     * @param cause
                     * @throws Exception
                     */
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        System.out.println("exceptionCaught");
                        ctx.close();
                    }
                });

        // 第4步, 绑定端口, 异步
        ChannelFuture cf = bootstrap.bind(port).sync();
        System.out.println("start ...");

        // 第5步, 等待关闭, 功能是阻塞程序 不让其停止
        cf.channel().closeFuture().sync();
        pGroup.shutdownGracefully();
        cGroup.shutdownGracefully();
    }


    @Test
    public void client() throws InterruptedException {
        ChannelFuture cf;
        // 第1步, 一个线程组
        EventLoopGroup group = new NioEventLoopGroup();
        // 第2步, 创建客户端Bootstrap
        Bootstrap b = new Bootstrap();
        // 绑定线程组
        b.group(group)
                // NIO模式
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))

                // 第3步, 客户端的handler
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {

                        // 添加序列化工具
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());


                        //超时handler（当服务器端与客户端在指定时间以上没有任何进行通信，则会关闭响应的通道，主要为减小服务端资源占用）
                        sc.pipeline().addLast(new ReadTimeoutHandler(5));

                        // 业务处理器
                        sc.pipeline().addLast(new ChannelHandlerAdapter() {

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                try {
                                    BodyDTO resp = (BodyDTO) msg;
                                    System.out.println("Client : " + resp.getId() + ", " + resp.getName() + ", " + resp.getMsg());
                                } finally {
                                    ReferenceCountUtil.release(msg);
                                }
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                ctx.close();
                            }
                        });
                    }
                });

        // 第4步, 绑定端口, 异步
        cf = b.connect("127.0.0.1", port).sync();


        BodyDTO request = new BodyDTO();
        int i = 1;
        request.setId("" + i);
        request.setName("pro" + i);
        request.setMsg("数据信息" + i);
        cf.channel().writeAndFlush(request);


        cf.channel().closeFuture().sync();
    }
}
