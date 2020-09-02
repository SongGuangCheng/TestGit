package org.song.network.javaio.io.io_01_bio.api.socket;

import org.junit.jupiter.api.Test;

public class Socket_01_structure {

    /**
     * 结构
     *
     * ServerSocket 服务端套接字 类
     * -    - ServerSocketAdaptor 类
     *
     * SocketOptions        接口
     * -    - SocketImpl    抽象类
     * -    -   - AbstractPlainSocketImpl   抽象类
     * -    -   -   - PlainSocketImpl       类
     * -    -   -   -   - SocksSocketImpl   类
     *
     * ServerSocket 包含一个 SocketImpl 的引用
     * SocketImpl 也包含这个 ServerSocket 的引用
     *
     * 详解
     *
     * ServerSocket 只是一个包装(代理)类,
     * SocketImpl 实现了这些方法的实现 (这也是传统 OIO 实现方法)
     * accept();
     * bind();
     * getInetAddress();
     * getLocalPort();
     * setOption();
     * 等
     *
     * ServerSocket 一个子类 ServerSocketAdaptor (Java NIO ServerSocketChannel 的主要实现)
     * ServerSocketAdaptor 包含一个 ServerSocketChannelImpl 的引用
     * ServerSocketAdaptor 实现了这些方法的实现
     * accept();
     * bind();
     * getInetAddress()
     * getLocalPort();
     * 等
     *
     *
     */
    @Test
    public void test_01_ServerSocket_structure() {

    }
}
