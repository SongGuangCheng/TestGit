package org.song.network.nettydemo.api;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.jupiter.api.Test;

import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * @description:
 * @date: 2019年12月06日 15:19:05
 **/
public class NettyBase_01 {

    /**
     * netty 中抽象出 字节缓冲区, ByteBuf, 作为 java NIO 中 ByteBuffer 的拓展
     * ByteBuf
     * <p>
     * <p>
     * - |___________|________________|__________________|_ _ _ _ _ _ _ _ _ _|
     * - |__废弃字节__|可读下标________|可写下标___________|_可扩容字节_ _ _ _ _|
     * - |__________________capacity_____________________|
     * - |________________________maxCapacity______________ _ _ _ _ _ _ _ _ _|
     * <p>
     * Netty抽象出来的ByteBuf和JDK的ByteBuffer 的区别
     * <p>
     * 1. 扩容
     * -    ByteBuffer：ByteBuffer缓冲区的长度固定，分多了会浪费内存，分少了存放大的数据时会索引越界，
     * -    所以使用ByteBuffer时，为了解决这个问题，我们一般每次put操作时，都会对可用空间进行校检，
     * -   如果剩余空间不足，需要重新创建一个新的ByteBuffer，然后将旧的ByteBuffer复制到新的ByteBuffer中去。
     * <p>
     * -    ByteBuf：而ByteBuf则对其进行了改进，它会自动扩展，具体的做法是，写入数据时，会调用 ensureWritable 方法，传入我们需要写的字节长度，判断是否需要扩容：
     * 2. 位置指针
     * -    优化读写指针
     * <p>
     * <p>
     * ByteBuf 一些优点
     * 下面是一些ByteBuf API的优点：
     * 　　它可以被用户自定义的缓冲区类型扩展；
     * 　　通过内置的复合缓冲区类型实现了透明的零拷贝；
     * 　　容量可以按需增长（类似于 JDK 的 StringBuilder ）；
     * 　　在读和写这两种模式之间切换不需要调用ByteBuffer的flip()方法；
     * 　　读和写使用了不同的索引；
     * 　　支持方法的链式调用；
     * 　　支持引用计数；
     * 　　支持池化。
     * 　　使用不同的读索引和写索引来控制数据访问；
     * 　　readerIndex达到和writerIndex
     * <p>
     * 　　使用内存的不同方式——基于字节数组和直接缓冲区；
     * 　　通过CompositeByteBuf生成多个ByteBuf的聚合视图；
     * 　　数据访问方法——搜索、切片以及复制；
     * 　　随机访问索引 【0到capacity() - 1】
     * 　　顺序访问索引
     * 　　可丢弃字节 【discardReadBytes() clear()改变index值】
     * 　　可读字节【readBytes(ByteBuf dest) 】
     * 　　可写字节【writeBytes(ByteBuf dest);】
     * 　　索引管理【markReaderIndex()、markWriterIndex()、resetWriterIndex()和resetReaderIndex( readerIndex(int)或者writerIndex(int) 】
     * 　　查找操作【buf.indexOf(),forEachByte(ByteBufProcessor.FIND_NUL), int nullIndex = buf.forEachByte(ByteBufProcessor.FIND_NUL);int rIndex = buf.forEachByte(ByteBufProcessor.FIND_CR);】
     * 　　派生缓冲区【 返回新的buf,都具有 readIndex writeIndex markIndex
     * 　　buf.duplicate();
     * 　　ByteBuf rep = buf.copy();//创建副本
     * 　　buf.slice();//操作buf分段
     * 　　buf.slice(0, 5);//操作buf分段】
     * 　　读、写、获取和设置API；
     * 　　读/写操作【get()和set()操作，从给定的索引开始，并且保持索引不变；read()和write()操作，从给定的索引开始，并且会根据已经访问过的字节数对索引进行调整】
     * 　　ByteBufAllocator池化
     * 　　ByteBufAllocator pool = new PooledByteBufAllocator();//提高性能减少碎片，高效分配算法
     * 　　ByteBufAllocator unpool = new UnpooledByteBufAllocator(true);//一直新建
     * 　　引用计数
     * 　　ByteBufAllocator allocator = ctx.channel().alloc();
     * 　　ByteBuf directBuf = allocator.directBuffer();
     * 　　if(directBuf.refCnt() == 1){//当引用技术为1时释放对象
     * 　　directBuf.release();
     * 　　}
     */
    @Test
    public void test01() {
        ByteBuf byteBuf = ByteBufUtil.encodeString(new PooledByteBufAllocator(), CharBuffer.allocate(8), Charset.defaultCharset());
        // 缓冲区最大容量
        System.out.println(byteBuf.maxCapacity());
        // 可以包含的容量数
        System.out.println(byteBuf.capacity());
        // 可读下标
        System.out.println(byteBuf.readerIndex());
        // 可写下标
        System.out.println(byteBuf.writerIndex());
    }

    /**
     * 内存池化
     * <p>
     * Netty的ByteBuf分为池化的和非池化的，池化的优点包含如下两点：
     * <p>
     * -    1. 对于DirectByteBuffer的分配和释放是比较低效的，使用池化技术能快速分配内存。
     * -    2. 池化技术使对象可以复用，从而降低gc频率。
     * <p>
     * Recycler
     */
    @Test
    public void pooledTest() {

    }

    /**
     * netty 零拷贝
     * <p>
     * <p>
     * 一 JVM层面(非操作系统层面)
     * 1. 通过合并缓冲区
     * -    Unpooled.wrappedBuffer CompositeByteBuf 合并缓冲区
     * 2. 通过拆分缓冲区
     * -    slice
     * 3. 将数组, 缓冲区 包装成Netty 缓冲区, 避免复制
     * -    wrap
     * <p>
     * 二 操作系统层面
     * 1. Netty 的 FileRegion.transferTo() 调用 JDK 的 FileChannel.transferTo 最终调用 native 方法 transferTo0
     * -     JDK 的 FileChannel.transferFrom
     * 2. 直接内存
     * <p>
     * <p>
     * transferTo()
     * <p>
     * -    JDK API doc: 将该通道文件的字节传输到给定的可写字节通道。
     * -    (理解: 文件 channel 直接复制到 socket channel 或者 文件 channel, 无需经过用户空间, 调用操作系统的 sendfile )
     * -    方法使得文件内容被 DMA copy到了 kernel buffer(内核缓冲区)，这一动作由 DMA engine完成。
     * -    我们可以用这两个方法来把bytes直接从调用它的channel传输到另一个writable byte channel，中间不会使data经过应用程序，以便提高数据转移的效率。
     * <p>
     * transferFrom()
     * <p>
     * -    JDK API doc: 从给定的可读字节通道将字节传输到该通道的文件中。
     */
    @Test
    public void zeroCopy() {

    }
}