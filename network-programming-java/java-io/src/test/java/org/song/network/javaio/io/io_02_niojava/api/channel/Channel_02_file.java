package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
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

}
