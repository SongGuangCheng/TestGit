package org.song.network.javaio.io.io_02_niojava.api.buffer;


import org.junit.jupiter.api.Test;

import java.nio.*;

/**
 * Buffer 缓冲区 公共父类抽象类
 *
 * @description:
 * @date: 2019年07月09日 13:46:00
 **/
public class Buffer_01_base {

    /**
     * Buffer 抽象类, 为所有基本类型都有一个实现类, boolean类型除外,
     * 其中最常用的是 ByteBuffer
     */
    @Test
    public void test_01_base(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        ShortBuffer shortBuffer = ShortBuffer.allocate(4);
        CharBuffer charBuffer = CharBuffer.allocate(4);
        FloatBuffer floatBuffer = FloatBuffer.allocate(4);
        IntBuffer intBuffer = IntBuffer.allocate(4);
        DoubleBuffer doubleBuffer = DoubleBuffer.allocate(4);
        LongBuffer longBuffer = LongBuffer.allocate(4);

    }

    /**
     * Buffer 抽象类
     * -    - ByteBuffer:               省略其他基本类型(ShortBuffer/CharBuffer/FloatBuffer/IntBuffer/DoubleBuffer/LongBuffer)
     * -    -   - HeapByteBuffer:       堆内存缓冲区
     * -    -   -   - HeapByteBufferR:  只读 堆内存缓冲区
     * DirectBuffer 接口, 直接内存缓冲区
     * -    -   - MappedByteBuffer:             内存映射文件 缓冲区
     * -    -   -   - DirectByteBuffer:         直接内存缓冲区
     * -    -   -   -   - DirectByteBufferR:    只读 直接内存缓冲区
     */
    @Test
    public void test_01_structure(){

    }

    /**
     * 创建缓冲区方式
     */
    @Test
    public void test_01_init() {
        // 1. 方式1 直接分配大小
        ByteBuffer allocateBuffer = ByteBuffer.allocate(16);

        // 2. 方式2 直接分配大小, 创建直接缓冲区
        ByteBuffer allocateDirectBuffer = ByteBuffer.allocateDirect(16);

        // 3. 方式3 使用数组初始化 创建缓冲区
        // 注意: wrap方法修改缓冲区对象的时候，数组本身也会跟着发生变化, 他们拥有同一个数组对象
        byte[] array = new byte[]{1, 2, 3, 4};
        ByteBuffer wrapBuffer = ByteBuffer.wrap(array);

    }
}