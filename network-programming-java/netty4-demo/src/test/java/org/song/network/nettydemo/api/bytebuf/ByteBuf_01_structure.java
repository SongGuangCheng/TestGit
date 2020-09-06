package org.song.network.nettydemo.api.bytebuf;

public class ByteBuf_01_structure {

    /**
     * ByteBuf 抽象类, 定义了 ByteBuf 的主要特性
     *
     * ReferenceCounted 接口, 通过引用计数来管理对象的释放
     * -    - ByteBuf 抽象类, 定义了 ByteBuf 的主要特性
     * -    -   - AbstractByteBuf 抽象类
     * -    -   -   - AbstractDerivedByteBuf 抽象类
     * -    -   -   -   - AbstractUnpooledSlicedByteBuf 抽象类
     * -    -   -   -   -   - UnpooledSlicedByteBuf 类
     *
     * -    -   -   - AbstractReferenceCountedByteBuf 抽象类
     * -    -   -   -   - AbstractPooledDerivedByteBuf 抽象类
     * -    -   -   -   -   - PooledDuplicatedByteBuf 类
     *
     * -    -   -   - AbstractReferenceCountedByteBuf 抽象类
     * -    -   -   -   - PooledByteBuf 抽象类
     * -    -   -   -   -   - PooledHeapByteBuf 类
     * -    -   -   -   -   -   - PooledUnsafeHeapByteBuf 类
     * -    -   -   -   -   - PooledDirectByteBuf 类
     *
     * -    -   -   - AbstractReferenceCountedByteBuf 抽象类
     * -    -   -   -   - UnpooledHeapByteBuf 抽象类
     * -    -   -   -   - UnpooledDirectByteBuf 抽象类
     *
     * -    -   -   - AbstractReferenceCountedByteBuf 抽象类
     * -    -   -   -   - CompositeByteBuf 类
     * -    -   -   -   -   - WrappedCompositeByteBuf 类
     * -    -   -   -   - FixedCompositeByteBuf 类
     *
     * -    -   -   - AbstractReferenceCountedByteBuf 抽象类
     * -    -   -   -   - ReadOnlyByteBufferBuf 类
     * -    -   -   -   -   - ReadOnlyUnsafeDirectByteBuf 类
     * -    -   - WrappedByteBuf 类
     * -    -   - EmptyByteBuf 类
     */
    public void test_01_ByteBuf(){

    }

    /**
     * ByteBufAllocator 缓冲区内存分配器
     *
     * ByteBufAllocator 接口
     * -    - AbstractByteBufAllocator 抽象类
     * -    -   - PooledByteBufAllocator 类
     * -    -   - UnpooledByteBufAllocator 类
     *
     * ByteBufAllocatorMetricProvider 接口
     * -    - PooledByteBufAllocator 类
     * -    - UnpooledByteBufAllocator 类
     *
     * ByteBufAllocatorMetric 接口
     * -    - PooledByteBufAllocatorMetric 类
     * -    - UnpooledByteBufAllocatorMetric 类
     *
     */
    public void test_02_ByteBufAllocator(){

    }
}
