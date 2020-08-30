package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Channel_03_socket {

    /**
     * SocketChannel socket 通道
     * SocketChannel.open();
     * SocketChannel.open(SocketAddress);
     */
    @Test
    public void test_03_socket_create() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
    }

}
