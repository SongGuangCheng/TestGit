package org.song.network.javaio.io.io_01_bio;

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
import java.util.concurrent.TimeUnit;

public class BIOTestCase_02 {

    private static Logger logger = LoggerFactory.getLogger(BIOTestCase_02.class);

    private String ip = "127.0.0.1";
    private int port = 10001;
    private int clientPort1 = 60010;
    private int clientPort2 = 60011;


    @Test
    public void client1() throws IOException, InterruptedException {
        Socket socket = new Socket(ip, port);
        socket.bind(new InetSocketAddress(clientPort1));
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
        TimeUnit.SECONDS.sleep(10);
        IOUtils.close(reader, writer, socket);
    }

    @Test
    public void client2() throws IOException, InterruptedException {
        Socket socket = new Socket(ip, port);
        socket.bind(new InetSocketAddress(clientPort2));
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
        TimeUnit.SECONDS.sleep(10);
        IOUtils.close(reader, writer, socket);
    }

    @Test
    public void server_multipart_thread() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));

        System.out.println("server start ...");

        while (true) {
            Socket socket = serverSocket.accept();
            InetAddress inetAddress = socket.getInetAddress();
            logger.info("新的连接进入 地址 {}, 端口 {}", inetAddress.getHostAddress(), socket.getPort());
            System.out.println("连接的地址 : " + inetAddress.getHostAddress());
            System.out.println("连接的端口 : " + socket.getPort());

            handler(socket);

            System.out.println("server 等待下一个连接 ...");
            // 服务端不停止
            serverSocket.close();
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
