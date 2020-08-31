package org.song.network.javaio.io.io_02_niojava.api.buffer;


import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * Buffer 缓冲区 公共父类抽象类
 *
 * @description:
 * @date: 2019年07月09日 13:46:00
 **/
public class Buffer_03_access {

    /**
     * 缓冲区的使用
     * 修改和获取缓冲区内容 get() 和 put()
     */
    @Test
    public void access_put() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        BufferUtils.print("原始数据", buffer);

        /**
         * put() 在position位置, 存入一个元素
         * get() 和 put() 每操作一次, position位置都会后移一次
         *
         */
        byte b = 1;
        buffer.put(b);
        BufferUtils.print("put(byte)", buffer);

        buffer.clear();

        /**
         * put(array)
         * 如果是数组, 则position会向后移动 array.length 位
         */
        byte[] array = new byte[]{1, 2, 3, 4};
        buffer.put(array);
        BufferUtils.print("put(byte[])", buffer);

        buffer.clear();
        /**
         * put(index, byte)
         * 指定位置放入元素, position 不变
         */
        byte b3 = 3;
        buffer.put(5, b3);
        BufferUtils.print("put(idx, byte)", buffer);
    }

    @Test
    public void access_get() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        BufferUtils.print("原始数据", buffer);
        /**
         * get() 和 put() 每操作一次, position 位置都会后移一次
         */
        byte get = buffer.get();
        BufferUtils.print("get()", buffer);
        System.out.println("get() = " + get);

        buffer.clear();

        /**
         * get(index) 获取指定位置的元素, position不变
         */
        byte b1 = buffer.get(5);
        System.out.println("get(idx=5) = " + b1);
        BufferUtils.print("get(idx)", buffer);
    }

}