package org.song.network.javaio.io.io_01_bio.api.socket;

import org.junit.jupiter.api.Test;

public class Socket_02_ServerSocket_base {

    /**
     * 结构
     *
     * ServerSocket 服务端套接字 类
     * -    - ServerSocketAdaptor 类
     *
     * SocketOptions        接口
     * -    - SocketImpl    抽象类
     * -    -   - AbstractPlainSocketImpl   抽象类
     * -    -   -   - DualStackPlainSocketImpl       类
     * -    -   -   - PlainSocketImpl       类 (包含一个 AbstractPlainSocketImpl 引用 默认是 DualStackPlainSocketImpl 实例, TwoStacksPlainSocketImpl)
     * -    -   -   -   - SocksSocketImpl   类
     */

    /**
     * ServerSocket bind 方法
     *
     * ServerSocket.bind()
     * ServerSocket.getImpl().bind()
     *
     * SocksSocketImpl.bind() = PlainSocketImpl.bind()
     *
     * DualStackPlainSocketImpl.bind() = AbstractPlainSocketImpl.bind()
     * DualStackPlainSocketImpl.socketBind()
     * native DualStackPlainSocketImpl.bind0()
     */
    @Test
    public void test_01_ServerSocket_bind() {

    }

    /**
     * ServerSocket accept 方法
     *
     * ServerSocket.accept()
     * ServerSocket.implAccept()
     * ServerSocket.getImpl().accept()
     *
     * SocksSocketImpl.accept() = PlainSocketImpl.accept()
     *
     * DualStackPlainSocketImpl.accept() = AbstractPlainSocketImpl.accept()
     * DualStackPlainSocketImpl.socketAccept()
     * native DualStackPlainSocketImpl.accept0() // 开始阻塞
     *
     */
    @Test
    public void test_01_ServerSocket_accept() {

    }
}
