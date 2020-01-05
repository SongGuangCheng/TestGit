package org.song.network.nettydemo.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 引导服务器
 * <p>
 * 所有的 Netty 服务器都需要以下两部分。
 * 1. 至少一个 ChannelHandler — 该组件实现了服务器对从客户端接收的数据的处理，即
 * 它的业务逻辑。
 * 2. 引导 — 这是配置服务器的启动代码。至少，它会将服务器绑定到它要监听连接请求的
 * 端口上。
 *
 * 服务器的主要代码组件
 * EchoServerHandler 实现了业务逻辑；
 * main()方法引导了服务器；
 * 引导过程中所需要的步骤如下：
 * 创建一个 ServerBootstrap 的实例以引导和绑定服务器；
 * 创建并分配一个 NioEventLoopGroup 实例以进行事件的处理，如接受新连接以及读/
 * 写数据；
 * 指定服务器绑定的本地的 InetSocketAddress；
 * 使用一个 EchoServerHandler 的实例初始化每一个新的 Channel；
 * 调用 ServerBootstrap.bind()方法以绑定服务器。
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println(
//                    "Usage: " + EchoServer.class.getSimpleName() +
//                            " <port>");
//        }
        //设置端口值（如果端口参数的格式不正确，则抛出一个NumberFormatException）
//        int port = Integer.parseInt(args[0]);
        //调用服务器的 start()方法
        int port = 9999;
        new EchoServer(port).start();
    }

    /**
     * new ServerBootstrap()，你创建了一个ServerBootstrap 实例。因为你正在使用的是NIO 传输，所以你指定了 NioEventLoopGroup
     * new EchoServerHandler() 来接受和处理新的连接，并且将 Channel 的类型指定为 NioServer-
     * SocketChannel .channel(NioServerSocketChannel.class)。
     * 在此之后，你将本地地址设置为一个具有选定端口的 InetSocket-Address 。
     * 服务器将绑定到这个地址以监听新的连接请求。在 处，你使用了一个特殊的类——ChannelInitializer。这是关键。
     * 当一个新的连接被接受时，一个新的子 Channel 将会被创建，而 ChannelInitializer 将会把一个你的
     * EchoServerHandler 的实例添加到该 Channel 的 ChannelPipeline 中。正如我们之前所
     * 解释的，这个 ChannelHandler 将会收到有关入站消息的通知。
     * 虽然 NIO 是可伸缩的，但是其适当的尤其是关于多线程处理的配置并不简单。Netty 的设计
     * 封装了大部分的复杂性，而且我们将在第 3 章中对相关的抽象（EventLoopGroup、Socket-
     * Channel 和 ChannelInitializer）进行详细的讨论。
     * 接下来你绑定了服务器 ，并等待绑定完成。（对 sync()方法的调用将导致当前 Thread
     * 阻塞，一直到绑定操作完成为止）。在 处，该应用程序将会阻塞等待直到服务器的 Channel
     * 关闭（因为你在 Channel 的 CloseFuture 上调用了 sync()方法）。然后，你将可以关闭
     * EventLoopGroup，并释放所有的资源，包括所有被创建的线程
     *
     * @throws Exception
     */
    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //创建Event-LoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建Server-Bootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    //指定所使用的 NIO 传输 Channel
                    .channel(NioServerSocketChannel.class)
                    //使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    //添加一个EchoServer-Handler 到子Channel的 ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            //EchoServerHandler被标注为@Shareable，所以我们可以总是使用同样的实例
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            //异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            //获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            //关闭 EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
