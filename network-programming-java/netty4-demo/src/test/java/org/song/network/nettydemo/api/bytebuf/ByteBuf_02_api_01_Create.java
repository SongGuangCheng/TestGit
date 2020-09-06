package org.song.network.nettydemo.api.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

public class ByteBuf_02_api_01_Create {

    /**
     * Unpooled 非池化的 ByteBuf 工具类
     * netty 并不建议使用 ByteBuf 构造器来创建实例, 而是间通过使用 Unpooled 工具
     *
     * 根据 ByteBuf 类型, 创建方法分为3类
     * 1. 创建一个新的缓冲区
     * 2. 通过包装创建一个缓冲区
     * 3. 通过复制创建一个缓冲区
     */
    public void test_01_Create_Unpooled() {

        /**
         * 1. 创建一个新的缓冲区
         */
        // 分配固定长度 堆 缓冲区
        ByteBuf buffer = Unpooled.buffer(16);
        // 分配固定长度 直接 缓冲区
        Unpooled.directBuffer(16);

        /**
         * 2. 通过包装创建一个缓冲区
         * 注意, 包装出来的缓冲区仅仅是一个视图, 原对象的改变, 会影响新的 缓冲区对象
         */
        // 包装一个 字节数组
        Unpooled.wrappedBuffer("".getBytes());
        // 包装一个 ByteBuf
        Unpooled.wrappedBuffer(Unpooled.buffer());
        // 包装一个 ByteBuffer
        Unpooled.wrappedBuffer(ByteBuffer.allocate(16));

        /**
         * 3. 通过复制创建一个缓冲区
         * 复制操作是深拷贝, 原对象的改变, 不会影响新的 缓冲区对象
         */
        // 复制一个 字节数组
        Unpooled.copiedBuffer("".getBytes());
        // 复制一个 ByteBuf
        Unpooled.copiedBuffer(Unpooled.buffer());
        // 复制一个 ByteBuffer
        Unpooled.copiedBuffer(ByteBuffer.allocate(16));

    }
}
