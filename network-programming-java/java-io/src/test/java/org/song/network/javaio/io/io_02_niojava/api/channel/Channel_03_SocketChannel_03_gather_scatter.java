package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Channel_03_SocketChannel_03_gather_scatter {

    /**
     * SocketChannel
     * 分散读与聚集写
     */
    @Test
    public void test_01_SocketChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);

        socketChannel.connect(new InetSocketAddress(1000));

        // 此方法会阻塞到连接完成
        socketChannel.finishConnect();

        ByteBuffer writeBuffer1 = ByteBuffer.wrap("hello server1".getBytes());
        ByteBuffer writeBuffer2 = ByteBuffer.wrap("hello server2".getBytes());
        socketChannel.write(new ByteBuffer[]{writeBuffer1, writeBuffer2});

        ByteBuffer readBuffer1 = ByteBuffer.allocate(16);
        ByteBuffer readBuffer2 = ByteBuffer.allocate(16);
        socketChannel.read(new ByteBuffer[]{readBuffer1, readBuffer2});

        System.out.println(new String(readBuffer1.array()));
        System.out.println(new String(readBuffer2.array()));
        socketChannel.close();
    }

    /**
     * ServerSocketChannel
     * 分散读与聚集写
     */
    @Test
    public void test_01_ServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(1000));

        while (true) {
            // 此方法不阻塞, 直接返回null, 除非有连接进入
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                continue;
            }
            ByteBuffer readBuffer1 = ByteBuffer.allocate(16);
            ByteBuffer readBuffer2 = ByteBuffer.allocate(16);
            socketChannel.read(new ByteBuffer[]{readBuffer1, readBuffer2});
            System.out.println(new String(readBuffer1.array()));
            System.out.println(new String(readBuffer2.array()));

            ByteBuffer writeBuffer1 = ByteBuffer.wrap("hello client1".getBytes());
            ByteBuffer writeBuffer2 = ByteBuffer.wrap("hello client2".getBytes());
            socketChannel.write(new ByteBuffer[]{writeBuffer1, writeBuffer2});
            socketChannel.close();
            serverSocketChannel.close();
            break;
        }
    }

}
