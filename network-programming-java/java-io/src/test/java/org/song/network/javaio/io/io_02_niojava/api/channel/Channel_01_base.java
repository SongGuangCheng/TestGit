package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

public class Channel_01_base {

    /**
     * Channel 通道
     * 一个 Channel 表示一个连接, 连接的双方可以是 硬件设备, 网络设备, 文件, 程序等
     * 定义了两个基本方法
     * isOpen() 连接是否开启
     * close(); 关闭一个连接
     * <p>
     * 其子类 包括两大类, 文件通道 FileChannel 和 网络通道 SocketChannel
     * Channel
     * -   - ReadableByteChannel 可读通道
     * -   - WritableByteChannel 可写通道
     * -   - InterruptibleChannel 可异步关闭和可中断通道
     * -   - NetworkChannel 可以连接到网络 Socket 的通道
     * -   - SelectableChannel 可以被 selector 选择的通道
     * -   -   - GatheringByteChannel 聚集写通道
     * -   -   - ScatteringByteChannel 分散读通道
     * -   -   -   - FileChannel 文件通道
     * -   -   -   - SocketChannel Socket通道
     * -   -   -   - ServerSocketChannel 服务端Socket通道
     * <p>
     * FileChannel 通道 和 SocketChannel/ServerSocketChannel 通道, 通过继承不同的父类, 拥有不同的特性
     */
    @Test
    public void test_01_structure() {

    }

}
