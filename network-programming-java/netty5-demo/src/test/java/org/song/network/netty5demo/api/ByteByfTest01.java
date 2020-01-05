package org.song.network.netty5demo.api;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ByteByfTest01 {

    /**
     * ByteBuf
     */
    public void byteBuf_read_test() throws IOException {

        // 顺序读
        ByteBuf byteBuf = null;

        // 从 readerIndex 开始, 获取字节, readerIndex +1
        byteBuf.readByte();
        // 从当前 ByteBuffer 中的数据读取指定长度到新创建的 ByteBuf 中,
        byteBuf.readBytes(1);

        ByteBuf dst = null;
        // 将当前数据读取到目标 ByteBuf中, 直到目标没有剩余空间可写, 如果目标比当前大, 则抛异常
        byteBuf.readBytes(dst);
        int dstIndex = 2, length = 2;
        // 将当前数据读取到目标 ByteBuf中, 从指定位置读取指定长度
        byteBuf.readBytes(dst, dstIndex, length);

        byte[] dstBytes = new byte[]{};
        // 将当前数据读取到目标 字节数组中, 直到目标没有剩余空间可写, 如果目标比当前大, 则抛异常
        byteBuf.readBytes(dstBytes);

        ByteBuffer dstByteBuffer = null;
        // 将当前数据读取到目标 字节缓冲区中, 直到缓冲区的limit ...
        byteBuf.readBytes(dstByteBuffer);

        OutputStream out = null;
        // 将当前数据读取到目标 输出流中, 直到指定的length ...
        byteBuf.readBytes(out, 4);

    }
    /**
     * ByteBuf
     */
    public void byteBuf_write_test() throws IOException {

        // 顺序写
        ByteBuf byteBuf = null;

        // 写入到当前 ByteBuffer, 将当前值
        byteBuf.writeByte(1);

        ByteBuf src = null;
        // 写入到当前 ByteBuffer, 如果目标比当前大, 则抛异常
        byteBuf.readBytes(src);
        int dstIndex = 2, length = 2;
        // 写入到当前 ByteBuffer, 从指定位置读取指定长度
        byteBuf.readBytes(src, dstIndex, length);

        byte[] srcBytes = new byte[]{};
        // 写入到当前 ByteBuffer, 直到目标没有剩余空间可写, 如果目标比当前大, 则抛异常
        byteBuf.writeBytes(srcBytes);

        ByteBuffer srcByteBuffer = null;
        // 写入到当前 ByteBuffer, 直到缓冲区的limit ...
        byteBuf.writeBytes(srcByteBuffer);

        InputStream in = null;
        // 写入到当前 ByteBuffer, 直到指定的length ...
        byteBuf.writeBytes(in, 4);

    }

    public void byteByf_index_test() {
        ByteBuf byteBuf = null;

        // read 的时候, 从 readerIndex 开始读取,
    }
}
