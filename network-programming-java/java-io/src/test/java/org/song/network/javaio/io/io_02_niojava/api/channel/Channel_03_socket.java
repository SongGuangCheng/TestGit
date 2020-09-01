package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Channel_03_socket {

    /**
     * SocketChannel 和 ServerSocketChannel 都是套接字通道, 分别用于客户端和服务端
     * 他们的功能出了本类中定义的方法外, ServerSocketChannel 没有读写功能(没有继承读写通道)
     * <p>
     * 他们都继承了共同的父类
     * AbstractSelectableChannel
     * SelectableChannel
     * 等
     * <p>
     * AbstractSelectableChannel 抽象 可选择的通道
     * SelectableChannel 抽象 可选择的通道
     */
    public void test_01_selectable() {

    }

    /**
     * SelectableChannel 抽象 可选择的通道
     * 主要定义了多个抽象方法
     * <p>
     * provider();  获取默认的 SelectorProvider
     * validOps();  获取支持的事件类型
     * isRegistered();          是否已被注册
     * register();              注册
     * keyFor(Selector sel);    返回已注册的key
     * configureBlocking(boolean block); 设置阻塞模式
     * isBlocking();
     * blockingLock();
     */
    public void test_01_abs_selectable1() {

    }

    /**
     * AbstractSelectableChannel 抽象 可选择的通道
     * 为 SelectableChannel 提供了基本的方法实现, 不过依然是抽象的
     * 其中主要的方法有
     * register(); 注册通道到 selector 上
     * keyFor(); 返回已注册的 keys
     */
    public void test_01_abs_selectable2() {

    }

    /**
     * SocketChannel 客户端 套接字通道
     * 继承 AbstractSelectableChannel
     * 主要方法
     * open(); 打开通道
     * bind(); 绑定地址
     * connect(); 建立连接
     * finishConnect(); 连接是否完成
     * getRemoteAddress(); 获取远程地址
     * getLocalAddress(); 获取本地地址
     * <p>
     * read(); 读取通道数据
     * write(); 向通道写数据
     */
    @Test
    public void test_01_SocketChannel() {

    }

    /**
     * ServerSocketChannel 服务端 套接字通道
     * 继承 AbstractSelectableChannel
     * 主要方法
     * open(); 打开通道
     * bind(); 绑定地址
     * accept(); 接受事件
     * socket(); 获取已连接的客户端 socket
     * getLocalAddress(); 获取本地地址
     */
    @Test
    public void test_01_ServerSocketChannel() {

    }

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
        serverSocketChannel.bind(new InetSocketAddress(1000));

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
