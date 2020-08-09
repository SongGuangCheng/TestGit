package org.song.network.nettydemo.demo.demo01.echo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 引导客户端
 * <p>
 * 引导客户端类似于引导服务器，不同的是，客户端是使
 * 用主机和端口参数来连接远程地址，也就是这里的 Echo 服务器的地址，而不是绑定到一个一直
 * 被监听的端口
 * <p>
 * 要点：
 * 为初始化客户端，创建了一个 Bootstrap 实例
 * 为进行事件处理分配了一个 NioEventLoopGroup 实例，其中事件处理包括创建新的
 * 连接以及处理入站和出站数据；
 * 为服务器连接创建了一个 InetSocketAddress 实例；
 * 当连接被建立时，一个 EchoClientHandler 实例会被安装到（该 Channel 的）
 * ChannelPipeline 中；
 * 在一切都设置完成后，调用 Bootstrap.connect()方法连接到远程节点；
 * 完成了客户端，你便可以着手构建并测试该系统了。
 */
public class EchoClient {
    private static final String host = "localhost";
    private static final int port = 9999;


    public static void main(String[] args) throws Exception {
        start();
    }

    public static void start() throws Exception {
        // 1.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 2. 创建 Bootstrap
            Bootstrap b = new Bootstrap();
            // 3. 指定 EventLoopGroup 以处理客户端事件；需要适用于 NIO 的实现
            b.group(group)
                    // 4. 适用于 NIO 传输的 Channel 类型
                    .channel(NioSocketChannel.class)
                    // 5. 设置服务器的InetSocketAddr-ess
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 5-2. 设置TCP连接
//                    .option(ChannelOption.TCP_NODELAY, true)

                    // 6. 在创建Channel时， 向 ChannelPipeline 中添加一个 Echo-ClientHandler 实例
                    // 自定义初始化器
                    .handler(new EchoClientInitializer());

            //连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync();
            //阻塞，直到 Channel 关闭
            f.channel().closeFuture().sync();
        } finally {
            //关闭线程池并且释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
