package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;
import org.song.network.javaio.io.io_02_niojava.api.buffer.BufferUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

public class Channel_02_file {

    /**
     * FileChannel 通道
     * <p>
     * 连接到文件的通道, 可以进行 读,写,map等操作
     * 具体包含的方法有
     * read(ByteBuffer, long);      在绝对位置 读操作
     * write(ByteBuffer, long);     在绝对位置 写操作
     * map(MapMode mode,long position, long size); 映射文件到内存, 可以进行读或写操作, 通常更适合于大文件
     * force(boolean metaData);     将数据更新到硬件, 以防止内存数据丢失
     * transferTo(long position, long count, WritableByteChannel target);   直接写入缓冲区, 比如直接从 FileChannel 写入到 SocketChannel
     * transferFrom(ReadableByteChannel src,long position, long count);     直接从缓冲区读取,
     * <p>
     * FileChannel 是线程安全的, 所有涉及到 position 修改的操作, 都将被阻塞,
     * <p>
     * FileChannel 提供统一的视图操作, 视图的内容和文件的内容同步
     * <p>
     * FileChannel 文件操作模式分为, 只读和读写
     * <p>
     * 包含方法
     * open(); 打开一个文件通道
     * read(); 从通道中读取数据
     * write(); 将数据写入到通道中
     * position(); 返回通道文件的 操作索引, 或设置通道文件的 操作索引
     * size(); 文件字节数
     * truncate()
     * force()
     * transferTo(); 将本文件通道中的数据直接发送到目标通道中
     * transferFrom(); 从目标通道中直接读取数据
     * map(); 创建一个内存映射文件 MappedByteBuffer
     * lock(); 给通道中指定的文件区域上锁, 或者整个通道上锁
     * tryLock(); 尝试获取通道中指定文件区域的锁, 或者整个通道的锁
     */
    @Test
    public void test_02_file_base() {

    }

    /**
     * 获取一个 FileChannel 实例
     * 方式通常有3
     * 1. FileChannel.open()
     * 2. 通过 FileInputStream 实例的 getChannel()
     * 3. 通过 RandomAccessFile 实例的 getChannel()
     */
    @Test
    public void test_02_file_create() throws Exception {

        // 1. FileChannel.open(), 文件路径, 模式
        FileChannel fileChannel = FileChannel.open(Paths.get(new URI("")),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);

        Set<OpenOption> options = new HashSet<>();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.READ);
        options.add(StandardOpenOption.WRITE);
        FileChannel fileChannel2 = FileChannel.open(Paths.get(new URI("")), options);

        // 2. 通过 FileInputStream 实例的 getChannel() 方法
        FileInputStream fis = new FileInputStream(new File(""));
        FileChannel fisChannel = fis.getChannel();

        // 3. 通过 RandomAccessFile 实例的 getChannel() 方法
        RandomAccessFile raf = new RandomAccessFile(new File(""), "rw");
        FileChannel rafChannel = raf.getChannel();
    }

    /**
     * FileChannel 的读操作
     *
     * 读取操作会修改 position 的值, 如果需要 FileChannel 重复读, 则需要重置 position
     * fileChannel.read(byteBuffer); // 读取 FileChannel 内容到 缓冲区
     * fileChannel.read(byteBuffer, position); // 从 FileChannel 的指定 position 位置开始, 读取 FileChannel 内容到 缓冲区
     */
    @Test
    public void test_03_read() throws URISyntaxException, IOException {

        FileChannel fileChannel = FileChannel.open(Paths.get("file\\FileChannelTest.txt"),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);

        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        // 1. 读取 FileChannel 内容到 缓冲区
        fileChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println("read(ByteBuffer): " + new String(byteBuffer.array()));
        // 读取操作会修改 position 的值, 如果需要 FileChannel 重复读, 则需要重置 position
        fileChannel.position(0);


        ByteBuffer byteBuffer02 = ByteBuffer.allocate(16);
        // 2. 从 FileChannel 的指定 position 位置开始, 读取 FileChannel 内容到 缓冲区
        fileChannel.read(byteBuffer02, 2);
        byteBuffer02.flip();
        System.out.println("read(ByteBuffer, position): " + new String(byteBuffer02.array()));
        fileChannel.position(0);


        ByteBuffer byteBuffer03 = ByteBuffer.allocate(16);
        ByteBuffer byteBuffer04 = ByteBuffer.allocate(16);
        // 3. 将 FileChannel 中数据, 分散读到多个 buffer中
        fileChannel.read(new ByteBuffer[]{byteBuffer03, byteBuffer04} );
        byteBuffer03.flip();
        System.out.println("read(ByteBuffer[], offset, len): " + new String(byteBuffer03.array()));
        byteBuffer04.flip();
        System.out.println("read(ByteBuffer[], offset, len): " + new String(byteBuffer04.array()));

    }

    /**
     * FileChannel 的写操作
     * 写入操作为啥不好使 ?
     */
    @Test
    public void test_03_write() throws IOException {

        FileChannel fileChannel = FileChannel.open(Paths.get("file\\FileChannelTest.txt"),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);

        ByteBuffer byteBuffer = ByteBuffer.wrap("ffff".getBytes());
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            fileChannel.write(byteBuffer);
        }
        fileChannel.force(true);
        fileChannel.close();
    }

}
