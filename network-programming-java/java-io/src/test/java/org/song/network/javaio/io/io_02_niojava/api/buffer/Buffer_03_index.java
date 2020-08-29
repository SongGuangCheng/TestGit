package org.song.network.javaio.io.io_02_niojava.api.buffer;


import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * Buffer 缓冲区 公共父类抽象类
 *
 * @description:
 * @date: 2019年07月09日 13:46:00
 **/
public class Buffer_03_index {

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
        ByteBuffer allocateBuffer = ByteBuffer.allocate(16);
        System.out.println("capacity" + allocateBuffer.capacity());
        System.out.println("limit" + allocateBuffer.limit());
        System.out.println("position" + allocateBuffer.position());
        System.out.println("mark" + allocateBuffer.mark());
    }

    /**
     * capacity 一已经初始化就不能改变,
     * 在分配缓冲区的时候, 传入的缓冲区的大小, 最终会赋值给capacity, 同时也是缓冲区底层数组的长度
     */
    @Test
    public void test_02_cap() {
        ByteBuffer allocateBuffer = ByteBuffer.allocate(16);
        System.out.println("capacity" + allocateBuffer.capacity());
    }

    /**
     * limit 默认 = capacity, 标示能够操作的索引上限
     * <p>
     * 能够修改 limit 值的方法有:
     * <p>
     * buffer.limit(idx);// 设置新值
     * buffer.flip(); // 锁定到position, 锁定到已经从操作的元素
     * buffer.clear(); // 恢复初始值
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
     *
     * buffer.position(index); // 设置新值
     * buffer.limit(idx); // 当设置的新的 limit <= position 的时候, 由于规定 必须 limit >= potion, 所以会将设置 position = limit
     * buffer.reset(); // 将设置 position = mark, 但是此时mark的值必须 >= 0
     * buffer.clear(); // 恢复初始值
     * buffer.flip(); // 设置 position = 0, 将操作索引调整到开始位置
     * buffer.rewind(); // 设置 position = 0, 将操作索引调整到开始位置, 在 position 索引下, flip() 和 rewind() 效果相同
     *
     * 间接调用方法
     * nextGetIndex()/nextPutIndex(), position ++, position 向后移动1
     * 调用其方法有
     * get(), 获取相对位置元素
     * put(), 设置相对位置元素
     *
     * 间接调用方法
     * nextGetIndex(int nb)/nextPutIndex(int nb), position += nb, position 向后移动nb
     * getChar()/putChar(), 获取/设置2个字节元素
     * getShort()/putShort(), 获取/设置2个字节元素
     * getFloat()/putFloat(), 获取/设置4个字节元素
     * getInt()/putInt(), 获取/设置4个字节元素
     * getLong()/putLong(), 获取/设置8个字节元素
     * getDouble()/putDouble(), 获取/设置8个字节元素
     *
     */
    @Test
    public void test_02_position() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        System.out.println("position " + buffer.position());

    }


    /****************************************来自尚古学堂******************************************************************************/

    /**
     * 来自尚古学堂
     * <p>
     * 一、缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
     * <p>
     * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
     * ByteBuffer
     * CharBuffer
     * ShortBuffer
     * IntBuffer
     * LongBuffer
     * FloatBuffer
     * DoubleBuffer
     * <p>
     * 上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
     * <p>
     * 二、缓冲区存取数据的两个核心方法：
     * put() : 存入数据到缓冲区中
     * get() : 获取缓冲区中的数据
     * <p>
     * 三、缓冲区中的四个核心属性：
     * capacity : 容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。
     * limit : 界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
     * position : 位置，表示缓冲区中正在操作数据的位置。
     * <p>
     * mark : 标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置
     * <p>
     * 0 <= mark <= position <= limit <= capacity
     * <p>
     * 四、直接缓冲区与非直接缓冲区：
     * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
     * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
     */

    @Test
    public void mark() {
        String str = "abcde";

        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.put(str.getBytes());

        buf.flip();

        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
        System.out.println(buf.position());

        //mark() : 标记
        buf.mark();

        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));
        System.out.println(buf.position());

        //reset() : 恢复到 mark 的位置
        buf.reset();
        System.out.println(buf.position());

        //判断缓冲区中是否还有剩余数据
        if (buf.hasRemaining()) {

            //获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());
        }
    }

    @Test
    public void initialized() {
        String str = "abcde";

        //1. 分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        System.out.println("-----------------allocate()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //2. 利用 put() 存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("-----------------put()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //3. 切换读取数据模式
        buf.flip();

        System.out.println("-----------------flip()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //4. 利用 get() 读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));

        System.out.println("-----------------get()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //5. rewind() : 可重复读
        buf.rewind();

        System.out.println("-----------------rewind()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        buf.clear();

        System.out.println("-----------------clear()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println((char) buf.get());

    }
}