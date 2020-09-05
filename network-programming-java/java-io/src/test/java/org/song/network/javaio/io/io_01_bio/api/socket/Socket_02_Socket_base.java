package org.song.network.javaio.io.io_01_bio.api.socket;

import org.junit.jupiter.api.Test;

public class Socket_02_Socket_base {

    /**
     * 结构
     *
     * Socket 服务端套接字 类
     * -    - ServerSocketAdaptor 类
     *
     * SocketOptions        接口
     * -    - SocketImpl    抽象类
     * -    -   - AbstractPlainSocketImpl   抽象类
     * -    -   -   - DualStackPlainSocketImpl       类
     * -    -   -   - PlainSocketImpl       类 (包含一个 AbstractPlainSocketImpl 引用 默认是 DualStackPlainSocketImpl 实例, TwoStacksPlainSocketImpl)
     * -    -   -   -   - SocksSocketImpl   类
     *
     * 和 ServerSocket 类似,
     */

    /**
     * Socket new Socket(ip, port) 方法
     * 带地址的 构造方法, 会调用 connect()
     */
    @Test
    public void test_01_Socket() {

    }

    /**
     * Socket connect() 方法
     *
     * Socket.connect()
     * SocksSocketImpl.connect()
     *
     * DualStackPlainSocketImpl.connect()
     *
     * AbstractPlainSocketImpl.connect()
     * AbstractPlainSocketImpl.connectToAddress()
     * AbstractPlainSocketImpl.doConnect()
     *
     * DualStackPlainSocketImpl.socketConnect()
     * native DualStackPlainSocketImpl.connect0()
     */
    @Test
    public void test_01_Socket_connect() {

    }
}
