package org.song.network.javaio.io.niojava;

import org.junit.jupiter.api.Test;
import org.song.network.javaio.utils.IOUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * socket IO demo
 * NIO
 * <p>
 * 总结
 * 服务端包含: 通道 channel(传输数据用, 类似于流), 缓冲区 buffer(数据包装或数据本身), 选择器 selector(事件处理器)
 * 1. 获取选择器
 * 2. 服务端的 通道ServerSocketChannel 注册到选择器(连接事件)
 * 3. 选择器阻塞式选择事件 selector.select(), 并处理事件
 * -    1. 如果是连接事件, 则建立连接之后, 将通道注册到选择器注册为可读取数据事件
 * -    2. 如果是可读数据事件, 则读取缓冲区中数据, 交由业务处理
 */
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

    /**
     * 过程
     * <p>
     * 获取选择器
     * -    1. 获取选择器 Selector.open(), 等待通道注册
     * <p>
     * 获取通道
     * -    1. 获得一个ServerSocket通道 ServerSocketChannel.open()
     * -    2. 设置为非阻塞(可选)
     * -    3. 绑定端口
     * -    4. 注册到选择器中(指定事件, 比如建立连接事件 SelectionKey.OP_ACCEPT)
     * <p>
     * 选择器处理通道
     * -    1. 选择器事件监听 selector.select();
     * -    2. 通过选择其中的 SelectionKey 来判断事件类型, 并获取其中的通道, 交由不同的业务处理
     * -        1. 如果是连接事件(SelectionKey.OP_ACCEPT), 获取完通道中的数据之后, 再将改通道注册到选择器中(Selector), 事件为可读取数据事件(SelectionKey.OP_READ)
     * -        2. 如果是可读取数据事件(SelectionKey.OP_READ), 则读取通道中的数据, 然后进行业务处理
     * <p>
     * 测试方式
     * - 1. 使用 telnet 客户端
     * -    1. telnet 127.0.0.1 10001   # 建立连接
     * -    2. ctrl + ]                 # 打开发送信息窗口
     * -    3. sen msg                  # 向服务端发送信息
     * - 2. 使用 socket 客户端 <>略</>
     *
     * @throws IOException
     */
    @Test
    public void server() throws IOException {

        // 1. 获得选择器
        Selector selector = Selector.open();

        // 2. 获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 设置为非阻塞
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(port));
        // 将通道注册到选择器中, 注册事件为建立连接
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务端启动成功！");
        while (true) {
            /**
             * 3. 选择器处理通道事件
             *
             * TODO 阻塞点1, 选择器监听事件
             * 服务端通道事件
             * 接收连接事件(SelectionKey.OP_ACCEPT), 新的连接建立, 则相当于socket的accept() 阻塞
             * 可读事件(SelectionKey.OP_READ), 则相当于socket中的读取请求流数据 阻塞
             * 可写事件(SelectionKey.OP_WRITE), 通道中等待数据写入
             *
             * 连接事件(SelectionKey.OP_CONNECT), 完成了握手之后, 通道所处的状态(socket 客户端的状态)
             */
            selector.select();

//            // 不阻塞
//            selector.select(1000);
//            // 也可以唤醒selector
//            selector.wakeup();
//            // 也可以立马返还
//            selector.selectNow();

            // 获得selector中选中的项的迭代器，选中的项为注册的事件
            Iterator<?> ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();
                // 删除已选的key,以防重复处理
                ite.remove();

                // 处理建立连接事件
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    // 获得和客户端连接的通道
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);

                    // 在这里可以给客户端发送信息
                    System.out.println("新的客户端连接 事件 ...");
                    // 建立连接之后, 注册可读取数据事件(设置可读权限)
                    channel.register(selector, SelectionKey.OP_READ);
                }

                // 处理可读数据的事件
                else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 创建读取的缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = channel.read(buffer);
                    if (read > 0) {
                        byte[] data = buffer.array();
                        String msg = new String(data).trim();
                        System.out.println("服务端收到信息事件: " + msg);

                        //回写数据
                        ByteBuffer outBuffer = ByteBuffer.wrap("server res ...".getBytes());
                        channel.write(outBuffer);// 将消息回送给客户端
                    } else {
                        System.out.println("客户端关闭");
                        key.cancel();
                    }
                }
            }
        }
    }
}