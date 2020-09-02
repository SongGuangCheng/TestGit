package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Channel_04_SocketChannel {

    /**
     * 简单的 SocketChannel 和 ServerSocketChannel 通信
     * 阻塞通信 类似于 OIO
     */
    @Test
    public void test_01_SocketChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        // 默认阻塞模式
        socketChannel.configureBlocking(true);

        // 会阻塞在此
        socketChannel.connect(new InetSocketAddress(1000));

        ByteBuffer writeBuffer = ByteBuffer.wrap("hello server".getBytes());
        socketChannel.write(writeBuffer);

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(readBuffer);

        System.out.println(new String(readBuffer.array()));

        socketChannel.close();
    }

    /**
     * 简单的 SocketChannel 和 ServerSocketChannel 通信
     * 阻塞通信 类似于 OIO
     */
    @Test
    public void test_01_ServerSocketChannel() throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(1000));
        // 默认阻塞模式
        serverSocketChannel.configureBlocking(true);

        // 会阻塞在此
        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(readBuffer);
        System.out.println(new String(readBuffer.array()));

        socketChannel.write(ByteBuffer.wrap("hello client".getBytes()));

        socketChannel.close();
        serverSocketChannel.close();

    }

    /**
     * 简单的 SocketChannel 和 ServerSocketChannel 通信
     * 非阻塞通信
     */
    @Test
    public void test_02_SocketChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        socketChannel.connect(new InetSocketAddress(1000));

        // 此方法会阻塞到连接完成
        socketChannel.finishConnect();

        ByteBuffer writeBuffer = ByteBuffer.wrap("hello server".getBytes());
        socketChannel.write(writeBuffer);

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(readBuffer);

        System.out.println(new String(readBuffer.array()));
        socketChannel.close();

    }

    /**
     * 简单的 SocketChannel 和 ServerSocketChannel 通信
     * 非阻塞通信 类似于 non-blocking (unix 中的非阻塞)
     */
    @Test
    public void test_02_ServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(1000));

        while (true) {
            // 此方法不阻塞, 直接返回null, 除非有连接进入
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                continue;
            }
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(readBuffer);
            System.out.println(new String(readBuffer.array()));

            socketChannel.write(ByteBuffer.wrap("hello client".getBytes()));
            socketChannel.close();
            serverSocketChannel.close();
            break;
        }

    }

    /**
     * SocketChannel
     * 的
     * socketChannel.socket().connect() 和 socketChannel.connect()
     * 在建立连接方法上是一样的, 都是调用 SocketChannelImpl.connect()
     * 区别在于
     * Socket 使用流的方式进行数据传输
     * SocketChannel 使用通道缓冲区的方式进行数据传输
     */
    @Test
    public void test_03_SocketChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        /**
         * 方式 1.
         * 使用 socketChannel.socket() 返回的 Socket 进行连接操作
         */
        Socket socket = socketChannel.socket();
        socket.connect(new InetSocketAddress(1000));
        // SocketAdaptor
        System.out.println(socket.getClass());

        /**
         * 方式 2.
         */
        socketChannel.connect(new InetSocketAddress(1000));
        socketChannel.finishConnect();

    }

    /**
     * ServerSocketChannel
     * 的
     * serverSocketChannel.socket() 和 serverSocketChannel.bind
     * 在绑定端口方法上是相同的, 都是 ServerSocketChannelImpl.bind()
     * 区别在于
     * 1.
     * ServerSocket.accept() 属于 OIO 阻塞方式,
     * serverSocketChannel.accept() 属于 NIO 非阻塞方式
     * 2.
     * ServerSocket.accept() 返回 ServerSocket 使用流进行数据传输
     * serverSocketChannel.accept() 返回 SocketChannel 使用通道缓冲区进行数据传输
     */
    @Test
    public void test_03_ServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        /**
         * 方式 1.
         * serverSocketChannel.socket() 返回的 ServerSocket 进行接收连接
         */
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(1000));
        // ServerSocketAdaptor
        System.out.println(serverSocket.getClass());
        Socket accept = serverSocket.accept();
        accept.getOutputStream().write(1);

        /**
         * 方式 2.
         */
        serverSocketChannel.bind(new InetSocketAddress(1000));
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.write(ByteBuffer.wrap("".getBytes()));
    }

}
