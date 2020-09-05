package org.song.network.javaio.io.io_02_niojava.demo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIO_01_demo {

    private int port = 10000;

    @Test
    public void client() {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(port));
            socketChannel.finishConnect();
            System.out.println("已连接");

            socketChannel.write(ByteBuffer.wrap("hello".getBytes()));
            socketChannel.write(ByteBuffer.wrap("end".getBytes()));
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(64);
                socketChannel.read(byteBuffer);
                String res = new String(byteBuffer.array());
                System.out.println("收到服务端信息" + res);
                if ("end".equals(res)) {
                    break;
                }
            }
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void server() {

        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();

                Iterator<SelectionKey> keyIterator = selector.keys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    if (!selectionKey.isValid()) {
                        keyIterator.remove();
                        continue;
                    }
                    if (selectionKey.isAcceptable()) {

                    }
                    if (selectionKey.isReadable()) {

                    }
                    if (selectionKey.isWritable()) {

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
