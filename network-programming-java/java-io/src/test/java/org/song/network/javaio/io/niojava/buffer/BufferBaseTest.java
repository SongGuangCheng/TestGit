package org.song.network.javaio.io.niojava.buffer;


import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * Buffer 缓冲区 公共父类抽象类
 *
 *
 * @description:
 * @date: 2019年07月09日 13:46:00
 **/
public class BufferBaseTest {

    /**
     * 创建缓冲区方式
     */
    @Test
    public void bufferInitialized() {
        // 1. 方式1 直接分配大小
        ByteBuffer allocateBuffer = ByteBuffer.allocate(16);

        // 2. 方式2 直接分配大小, 创建直接缓冲区
        ByteBuffer allocateDirectBuffer = ByteBuffer.allocateDirect(16);

        // 3. 方式3 使用数组初始化 创建缓冲区
        byte[] array = new byte[]{1, 2, 3, 4};
        ByteBuffer wrapBuffer = ByteBuffer.wrap(array);

    }

    /**
     * capacity:    缓冲区容量-大小, 也就是数组总长度, 一旦创建不能改变
     * limit:       缓冲区限制-大小, 可以存取数据的大小
     * position:    当前读取位置-下标,
     * mark:        标记-下标
     * <p>
     * Invariants: mark <= position <= limit <= capacity
     */
    @Test
    public void properties() {
        ByteBuffer allocateBuffer = ByteBuffer.allocate(16);
        System.out.println("capacity: " + allocateBuffer.capacity());
        System.out.println("limit: " + allocateBuffer.limit());
        System.out.println("position: " + allocateBuffer.position());
        System.out.println("mark: " + allocateBuffer.mark());
    }

    /**
     * 缓冲区的使用 存取
     * get() 和 put()
     */
    @Test
    public void access1() {
        ByteBuffer allocateBuffer = ByteBuffer.allocate(8);

//        print(allocateBuffer);

        /**
         * put() 在position位置, 存入一个元素
         * get() 和 put() 每操作一次, position位置都会后移一次
         */
        byte b = 1;
        allocateBuffer.put(b);
        print(allocateBuffer);

        /**
         * put(array)
         * position会向后移动 array.length 位
         */
//        byte[] array = new byte[]{1, 2, 3, 4};
//        allocateBuffer.put(array);
//        print(allocateBuffer);

        /**
         * put(index, byte)
         * 指定位置放入元素, position 不变
         */
        byte b3 = 3;
        allocateBuffer.put(5, b3);
        print(allocateBuffer);

        /**
         * get() 在position位置, 存入一个元素
         * get() 和 put() 每操作一次, position 位置都会后移一次
         */
//        byte get = allocateBuffer.get();
//        print(allocateBuffer);
//        System.out.println(get);

        /**
         * get(index) 获取指定位置的元素, position不变
         */
        byte b1 = allocateBuffer.get(5);
        System.out.println(b1);
        print(allocateBuffer);
    }

    /**
     * 打印缓冲区内容
     *
     * @param byteBuffer
     */
    public static void print(ByteBuffer byteBuffer) {
        byte[] hb = byteBuffer.array();
        System.out.println(byteBuffer);
        for (int i = 0; i < hb.length; i++) {

            StringBuilder sb = new StringBuilder();
            sb.append(i).append(":").append(hb[i]);
            if (i == byteBuffer.position()) {
                sb.append("_pos");
            }
            if (byteBuffer.limit() - 1 == i) {
                sb.append("_lim");
            }
            if (byteBuffer.capacity() - 1 == i) {
                sb.append("_cap");
            }
            sb.append(" ");
            System.out.print(sb.toString());
        }
        System.out.println();
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
    public void direct() {
        //分配直接缓冲区
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);

        System.out.println(buf.isDirect());
    }

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