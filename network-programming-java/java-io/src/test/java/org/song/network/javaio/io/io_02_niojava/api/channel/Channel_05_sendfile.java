package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Channel_05_sendfile {

    /**
     * FileChannel 通道
     * 源文件通道 transferTo 目标可写通道
     * 目标文件通道 transferFrom 源可读通道
     */
    @Test
    public void test_01_transferTo() throws IOException {

        FileChannel fileChannelSrc = FileChannel.open(Paths.get("file\\test.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ);

        FileChannel fileChannelTarget = FileChannel.open(Paths.get("file\\test-copy.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 将原通道数据直接 写入 目标通道
        fileChannelSrc.transferTo(0, fileChannelSrc.size(), fileChannelTarget);
    }

    @Test
    public void test_01_transferFrom() throws IOException {

        FileChannel fileChannelSrc = FileChannel.open(Paths.get("file\\test.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ);

        FileChannel fileChannelTarget = FileChannel.open(Paths.get("file\\test-copy.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 从原通道数据直接 写入 目标通道
        fileChannelTarget.transferFrom(fileChannelSrc, 0, fileChannelSrc.size());
    }

    /**
     * 源文件通道 transferTo 目标可写通道
     * 目标文件通道 transferFrom 源可读通道
     */
    @Test
    public void test_02_SocketChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(1000));
        socketChannel.finishConnect();

        FileChannel fileChannelSrc = FileChannel.open(Paths.get("file\\test.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ);
        fileChannelSrc.transferTo(0, fileChannelSrc.size(), socketChannel);

        FileChannel fileChannelTarget = FileChannel.open(Paths.get("file\\test-from-server.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
        fileChannelTarget.transferFrom(socketChannel, 0, 8);
    }

    /**
     *
     */
    @Test
    public void test_02_ServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(1000));
        SocketChannel socketChannel = serverSocketChannel.accept();

        FileChannel fileChannelTarget = FileChannel.open(Paths.get("file\\test-from-client.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
        fileChannelTarget.transferFrom(socketChannel, 0, 8);

        FileChannel fileChannelSrc = FileChannel.open(Paths.get("file\\test.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ);
        fileChannelSrc.transferTo(0, fileChannelSrc.size(), socketChannel);

    }

}
