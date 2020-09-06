package org.song.network.javaio.io.io_01_bio.api.address;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

public class Socket_01_base {

    /**
     * ServerSocket: 服务端Socket
     * Socket: 客户端Socket
     * <p>
     * Socket: 四元组, 源IP:端口 = 目标IP:端口, 缺一不可
     */
    @Test
    public void test01() throws IOException {

        // 客户端 Socket
        Socket socket = null;

        socket.getInetAddress(); // 获取远程IP
        socket.getPort();// 获取远程端口

        socket.getLocalAddress();// 获取本地IP
        socket.getLocalPort();// 获取本地端口

        socket.getOutputStream();// 获取输出流
        socket.getInputStream();// 获取输入流

        socket.isClosed();// 是否已关闭
        socket.isConnected();// 是否已连接

        socket.isBound();// 是否绑定本地端口成功

    }
}
