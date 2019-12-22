package org.song.network.nettydemo.api;

import io.netty.buffer.*;
import org.junit.jupiter.api.Test;

/**
 * Netty 中的零拷贝技术
 */
public class NettyZeroCopyTest {

    /**
     * 聚集成缓冲区
     * 多个缓冲区聚集成一个缓冲区, 不进行数据拷贝, 可能存在直接缓冲区和非直接缓冲区同时被聚集在一起
     */
    @Test
    public void composite_test() {
        // Netty 3.x CompositeChannelBuffer
//        CompositeChannelBuffer

        // Netty 4.x 5.x CompositeByteBuf

        // 聚集 替换下面的方式
        CompositeByteBuf byteBufs = new CompositeByteBuf(ByteBufAllocator.DEFAULT, true, 1);

        ByteBuf byteBuf = null;
        byteBufs.addComponent(byteBuf);


//        // 传统方式聚集, 会出现内存复制(被CompositeByteBuf 方式 替换)
//        ByteBuf header = null;
//        ByteBuf body = null;
//        ByteBuf allBuf = Unpooled.buffer(header.readableBytes() + body.readableBytes());
//        allBuf.writeBytes(header);
//        allBuf.writeBytes(body);

    }

    /**
     * 数组转成缓冲区
     * 没有数据拷贝
     * 通过 Unpooled.wrappedBuffer 方法来将 bytes 包装成为一个 UnpooledHeapByteBuf 对象
     */
    @Test
    public void wrapped_test() {
        byte[] bytes = new byte[]{};
        byte[] bytes2 = new byte[]{};

        // 将数组转成缓冲区, 没有数据拷贝
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        ByteBuf result = Unpooled.wrappedBuffer(bytes, bytes2);

        ByteBuf byteBuf1 = null;
        ByteBuf byteBuf2 = null;
        ByteBuf result2 = Unpooled.wrappedBuffer(byteBuf1);
        ByteBuf result3 = Unpooled.wrappedBuffer(byteBuf1, byteBuf2);

        // 传统范式, 有数据拷贝
//        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(bytes);
    }

    /**
     * 缓冲区分片
     * 用 slice 方法产生 header 和 body 的过程是没有拷贝操作的
     */
    @Test
    public void slice_test() {
        ByteBuf byteBuf = null;
        ByteBuf header = byteBuf.slice(0, 5);
        ByteBuf body = byteBuf.slice(5, 10);
    }

    /**
     * netty 内存池化
     */
    @Test
    public void pooled_test() {

        // 池化缓冲区
        PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator();
//        pooledByteBufAllocator.directArenas()

        // 未池化缓冲区
        UnpooledByteBufAllocator unpooledByteBufAllocator = new UnpooledByteBufAllocator(false);

    }

}
