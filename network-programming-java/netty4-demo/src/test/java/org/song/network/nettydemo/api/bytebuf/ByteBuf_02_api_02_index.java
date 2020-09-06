package org.song.network.nettydemo.api.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

public class ByteBuf_02_api_02_index {

    /**
     *
     * ByteBuf 的5个索引
     * 0 <= readerIndex <= writerIndex <= capacity
     * markedReaderIndex / markedWriterIndex
     *
     */
    public void test_01_index() {
        ByteBuf buffer = Unpooled.buffer(16);

        // 读索引, 获取/设置 读索引
        int readerIndex = buffer.readerIndex();
        buffer.readerIndex(1);

        // 写索引, 获取/设置 写索引
        int writerIndex = buffer.writerIndex();
        buffer.writerIndex(1);

        // 读标记索引
        buffer.markReaderIndex();

        // 写标记索引
        buffer.markWriterIndex();
    }

    /**
     * readerIndex 默认 = 0
     * AbstractByteBuf 中 可对其进行修改的操作有
     * readerIndex(index); 设置新值
     * resetReaderIndex(); 设置新值 为读标记值
     * setBytes(); 设置新值 增加长度
     * clear(); 初始为0
     * discardReadBytes(); 废弃已读字节, 值为0
     * discardSomeReadBytes(); 废弃部分已读字节, 值为0
     * readByte(); 读取操作, 自增1
     * 等
     */
    public void test_02_ReaderIndex(){

    }

    /**
     * writerIndex 默认 = 0
     * AbstractByteBuf 中 可对其进行修改的操作有
     * writerIndex(index); 设置新值
     * resetWriterIndex(); 设置新值 为写标记值
     * getBytes(); 设置新值 增加长度
     * clear(); 初始为0
     * discardReadBytes(); 废弃已读字节, 值为0
     * discardSomeReadBytes(); 废弃部分已读字节, 值为0
     * writeZero(); 写入操作, 自增写入数
     */
    public void test_02_WriterIndex(){

    }

    /**
     * markedReaderIndex 默认 0
     * AbstractByteBuf 中 可对其进行修改的方法有
     * markReaderIndex(); 标记值为读索引值
     * discardReadBytes(); 废弃已读字节
     * discardSomeReadBytes(); 废弃部分已读字节
     *
     * markedWriterIndex 默认 0
     * AbstractByteBuf 中 可对其进行修改的方法有
     * markWriterIndex(); 标记值为写索引值
     * discardReadBytes(); 废弃已读字节
     * discardSomeReadBytes(); 废弃部分已读字节
     *
     */
    public void test_02_mark(){

    }
}
