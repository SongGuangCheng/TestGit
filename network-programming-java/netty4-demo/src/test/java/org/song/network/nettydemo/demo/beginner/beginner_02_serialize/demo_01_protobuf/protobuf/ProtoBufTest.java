package org.song.network.nettydemo.demo.beginner.beginner_02_serialize.demo_01_protobuf.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.Test;

public class ProtoBufTest {

    /**
     * hello world
     * <p>
     * 1. protobuf 会根据 proto 文件生成对应的java类, 并构建一套建造者模式的代码, 方便在代码中构建指定对象
     * 2. 对应的java类对象, 是自定生成的, 所以不要在里面修改内容, 因为重新生成会被覆盖
     */
    @Test
    public void helloProto_01() {
        // 建造者模式 构建对象
        Data.Student student = Data.Student.newBuilder()
                .setEmail("123@qq.com").setId(12).setName("小南")
                .build();
        System.out.println(student.toByteString());
        // 序列化成字节数组, 用于存储磁盘或者网络传输
        byte[] studentByteArray = student.toByteArray();

        // 反序列化成对象
        try {
            Data.Student student2 = Data.Student.parseFrom(studentByteArray);
            System.out.println(student2.toByteString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }
}
