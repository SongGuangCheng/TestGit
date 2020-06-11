package org.song.network.javaio.io.noneio;

import org.junit.jupiter.api.Test;
import org.song.network.javaio.utils.IOUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOTestCase_01 {

    private static int port = 10001;

    @Test
    public void client() throws IOException {

        Socket socket = new Socket("127.0.0.1", port);

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        writer.println("client : 我发消息了");
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String body;
        while (true) {
            body = reader.readLine();
            if (body == null || "end".equals(body)) {
                break;
            }
            System.out.println("client 收到服务端消息 :" + body);
        }
        IOUtils.close(writer, reader, socket);
    }

    @Test
    public void server() throws IOException {

        // 获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 设置为非阻塞
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务端启动成功！");
        while (true) {
            SocketChannel accept = serverChannel.accept();
            // 创建读取的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = accept.read(buffer);
            if (read > 0) {
                byte[] data = buffer.array();
                String msg = new String(data).trim();
                System.out.println("服务端收到信息事件: " + msg);
                //回写数据
                ByteBuffer outBuffer = ByteBuffer.wrap("server res ...".getBytes());
                accept.write(outBuffer);// 将消息回送给客户端
            }
        }
    }
}