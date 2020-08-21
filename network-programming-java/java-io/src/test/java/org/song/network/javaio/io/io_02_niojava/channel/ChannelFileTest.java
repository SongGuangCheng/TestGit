package org.song.network.javaio.io.io_02_niojava.channel;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * 文件通道
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * <p>
 * 二、通道的主要实现类
 * java.nio.channels.Channel 接口：
 * -     |--FileChannel 文件通道不能被选择器选择, 文件通道只有阻塞模式没有非阻塞模式
 * -     |--SocketChannel
 * -     |--ServerSocketChannel
 * -     |--DatagramChannel
 * <p>
 * 三、获取通道
 * -     1. Java 针对支持通道的类提供了 getChannel() 方法
 * -     本地 IO：
 * -         FileInputStream/FileOutputStream
 * -         RandomAccessFile
 * <p>
 * -     网络IO：
 * -         Socket
 * -         ServerSocket
 * -        DatagramSocket
 * <p>
 * -    2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * -    3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * <p>
 * 四、通道之间的数据传输
 * -    transferFrom()
 * -    transferTo()
 * <p>
 * 五、分散(Scatter)与聚集(Gather)
 * -    分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中, 从Channel读取数据, 分散到多个Buffer中,
 * -            (按照缓冲区的顺序, 从Channel中读取的数据依次将Buffer填满)
 * -    聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中, 将多个Buffer中的数据聚集草Channel中
 * -            (按照缓冲区的顺序, 写入position和limit之间的数据到Channel中)
 * <p>
 * 六、字符集：Charset
 * -    编码：字符串 -> 字节数组
 * -    解码：字节数组  -> 字符串
 */
public class ChannelFileTest {

    /**
     * 字符集
     * 编解码也就是 ByteBuffer和CharBuffer之间的转换
     *
     * @throws IOException
     */
    @Test
    public void charsetTest() throws IOException {
        Charset cs1 = Charset.forName("GBK");

        // 获取编码器
        CharsetEncoder ce = cs1.newEncoder();
        // 获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("威武！");
        cBuf.flip();

        //编码
        ByteBuffer bBuf = ce.encode(cBuf);

        for (int i = 0; i < 4; i++) {
            System.out.println(bBuf.get());
        }

        //解码
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());

        System.out.println("------------------------------------------------------");

        // 编解码方式不同 会乱码
        Charset cs2 = Charset.forName("UTF-8");
        bBuf.flip();
        CharBuffer cBuf3 = cs2.decode(bBuf);
        System.out.println(cBuf3.toString());
    }

    /**
     * 字符集
     */
    @Test
    public void charset() {
        // 查看 支持的字符集
        Map<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    /**
     * 演示4
     * <p>
     * 分散和聚集
     * <p>
     * (RandomAccessFile)不区分流方式, 仅仅作为示例
     *
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");

        //1. 获取通道
        FileChannel channel1 = raf1.getChannel();

        //2. 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3. 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            // 切换成读取模式
            byteBuffer.flip();
        }

        // 验证是否是按顺序依次填满
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-----------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //4. 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();

        channel2.write(bufs);
    }

    /**
     * 演示3
     * 通道之间的数据传输(直接缓冲区)
     * 使用通道间直接数据传输(复制)
     *
     * @throws IOException
     */
    @Test
    public void transfer() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mkv"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mkv"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 数据到哪去, 和下面的方法都可以(两者选其一)
//		inChannel.transferTo(0, inChannel.size(), outChannel);
        // 数据从哪来
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    /**
     * 演示2 使用open方法获取通道 + 使用直接缓冲区(内存映射) 完成文件复制
     * 使用直接缓冲区完成文件的复制(内存映射文件)
     * 直接缓冲区的速度要比非直接缓冲区 块10倍, 不过稳定性差, CPU占用高, 且数据从物理内存写到硬盘上时间不受控制
     *
     * @throws IOException
     */
    @Test
    public void fileCopyDirect() throws IOException {//2127-1902-1777
        long start = System.currentTimeMillis();

        // 使用 NIO2 的open()方法获取通道 使用FileChannel.open()方法获取通道
        // open() 方法是可变参数, 可以传入多个
        FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mkv"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mkv"),
                StandardOpenOption.WRITE, // 写入文件
                StandardOpenOption.READ, // 读取文件
                StandardOpenOption.CREATE // 如果文件不存在就创建,
        );

        // 创建内存映射文件(直接操作内核内存), 只支持byteBuffer
        // 只读模式
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        // 读写模式, (要和上面的outChannel模式对应上)
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }

    /**
     * 演示1: 使用文件流获取通道, 进行文件复制
     * 利用通道完成文件的复制（非直接缓冲区）
     */
    @Test
    public void fileCopyHeap() {//10874-10953
        long start = System.currentTimeMillis();

        FileInputStream fis = null;
        FileOutputStream fos = null;
        // 获取通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("d:/1.mkv");
            fos = new FileOutputStream("d:/2.mkv");

            // 1. 通过流 获取通道(通过传输数据必须依赖缓冲区), 使用流.getChannel()方法获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            // 2. 分配指定大小的缓冲区, 通过传输数据必须依赖缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // 3. 将通道中的数据存入缓冲区中
            while (inChannel.read(buf) != -1) {
                buf.flip(); //切换读取数据的模式
                // 4. 将缓冲区中的数据写入通道中
                outChannel.write(buf);
                buf.clear(); //清空缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));

    }
}