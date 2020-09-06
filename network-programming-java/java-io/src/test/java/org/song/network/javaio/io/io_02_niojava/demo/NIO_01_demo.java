package org.song.network.javaio.io.io_02_niojava.demo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Scanner;

public class NIO_01_demo {

    private int port = 10000;

    @Test
    public void client_01_NIO_Once() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
//            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(port));
            socketChannel.finishConnect();
            System.out.println("已连接");

            ByteBuffer buf = ByteBuffer.allocate(1024);
            Scanner scan = new Scanner(System.in);

            socketChannel.write(ByteBuffer.wrap((LocalTime.now() + ": hello!").getBytes()));
            socketChannel.read(buf);
            System.out.println(new String(buf.array()));
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void client_01_NIO_SystemIN() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(port));
            socketChannel.finishConnect();
            System.out.println("已连接");

            ByteBuffer buf = ByteBuffer.allocate(1024);
            Scanner scan = new Scanner(System.in);

            while (scan.hasNext()) {
                buf.put((LocalTime.now() + "\n" + scan.next()).getBytes());
                buf.flip();
                socketChannel.write(buf);
                buf.clear();
            }

            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void server_01_NIO() {

        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();

                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    keyIterator.remove();
                    if (!selectionKey.isValid()) {
                        continue;
                    }
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        System.out.println(LocalTime.now() + ": 已连接 " + sc.getLocalAddress().toString());
                        sc.register(selector, SelectionKey.OP_READ);
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        socketChannel.read(byteBuffer);
                        System.out.println(LocalTime.now() + ": 收到数据 " + new String(byteBuffer.array()));

                        socketChannel.register(selector, SelectionKey.OP_WRITE);
                    }
                    if (selectionKey.isWritable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        channel.write(ByteBuffer.wrap(("response: " + LocalDateTime.now()).getBytes()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
