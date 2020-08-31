package org.song.network.javaio.io.io_02_niojava.api.buffer;


import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * Buffer 缓冲区 公共父类抽象类
 *
 * @description:
 * @date: 2019年07月09日 13:46:00
 **/
public class Buffer_02_index {

    /**
     * capacity:    缓冲区容量-大小, 也就是数组总长度, 一旦创建不能改变
     * limit:       缓冲区限制-大小, 可以存取数据的大小
     * position:    当前读取位置-下标,
     * mark:        标记-下标
     * <p>
     * Invariants: mark <= position <= limit <= capacity
     */
    @Test
    public void test_01_properties() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        System.out.println("capacity" + buffer.capacity());
        System.out.println("limit" + buffer.limit());
        System.out.println("position" + buffer.position());
        System.out.println("mark" + buffer.mark());
    }

    /**
     * capacity 一经初始化就不能改变,
     * 在分配缓冲区的时候, 传入的缓冲区的大小, 最终会赋值给capacity, 同时也是缓冲区底层数组的长度
     * <p>
     * 获取 capacity 的方法
     * buffer.capacity(); //
     */
    @Test
    public void test_02_cap() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        System.out.println("capacity" + buffer.capacity());
    }

    /**
     * limit 默认 = capacity, 标示能够操作的索引上限
     * <p>
     * 能够修改 limit 值的方法有:
     * <p>
     * buffer.limit(idx);// 设置新值
     * buffer.flip(); // 锁定到position, 锁定到已经从操作的元素
     * buffer.clear(); // 恢复初始值
     * <p>
     * 获取 limit 方法
     * buffer.limit();
     */
    @Test
    public void test_02_lim() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        System.out.println("limit " + buffer.limit());
        buffer.limit(4);
        System.out.println("limit(idx), limit " + buffer.limit());
        buffer.put((byte) 1);
        buffer.flip();
        System.out.println("flip(), limit " + buffer.limit());
        buffer.clear();
        System.out.println("clear(), limit " + buffer.limit());
    }

    /**
     * position 默认值 0, 标示将要操作的相对位置的下标,
     * 可以修改 position 值的方法有
     * <p>
     * buffer.position(index);  // 设置新值
     * buffer.limit(idx);       // 当设置的新的 limit <= position 的时候, 由于规定 必须 limit >= potion, 所以会将设置 position = limit
     * buffer.reset();          // 将设置 position = mark, 但是此时mark的值必须 >= 0
     * buffer.clear();          // 恢复初始值
     * buffer.flip();           // 设置 position = 0, 将操作索引调整到开始位置
     * buffer.rewind();         // 设置 position = 0, 将操作索引调整到开始位置, 在 position 索引下, flip() 和 rewind() 效果相同
     * <p>
     * 间接调用方法
     * nextGetIndex()/nextPutIndex(), position ++, position 向后移动1
     * 调用其方法有
     * get(),           // 获取相对位置元素
     * put(),           // 设置相对位置元素
     * pub(byte[]),     // 循环调用put()
     * <p>
     * 间接调用方法
     * nextGetIndex(int nb)/nextPutIndex(int nb), position += nb, position 向后移动nb
     * getChar()/putChar(),     // 获取/设置2个字节元素
     * getShort()/putShort(),   // 获取/设置2个字节元素
     * getFloat()/putFloat(),   // 获取/设置4个字节元素
     * getInt()/putInt(),       // 获取/设置4个字节元素
     * getLong()/putLong(),     // 获取/设置8个字节元素
     * getDouble()/putDouble(), // 获取/设置8个字节元素
     * <p>
     * 获取 position 方法
     * buffer.position();
     */
    @Test
    public void test_02_position() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        System.out.println("position " + buffer.position());
        buffer.position(2);
        System.out.println("position(idx), position " + buffer.position());

        buffer.mark();
        buffer.reset();
        System.out.println("reset(), position " + buffer.position());

        buffer.position(3);
        buffer.clear();
        System.out.println("buffer.clear(), position " + buffer.position());
        buffer.flip();
        System.out.println("buffer.flip(), position " + buffer.position());


        buffer.clear();
        buffer.put((byte) 1);
        System.out.println("buffer.put(byte), position " + buffer.position());
        buffer.put(new byte[]{2, 3, 3, 3});
        System.out.println("buffer.put(byte[]), position " + buffer.position());
        buffer.putInt(5);
        System.out.println("buffer.putInt(int), position " + buffer.position());

        buffer.flip();
        buffer.get();
        System.out.println("get(), position " + buffer.position());
        buffer.getInt();
        System.out.println("getInt(), position " + buffer.position());

    }

    /**
     * mark 默认值 -1, 用于临时标记下标
     * 可以修改mark的值的方法
     * buffer.mark();           // 打标记, 将赋值 mark = position
     * buffer.limit(idx);       // 设置新limit时, 如果 mark > limit, 则将 mark 重置为 -1
     * buffer.clear();          // 恢复初始值 -1
     * buffer.flip();           // 删除标记 -1
     * buffer.rewind();         // 删除标记 -1
     */
    @Test
    public void test_02_mark() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 没有直接获取mark的方法
    }

    /**
     * buffer.flip();
     * 翻转缓冲区, 通常用于在缓冲区写入数据之后读取操作, 也就是写后读操作, 如果 flip 之后进行写操作, 则原有数据会被覆盖
     * 读取和写入缓冲区的方法有很多
     */
    @Test
    public void test_03_flip() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putInt(6);
        buffer.flip();
        System.out.println(buffer.getInt());

    }

    /**
     * rewind() 倒带
     * 经常用于重读, 和 flip 类似, 但是不会设置limit的值,
     * 也可用于重写
     */
    @Test
    public void test_03_rewind() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putInt(4);
        buffer.rewind();
        System.out.println(buffer.getInt());

        buffer.clear();

        buffer.putLong(4);
        buffer.putLong(4);
        buffer.rewind();
        buffer.putLong(3);
        buffer.putLong(3);
        System.out.println(buffer);

    }

    /**
     * mark() 打标记, 标记当前 position 的位置,
     * 经常和 reset() 一起使用
     * reset() 重置 position 索引
     * <p>
     * 以本例说明
     * 当 buffer 写入一些数据之后, 在当前位置打上标记, 然后继续写入一些数据, 这时候调用 reset(), 开始读取数据, 将会读到 mark 之后的数据, 而不是从头开始读
     * 和 flip(), rewind() 区别
     * flip/rewind, 后的操作都是从头开始, 而 reset 后的操作是从 mark 开始
     */
    @Test
    public void test_03_mark_reset() {
        ByteBuffer buffer = ByteBuffer.allocate(16);

        buffer.put(new byte[]{1, 2, 3});
        buffer.mark();
        buffer.put(new byte[]{4, 5, 6});
        buffer.reset();
        System.out.println(buffer.get());
    }

    /**
     * 其他方法
     *
     * remaining(), 返回剩余可写空间
     * hasRemaining(), 返回是否还有剩余可 写/读 空间
     * <p>
     * isReadOnly(), 是否是只读缓冲区
     * hasArray(), 是否由可访问数组支持,
     * array(), 返回数组
     * <p>
     * isDirect(), 是否是直接缓冲区,
     *
     * buffer.duplicate(), 复制一个缓冲区,
     */
    @Test
    public void test_04_other() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        System.out.println(buffer.remaining());
        System.out.println(buffer.hasRemaining());

        System.out.println(buffer.isReadOnly());
        System.out.println(buffer.hasArray());
        System.out.println(buffer.array());

        System.out.println(buffer.isDirect());

        ByteBuffer duplicate = buffer.duplicate();


    }
}