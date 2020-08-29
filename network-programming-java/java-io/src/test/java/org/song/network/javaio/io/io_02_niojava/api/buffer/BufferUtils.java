package org.song.network.javaio.io.io_02_niojava.api.buffer;

import java.nio.ByteBuffer;

public class BufferUtils {

    public static void print(String name, ByteBuffer byteBuffer) {
        byte[] hb = byteBuffer.array();
        System.out.println(name + ": " + byteBuffer);
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
            sb.append("  ");
            System.out.print(sb.toString());
        }
        System.out.println();
    }

    public static void print(ByteBuffer byteBuffer) {
        print("", byteBuffer);
    }
}
