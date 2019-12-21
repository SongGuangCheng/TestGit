package org.song.network.javaio.io.bio;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.song.network.javaio.utils.ExecutorUtils;
import org.song.network.javaio.utils.IOUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket IO demo
 * BIO, 单线程方式, 和线程池方式
 * <p>
 * 总结
 * 1. socket IO 服务端 有两个阻塞点, BIO模型中, 两个阻塞点分配在不同的线程中处理, 一般使用一个线程循环处理连接请求, 使用一线线程池处理获取请求数据并进行业务处理
 * -    1. 建立连接
 * -    2. 读取请求流数据
 * 2. socket client 和 server, 通信需要使用指定的字符来表示 数据请求完毕, 接收方方可中断数据获取, 进行业务处理,
 * (tcp中并没有指定:某个字符表示传输完成, 通常是其他协议包装tcp协议, 然后规定某些字符为传输结束的字符)
 * <p>
 * 本地之间的握手通信 wireshark 似乎抓不到包, 估计是本机通信不走网卡
 */
public class BIOTestCase_01 {

    private static Logger logger = LoggerFactory.getLogger(BIOTestCase_01.class);

    private String ip = "127.0.0.1";
    private int port = 10001;

    @Test
    public void client() throws IOException, InterruptedException {
        // 目标IP和端口(注意: 客户端IP是当前地址IP, 客户端端口是随机端口)
        Socket socket = new Socket(ip, port);
        // 可以手动指定客户端端口
//        socket.bind(new InetSocketAddress(60428));

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        writer.println("client : 我发消息了");
        // 发送结束码
        writer.println("end");
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String body;
        while (true) {
            body = reader.readLine();
            if (body == null || "end".equals(body)) {
                break;
            }
            System.out.println("client: 收到服务端消息 :" + body);
        }

        System.out.println("client: 结束 " + body);
//        TimeUnit.SECONDS.sleep(10);
        IOUtils.close(reader, writer, socket);
    }

    /**
     * socket server base, 单线程
     * 测试方式
     * - 1. 使用 telnet 客户端
     * -    1. telnet 127.0.0.1 10001   # 建立连接
     * -    2. ctrl + ]                 # 打开发送信息窗口
     * -    3. sen 内容                  # 向服务端发送信息
     * - 2. 使用 socket 客户端 <>略</>
     *
     * <p>
     * socket server 会有两个阻塞点
     * -    1. 建立连接的 accept()
     * -    2. 读取客户端发送的数据
     *
     * @throws IOException
     */
    @Test
    public void server_single_thread() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));

        System.out.println("server start ...");

        // TODO 阻塞点1, 阻塞等待(系统)客户端建立连接()
        Socket socket = serverSocket.accept();

        System.out.println("Server: 收到新连接 " + socket.getInetAddress().toString());

        // 获取 输入流 输出流
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
//        writer.println("Server response: hello !");
//        writer.println("end");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String body;
        while (true) {
            // TODO 阻塞点2, 读取请求的数据
            body = reader.readLine();
            // 指定数据表示结束
            if (body == null || "end".equals(body)) {
                break;
            }
            System.out.println("Server: 收到客户端消息 :" + body);
        }

        writer.write("Server response: hello !");
        // 发送结束码
        writer.write("end");


        writer.flush();

        IOUtils.close(reader, writer, socket);
    }

    /**
     * socket server 多线程
     * <p>
     * 1. 循环处理连接请求 accept()
     * 2. 获取到连接请求后, 连接请求交给线程池处理, 同时监听下一个连接请求
     * 3. 线程池, 阻塞式获取请求的数据,
     * 4. 线程池, 获取请求数据后交由业务处理
     * <p>
     * 测试方式 同上单线程模型
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void server_multipart_thread() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));

        System.out.println("server start ...");

        while (true) {
            // 阻塞点1, 阻塞等待(系统)客户端建立连接()
            Socket socket = serverSocket.accept();
            InetAddress inetAddress = socket.getInetAddress();
            logger.info("新的连接进入 地址 {}, 端口 {}", inetAddress.getHostAddress(), socket.getPort());
            System.out.println("连接的地址 : " + inetAddress.getHostAddress());
            System.out.println("连接的端口 : " + socket.getPort());

            handler(socket);

            System.out.println("server 等待下一个连接 ...");
            // 服务端不停止
//        serverSocket.close();
        }
    }

    private void handler(Socket socket) {
        ExecutorUtils.executorService.execute(() -> {
            BufferedReader reader = null;
            BufferedWriter writer = null;
            try {
                // 获取 输入流 输出流
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                System.out.println("服务线程开始 获取数据 ...");
                String body;
                while (true) {
                    // 阻塞点2, 读取请求的数据
                    body = reader.readLine();
                    // 指定数据表示结束
                    if (body == null || "end".equals(body)) {
                        break;
                    }
                    System.out.println("Server 收到客户端消息 :" + body);
                }
                System.out.println("服务线程开始 处理业务 ...");

                writer.write("Server response: hello !");
                // 发送结束码
                writer.write("end");


                writer.flush();

                IOUtils.close(reader, writer, socket);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader, writer, socket);
            }
        });
    }
}
