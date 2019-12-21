package org.song.network.javaio.io.nio.buffer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * NIO 零拷贝方式
 * 操作系统支持
 * DirectByteBuffer: 直接缓冲区, 堆外内存, 和系统内核缓冲区类似
 * 2. fileChannel.transferTo 和 fileChannel.transferFrom: 调用操作系统的 sendfile
 * 3. fileChannel.map: 调用操作系统的 mmap
 * <p>
 * java优化
 * 1. scattering 和 gathering: 缓冲区的分散和聚集
 */
public class NIOZeroCopyTest {


    /**
     * 操作系统实现
     * <p>
     * DirectByteBuffer 直接缓冲区 使用的是堆外内存
     * 通过 -XX:MaxDirectMemorySize 来指定最大的堆外内存大小, 当未设置时默认同 -Xmx(最大堆内存) 的大小
     * <p>
     * DirectByteBuffer 继承 MappedByteBuffer 实现 DirectBuffer
     * <p>
     * 使用 Bits.reserveMemory(size, cap) 内存申请
     * 释放的方式有两种
     * -　　1.ReferenceHandler 线程会自动检查有无被回收的 DirectByteBuffer, 如果有则执行Cleaner.clean方法释放其对应的直接内存
     * -　　2.通过调用 SharedSecrets.getJavaLangRefAccess() 方法来释放内存，具体见Reference类代码分析。
     */
    @Test
    public void directBuffer_OS() {
        // 2. 方式2 直接分配大小, 创建直接缓冲区
        /**
         * 底层调用的的是 Unsafe 的 native long allocateMemory()
         */
        ByteBuffer allocateDirectBuffer = ByteBuffer.allocateDirect(16);

    }

    /**
     * 操作系统实现
     * <p>
     * MappedByteBuffer 直接字节缓冲区, 内存映射方式实现 mmap
     * <p>
     * DirectByteBuffer 继承 MappedByteBuffer 实现 DirectBuffer
     * -                    MappedByteBuffer 继承 ByteBuffer
     * <p>
     * 直接字节缓冲器，其内容是文件的存储器映射区域。
     * 通过FileChannel.map() 方法创建
     * <p>
     * 映射字节缓冲区及其表示的文件映射在缓冲区本身被垃圾回收之前保持有效。
     * <p>
     * 映射字节缓冲区的内容可以随时更改
     * <p>
     * 映射字节缓冲区的全部或部分可能在任何时候变得无法访问，例如映射文件被截断。 访问映射字节缓冲区的不可访问区域的尝试不会更改缓冲区的内容，并将导致在访问时或稍后的时候抛出未指定的异常。 因此，强烈建议您采取适当的预防措施，以避免该程序或同时运行的程序对映射文件进行操作，但读取或写入文件的内容除外。
     * <p>
     * 映射的字节缓冲区的行为与普通的直接字节缓冲区不同。
     */
    @Test
    public void mappedByteBuffer_OS() throws IOException {

        /**
         * 创建方式
         */
//        MappedByteBuffer mappedByteBuffer = Files.map(new File(""));

        // linux mmap 零拷贝方式
        // 1.
        // FileChannel fileChannel = FileChannel.open(Paths.get("xxx.txt"), null);
        // 2.
        RandomAccessFile readFile = new RandomAccessFile("xxx.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();

        // 获取 MappedByteBuffer
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        // linux sendfile 零拷贝方式
        RandomAccessFile writeFile = new RandomAccessFile("xxx.txt", "rw");
        FileChannel fileChannelWrite = writeFile.getChannel();
        // 写入
        fileChannel.transferTo(0, 5, fileChannelWrite);
        // 读取
        fileChannel.transferFrom(fileChannel, 0, 5);
    }

    /**
     * 分散
     * java 自己实现 将一个缓冲区分散成多个缓冲区, 内部不复制
     */
    @Test
    public void scattering_JAVA() throws IOException {
        ByteBuffer header = ByteBuffer.allocate(8);
        ByteBuffer body = ByteBuffer.allocate(8);
        ByteBuffer[] bufferArray = {header, body};

        SocketChannel socketChannel = ServerSocketChannel.open().accept();
        // channel 会将socket通道中的数据, 依次分散的写入到多个缓冲区中
        long read = socketChannel.read(bufferArray);
    }

    /**
     * 聚集
     * java 自己实现 将多个缓冲区聚集成一个缓冲区 发送
     *
     * @throws IOException
     */
    @Test
    public void gathering_JAVA() throws IOException {
        ByteBuffer header = ByteBuffer.allocate(8);
        ByteBuffer body = ByteBuffer.allocate(8);
        ByteBuffer[] bufferArray = {header, body};

        SocketChannel socketChannel = ServerSocketChannel.open().accept();
        // channel 会向socket同道中依次写入多个缓冲区中的数据
        socketChannel.write(bufferArray);
    }


}
