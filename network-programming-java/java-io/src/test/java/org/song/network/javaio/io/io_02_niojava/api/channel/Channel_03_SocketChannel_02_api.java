package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Channel_03_SocketChannel_02_api {

    /**
     * SocketChannel
     */
    @Test
    public void test_02_client_init() throws IOException {

        // 1. 打开 / 新建一个 SocketChannel 实例
        SocketChannel socketChannel = SocketChannel.open();
        // socketChannel.bind(new InetSocketAddress(1000));
        // 1. 也可以打开的时候绑定地址
        // SocketChannel.open(new InetSocketAddress())


        // 2. 设置阻塞模式 (可选)
        socketChannel.configureBlocking(false);

        // 2. 设置 socket 选项(可选)
        // socketChannel.setOption();

        // 3. 开始连接
        socketChannel.connect(new InetSocketAddress(1000));
        // 3. 同步返回是否已连接 ?
        // socketChannel.isConnected();
        // 3. 阻塞返回连接已完成 ?
        // socketChannel.finishConnect();

        // 4. 写入数据 到 服务端
        socketChannel.write(ByteBuffer.wrap("".getBytes()));

        // 5. 读取数据 从 服务端
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        socketChannel.read(byteBuffer);

        // 6. 关闭通道
        socketChannel.close();
    }

    /**
     * ServerSocketChannel
     */
    @Test
    public void test_02_server_init() throws IOException {
        // 1. 打开一个通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(1000));
        // serverSocketChannel.bind(new InetSocketAddress(1000));

        // 2. 设置阻塞模式
        serverSocketChannel.configureBlocking(false);

        // 2. 设置 socket 选项
        // serverSocketChannel.setOption()

        // 3. 开始接收连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 4. 读取客户端传来的数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        socketChannel.read(byteBuffer);

        // 5. 向客户端发送数据
        socketChannel.write(ByteBuffer.wrap("aa".getBytes()));

        // 6. 关闭通道
        socketChannel.close();
        serverSocketChannel.close();

    }

}
