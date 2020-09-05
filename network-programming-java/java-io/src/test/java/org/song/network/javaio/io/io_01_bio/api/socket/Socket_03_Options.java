package org.song.network.javaio.io.io_01_bio.api.socket;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Socket_03_Options {

    /**
     * SocketOptions 接口, 定义了 java 支持的所有 Socket 选项,
     * -    - SocketImpl 抽象类, 提供了 Socket 和 ServerSocket 的 Socket 选项支持
     * <p>
     * SocketImpl 支持的 Socket 选项
     * <p>
     * SO_KEEPALIVE // 空闲检查/空闲超时时间 设置
     * SO_SNDBUF // 发送 Socket 缓冲区大小
     * SO_RCVBUF // 接收 Socket 缓冲区大小
     * SO_REUSEADDR // Socket (即将关闭) 地址重启
     * SO_LINGER // Socket 关闭后, 超时传输数据时间
     * SO_LINGER // Socket 读取数据超时时间
     * TCP_NODELAY // 禁用 Negale 算法, 立即发送 Socket 缓冲区数据
     * SO_OOBINLINE // 是否支持发送1字节的TCP紧急数据
     * <p>
     * 设置方式 通过 Socket 提供的直接设置方法
     */
    @Test
    public void test_01_Socket() throws SocketException {

        Socket socket = new Socket();

        /**
         * 空闲检查/空闲超时时间 设置
         * keepAlive = SocketOptions.SO_KEEPALIVE
         * SocketImpl.setOption()/getOption()
         */
        socket.setKeepAlive(true);
        socket.getKeepAlive();

        /**
         * 发送 Socket 缓冲区大小
         * SendBufferSize = SO_SNDBUF
         * SocketImpl.setOption()/getOption()
         */
        socket.setSendBufferSize(1);
        socket.getSendBufferSize();

        /**
         * 接收 Socket 缓冲区大小
         * ReceiveBufferSize = SO_RCVBUF
         * SocketImpl.setOption()/getOption()
         */
        socket.setReceiveBufferSize(1);
        socket.getReceiveBufferSize();

        /**
         * Socket (即将关闭) 地址重启
         * ReuseAddress = SO_REUSEADDR
         * SocketImpl.setOption()/getOption()
         */
        socket.setReuseAddress(true);
        socket.getReuseAddress();

        /**
         * Socket 关闭后, 超时传输数据时间
         * SoLinger = SO_LINGER
         * SocketImpl.setOption()/getOption()
         */
        socket.setSoLinger(true, 0);
        socket.getSoLinger();

        /**
         * 禁用 Negale 算法, 立即发送 Socket 缓冲区数据
         * TcpNoDelay = TCP_NODELAY
         * SocketImpl.setOption()/getOption()
         */
        socket.setTcpNoDelay(true);
        socket.getTcpNoDelay();

        /**
         * Socket 读取数据超时时间
         * SoTimeout = SO_TIMEOUT
         * SocketImpl.setOption()/getOption()
         */
        socket.setSoTimeout(1);
        socket.getSoTimeout();

        /**
         * 表示是否支持发送1字节的TCP紧急数据
         * OOBInline = SO_OOBINLINE
         * SocketImpl.setOption()/getOption()
         */
        socket.setOOBInline(true);
        socket.getOOBInline();

    }

    /**
     *
     * 和客户端 Socket 相比, ServerSocket 支持的修改 Socket 选项比较少
     *
     */
    @Test
    public void test_02_ServerSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket();

        serverSocket.setReuseAddress(true);
        serverSocket.getReuseAddress();

        serverSocket.setReceiveBufferSize(1);
        serverSocket.getReceiveBufferSize();

        serverSocket.setSoTimeout(1);
        serverSocket.getSoTimeout();
    }
}
