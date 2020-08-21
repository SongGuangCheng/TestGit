package org.song.network.javaio.io.io_02_niojava.buffer;


import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @description:
 * @date: 2019年07月09日 14:40:30
 **/
public class BufferUpgradeTest {

    /**
     * 直接缓冲区与非直接缓冲区：
     * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
     * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
     */
    @Test
    public void direct() {
        //分配直接缓冲区
        ByteBuffer buf = ByteBuffer.allocateDirect(8);
        System.out.println(buf.isDirect());
    }

    /**
     * hasRemaining() 是否有剩余空间
     * remaining() 剩余空间大小
     */
    @Test
    public void remaining() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put("abcde".getBytes());
        Buffer_01.print(byteBuffer);
        /**
         * 是否存在剩余可寻去存取空间
         */
        System.out.println(byteBuffer.hasRemaining());
        /**
         * 剩余可寻去存取空间 的大小
         */
        System.out.println(byteBuffer.remaining());
    }

    /**
     * clear()
     * 初始化 buffer 状态 (初始化这几个参数: mark <= position <= limit <= capacity)
     * 数据不丢弃
     */
    @Test
    public void clear() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put("abcdefg".getBytes());
        Buffer_01.print(byteBuffer);
        /**
         * position = 0;
         * limit = capacity;
         * mark = -1;
         */
        byteBuffer.clear();
        Buffer_01.print(byteBuffer);
    }

    /**
     * rewind()
     * 可重新存取位置
     */
    @Test
    public void rewind() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put("abcdefg".getBytes());
        Buffer_01.print(byteBuffer);
        /**
         * 切换读取数据模式,
         * position = 0; position 位置归0, 可以重新头开始 存取数据
         * mark = -1; 清空标记
         */
        byteBuffer.rewind();
        Buffer_01.print(byteBuffer);

    }

    /**
     * flip()
     * 可重新存取位置 + 调整最大可用大小限制
     * 通常用于将缓冲区切换成读取模式(已经存入数据, 开始读取数据)
     */
    @Test
    public void flip() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put("abcdefg".getBytes());
        Buffer_01.print(byteBuffer);
        /**
         * 切换读取数据模式,
         * limit = position; 存取显示大小改为操作位置大小
         * position = 0; position 位置归0, 可以重新头开始 存取数据
         * mark = -1; 清空标记
         */
        byteBuffer.flip();
        Buffer_01.print(byteBuffer);
    }
}