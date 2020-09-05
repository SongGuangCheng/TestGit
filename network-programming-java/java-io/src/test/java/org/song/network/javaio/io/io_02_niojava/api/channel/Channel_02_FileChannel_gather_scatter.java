package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Channel_02_FileChannel_gather_scatter {

    /**
     * 聚集写: 多个 buffer 写入 一个 channel
     * GatheringByteChannel 支持聚集写操作的 通道
     * 定义方法
     * write(ByteBuffer[] srcs); // 将多个 buffer 中数据, 写入到 channel 中
     * write(ByteBuffer[] srcs, int offset, int length);
     *
     */
    @Test
    public void test_01_gether() throws IOException {
        ByteBuffer buffer1 = ByteBuffer.wrap("aaaa".getBytes());
        ByteBuffer buffer2 = ByteBuffer.wrap("1111".getBytes());

        FileChannel fileChannel = FileChannel.open(Paths.get("file\\test.txt"),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);

        // FileChannel 继承 GatheringByteChannel, 所以支持聚集写
        // 将多个 buffer 数据写入一个 channel, 顺序按照目标数组下标顺序
        fileChannel.write(new ByteBuffer[]{buffer1, buffer2});
        fileChannel.close();
    }

    /**
     * 分散读: 一个 channel 读取到 多个 buffer
     * ScatteringByteChannel 支持分散读操作的 通道
     * 定义方法
     * read(ByteBuffer[] dsts); //
     * read(ByteBuffer[] dsts, int offset, int length);
     */
    @Test
    public void test_02_scatter() throws IOException {

        ByteBuffer buffer1 = ByteBuffer.allocate(4);
        ByteBuffer buffer2 = ByteBuffer.allocate(8);

        FileChannel fileChannel = FileChannel.open(Paths.get("file\\test.txt"),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);

        // FileChannel 继承 ScatteringByteChannel, 所以支持分散读
        // 将 channel 中数组分别 读取到多个 buffer 中, 按照 buffer 数组下标顺序, 满了之后下一个
        fileChannel.read(new ByteBuffer[]{buffer1, buffer2});
        System.out.println("buffer1: " + new String(buffer1.array()));
        System.out.println("buffer2: " + new String(buffer2.array()));
        fileChannel.close();
    }

}
