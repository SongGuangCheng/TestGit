package org.song.network.javaio.io.io_01_noneio;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 零拷贝发送 数据
 */
public class NoneIOTest_02 {

    private static int port = 10001;

    /**
     * 使用零拷贝方式传输文件
     *
     * @throws IOException
     */
    @Test
    public void client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(port));
        socketChannel.configureBlocking(true);

        FileChannel fileChannel = new FileInputStream("").getChannel();
        fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        fileChannel.close();
    }

    @Test
    public void server() throws IOException {
        // 获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket socket = serverChannel.socket();
        // 设置 关闭超时 可连接
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress(port));

        ByteBuffer allocate = ByteBuffer.allocate(1024);
        while (true) {
            /**
             * 如果非阻塞模式, 则立刻返回空
             * 如果是阻塞模式, 则会阻塞到有链接
             */
            SocketChannel accept = serverChannel.accept();
            int readCount = 0;
            while (readCount != -1) {
                try {
                    readCount = accept.read(allocate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 倒带, 读满了 重复读
                allocate.rewind();
            }
        }
    }
}