package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

public class Channel_03_SocketChannel_01_Structure02 {

    /**
     * SocketChannel 中的 Socket
     *
     * Socket               OIO 中的 Socket
     * -    - SocketAdapter NIO 中的 Socket
     *
     * ServerSocket                 OIO 中的 ServerSocket
     * -    - ServerSocketAdapter   NIO 中的 ServerSocket
     *
     * 本质上, NIO 中的 IO(包括 Socket), 是一套全新的 IO, 其底层系统调用完全不一样,
     * 不过Socket在API设计上遵循了原有的结构, 在使用的时候方式类似, 新的 Socket 继承原有的 Socket
     *
     */
    @Test
    public void test_01_Socket() {

    }

    /**
     * SocketAdapter
     * 包含了一个 SocketChannelImpl 引用, 很多方法实际有此类实现
     *
     * 通过 SocketChannel.socket() 获取 NIO Socket 实例
     *
     * SocketAdapter 重写了 Socket 的方法, 几乎重写了所有的核心方法, 而他们的具体实现都指向 NIO 的 SocketChannelImpl
     * connect();
     * bind();
     * getInputStream();
     * getOutputStream();
     * setXXXOption();
     * getXXXOption();
     *
     * SocketChannelImpl
     * 是 SocketChannel 的具体实现, 未开源
     *
     */
    @Test
    public void test_01_SocketChannel() {

    }

    /**
     * ServerSocketAdapter
     * 包含了一个 ServerSocketChannelImpl 引用, 很多方法实际有此类实现
     *
     * 通过 ServerSocketChannel.socket() 获取 NIO Socket 实例
     *
     * ServerSocketAdapter 重写了 ServerSocket 的方法, 几乎重写了所有的核心方法, 而他们的具体实现都指向 NIO 的 ServerSocketChannelImpl
     * accept();
     * bind();
     * setXXXOption();
     * getXXXOption();
     *
     * ServerSocketChannelImpl
     * 是 ServerSocketChannel 的具体实现, 未开源
     *
     */
    @Test
    public void test_01_ServerSocketChannel() {

    }

}
