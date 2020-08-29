package org.song.network.javaio.io.io_01_noneio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 对应 Linux网络编程 五种IO模型中的 NIO, 非阻塞IO(注意: 不是多路复用IO)
 *
 * 利用ServerSocketChannel 的非阻塞特性, 循环向系统内核发起询问, 是否有事件到达, 如果有就处理
 */
public class NoneIOTest_01 {

    private static int port = 10001;

    @Test
    public void client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", port));
        if (socketChannel.isConnected()) {
            ByteBuffer outBuffer = ByteBuffer.wrap("客户端发送请求: hello".getBytes());
            socketChannel.write(outBuffer);

            outBuffer.clear();
            socketChannel.read(outBuffer);
            System.out.println("来自服务器的响应: " + new String(outBuffer.array()));
        }
    }

    @Test
    public void server() throws IOException {

        // 获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 设置为非阻塞
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务端启动成功！");
        while (true) {
            SocketChannel accept = serverChannel.accept();
            if (accept == null) {
                continue;
            }
            System.out.println("连接成功！" + accept.getRemoteAddress());
            // 创建读取的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = accept.read(buffer);
            if (read > 0) {
                byte[] data = buffer.array();
                String msg = new String(data).trim();
                System.out.println("服务端收到信息事件: " + msg);
                //回写数据
                ByteBuffer outBuffer = ByteBuffer.wrap("服务端给客户端响应...".getBytes());
                accept.write(outBuffer);// 将消息回送给客户端
            }
        }
    }
}