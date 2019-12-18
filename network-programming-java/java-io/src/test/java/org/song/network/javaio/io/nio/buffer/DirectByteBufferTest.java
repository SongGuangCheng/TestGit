package org.song.network.javaio.io.nio.buffer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 直接缓冲区
 * 公共接口 DirectBuffer
 */
public class DirectByteBufferTest {


    /**
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
    public void test1() {
        // 2. 方式2 直接分配大小, 创建直接缓冲区
        /**
         * 底层调用的的是 Unsafe 的 native long allocateMemory()
         */
        ByteBuffer allocateDirectBuffer = ByteBuffer.allocateDirect(16);

    }

    /**
     * MappedByteBuffer 直接字节缓冲区, 内存映射方式实现
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
    public void test2() throws IOException {

        /**
         * 创建方式
         */
//        MappedByteBuffer mappedByteBuffer = Files.map(new File(""));
//        MappedByteBuffer mappedByteBufferReadWrite = Files.map(new File(""), FileChannel.MapMode.READ_WRITE);

    }


}
